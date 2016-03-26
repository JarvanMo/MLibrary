package co.tton.mlibrary.system;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import co.tton.mlibrary.base.BaseApplication;

public class SystemManager {
    private boolean isExit = false;

    private List<Activity> mList = new LinkedList<>();
    // 为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static SystemManager instance;


    // 构造方法
    private SystemManager() {

    }

    // 实例化一次
    public synchronized static SystemManager getInstance() {
        if (null == instance) {
            instance = new SystemManager();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // 关闭每一个list内的activity
    public void exit() {

        for(Runnable r : SystemSettings.ExitHere.doTheseBeforeExit){
            r.run();
        }

        try {
            for (int i = mList.size() - 1; i >= 0; i--) {
                Activity activity = mList.get(i);
//				Log.e("----", i + "");
                if (activity != null) {
                    activity.finish();
                    mList.remove(activity);

                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 某些activity不需要记录
    public void removeActivity(Activity activity) {
        if (mList != null) {
            mList.remove(activity);
        }
    }

    public Activity getCurrActivity() {
        return mList.get(mList.size() - 1);
    }


    public void finishThisActivity(Activity activity) {
        for (int i = mList.size() - 1; i >= 0; i--) {
            Activity a = mList.get(i);


            if (null != a && activity.getClass().toString().equals(a.getClass().toString())) {
                mList.remove(a);

                a.finish();
            }
        }
    }


    public void finishCurrActivity(Activity activity) {

        for (int i = mList.size() - 1; i >= 0; i--) {
            Activity a = mList.get(i);


            String name = activity.getClass().getSimpleName();
            //quitHere永远不为空
//            String quitHere = SystemSettings.ExitHere.exitName;


            if (a != null && a.getClass().getSimpleName().equals(name)) {

                if (SystemSettings.ExitHere.exitNames.contains(name)) {
//					Toast.makeText(a, "this is main", 0).show();
                    doExit(activity);
                    return;
                } else {
//					Toast.makeText(a, "this is not main", 0).show();
                    mList.remove(a);
                    a.finish();
                    return;
                }
            }
        }
    }


    private Handler handler = new ManagerHandler(this);


    private void doExit(Activity activity) {

        if (!isExit) {
            isExit = true;
            BaseApplication.MyToast.show(SystemSettings.ExitHere.exitNotice);
//            Toast.makeText(activity, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            activity.finish();
            exit();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

    }


    public boolean isBackgroundRunning(Context context) {
        String processName = SystemSettings.RunningInBackground.progressName;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (activityManager == null) return false;
// get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
//                if (isBackground || isLockedState) {
//                    return true;
//                } else {
//                    return false;
//                }

                return isBackground || isLockedState;
            }
        }
        return false;
    }


    private static class ManagerHandler extends  Handler{

        private WeakReference<SystemManager> weakReference ;
        public ManagerHandler(SystemManager systemManager){
            weakReference = new WeakReference<SystemManager>(systemManager);
        }

        @Override
        public void handleMessage(Message msg) {
          weakReference.get().isExit = false;
        }
    }
}
