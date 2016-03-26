package co.tton.mlibrary.base;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

import co.tton.mlibrary.R;
import co.tton.mlibrary.system.LeakCanaryHelper;
import co.tton.mlibrary.system.SystemManager;
import co.tton.mlibrary.system.SystemSettings;

/**
 * Created by Mo on 2015/7/6.
 *
 */
public class BaseApplication extends Application {
    public static Context mApplicationContext;
    private static ProgressDialog dialog;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        initSystem();
    }

    private void initSystem() {
//        CrashHandler.getInstance().init(getApplicationContext());
        SystemSettings.ExitHere.exitNames.add(SystemSettings.ExitHere.exitName);
        SystemManager.getInstance();
        LeakCanaryHelper.getInstance(this);
    }



    public static class MyDialog {

        public static void show(Context context) {
            if (dialog == null) {
                dialog = new ProgressDialog(context);
            }

            dialog.setTitle(null);
            dialog.setMessage(context.getString(R.string.dialog_text));
//            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        public static void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }


    public static class MyToast {

        public static void show(@StringRes int stringId) {
            show(mApplicationContext.getString(stringId));
        }

        public static void show(String mess) {
            if(mApplicationContext != null){
                show(mApplicationContext, mess);
            }

        }

        public static void show(final Context context, final String mess) {

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
