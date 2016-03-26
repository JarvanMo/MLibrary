package co.tton.mlibrary.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;


import butterknife.ButterKnife;
import co.tton.mlibrary.system.LeakCanaryHelper;
import co.tton.mlibrary.system.SystemManager;
import co.tton.mlibrary.system.SystemSettings;

/**
 * Created by Mo on 2015/7/6.
 *
 */
public abstract class  BaseActivity extends AppCompatActivity {

    public SystemManager Manager = SystemManager.getInstance();
    private final int ACTIVITY_NOT_FOR_RESULT = 0xFFFF;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = setLayoutId();

        if(layoutId != 0 ){
            setContentView(layoutId);
        }

        ButterKnife.bind(this);
        Manager.addActivity(this);
        getDataFromIntent();
        doMainJob();
    }


    protected void getDataFromIntent() {

    }

    protected void doMainJob() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // PolyApplication.SystemManager.finishCurrActivity(this);
            Manager.finishCurrActivity(this);
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) findViewById(id);

    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(View view, int id) {

        return (T) view.findViewById(id);

    }

    public void callNewActivity(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (requestCode == ACTIVITY_NOT_FOR_RESULT) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    public void callNewActivity(Class<?> clazz) {
        callNewActivity(clazz, ACTIVITY_NOT_FOR_RESULT);
    }


    public void callNewActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void showToast(String msg) {
        BaseApplication.MyToast.show(msg);
    }

    public void showToast(@StringRes  int id) {
        BaseApplication.MyToast.show(id);
    }


    @LayoutRes
    public abstract int setLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public Resources getResources() {
        if(!SystemSettings.Defaults.IS_APP_FONT_SIZE_FIXED) {
            return super.getResources();
        }else {

            Resources resources = super.getResources();
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            resources.updateConfiguration(configuration,resources.getDisplayMetrics());

            return  resources;
        }
    }
}

