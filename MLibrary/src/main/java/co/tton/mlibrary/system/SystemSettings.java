package co.tton.mlibrary.system;





import java.io.File;
import java.util.HashSet;
import java.util.Set;

import co.tton.mlibrary.R;
import co.tton.mlibrary.base.BaseApplication;
import co.tton.mlibrary.util.FileUtil;

/**
 * Created by Mo on 2015/7/6.
 *
 */
public class SystemSettings {

    //ss

    public final static String SLASH = File.separator;

    public static class Defaults{

        public static boolean IS_APP_FONT_SIZE_FIXED = false;
    }

    public static class Debug {

        /**
         * 是否 是 开发模式, 是的话, 使用 {@link co.tton.mlibrary.util.LogUtil} 中的方法 会打印 log 等
         */
        public static boolean SHOW_DEVELOP_LOG;// XXX 发布的时候, 注意写成 false

        /**
         * 是否 开启 代码质量 严格检查 模式
         */
        public static boolean ENABLE_STRICT_MODE;// XXX 发布的时候, 注意写成 false
    }

    public static class LogCachePath {
        public static String CRASH_LOG_PATH;

        public static String getCrashLogPath() {
            if (CRASH_LOG_PATH == null || CRASH_LOG_PATH.equals("")) {
                CRASH_LOG_PATH = SystemFile.getBasePath() + "CrashLog" + SLASH;
            }

            return CRASH_LOG_PATH;
        }

    }


    /**
     *
     */
    public static class SystemFile {

        public static final String FILE_PREFIX = "tton";

        private static String BASE_PATH;


        public static String getBasePath() {
            if (BASE_PATH == null || BASE_PATH.equals("")) {
                BASE_PATH = FileUtil.getBaseSavePath(BaseApplication.mApplicationContext) + SLASH + //
                        FILE_PREFIX + SLASH + BaseApplication.mApplicationContext.getPackageName() + SLASH;
            }

            return BASE_PATH;
        }

    }


    /**
     * usedfor update
     */
    public static class UpdateParams {

        public static final String APK_UPDATE_SERVER = "apk_update_server";
        public static final String APK_UPDATE_CONTENT = "apk_update_content";
        public static final String APK_DOWNLOAD_URL = "apk_download_url";
        public static final String APK_VERSION_CODE = "version";
    }

    public static class ExitHere {
        public static Set<String> exitNames = new HashSet<>();
        public static String exitName = "MainActivity";
        public static int exitNotice = R.string.exit_app_notice;
        public static Set<Runnable> doTheseBeforeExit = new HashSet<>();

    }


    public static class RunningInBackground {
        public static String progressName = "match.android.activity";
    }

}
