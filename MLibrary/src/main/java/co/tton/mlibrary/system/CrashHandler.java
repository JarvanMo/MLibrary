package co.tton.mlibrary.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import co.tton.mlibrary.R;
import co.tton.mlibrary.util.DeviceUtil;
import co.tton.mlibrary.util.LogUtil;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE = new CrashHandler();

    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<>();

    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());// Locale.CHINA

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        this.mContext = context;

        // 获取系统默认的UncaughtException处理器
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();

        if ((!handleException(ex)) && (mDefaultHandler != null)) {
            // 如果用户没有处理, 则让系统默认的异常处理器来处理, 弹出来 Focus Close
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                LogUtil.e(TAG, "打断出现错误: ", e);
            }

            // SharePre.setLoginState(mContext, false);
            //BaseApplication.exitClient();
            SystemManager.getInstance().exit();
        }
    }

    /**
     * 自定义错误处理, 收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex throwable
     * @return [true] 用户自己处理了该异常信息 [false]系统处理
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, mContext.getString(R.string.unknown_exception), Toast.LENGTH_LONG).show();// 不使用 框架, 方便 移植
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息
        DeviceUtil.collectDeviceInfo(mContext, infos);

        // 保存日志文件
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex throwable
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String result = key + "=" + value + "\n";
            sb.append(result);
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = "\n===========\n" + writer.toString();
        sb.append(result);

        try {
            String time = formatter.format(new Date());
            long timestamp = System.currentTimeMillis();
            String fileName = "crash (" + time + ") " + timestamp + ".log";

            // if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // String path = Environment.getExternalStorageDirectory() + Constant.Log.CRASH_LOG_PATH;
            String path = SystemSettings.LogCachePath.getCrashLogPath();
            File dir = new File(path);


            boolean b = dir.exists() || dir.mkdirs();

            if (b) {
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            // }

            return fileName;
        } catch (Exception e) {
            LogUtil.e(TAG, "写入文件时, 发生了错误.", e);
        }

        return null;
    }


}
