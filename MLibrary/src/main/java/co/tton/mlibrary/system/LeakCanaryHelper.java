package co.tton.mlibrary.system;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Jerry on 2016/1/12.
 *
 */
public class LeakCanaryHelper {

    public static LeakCanaryHelper leakCanaryHelper;
    private RefWatcher refWatcher;
    private LeakCanaryHelper(Application application){
        refWatcher = LeakCanary.install(application);
    }

    public static  LeakCanaryHelper getInstance(Application application){

        if(leakCanaryHelper == null){

            leakCanaryHelper =  new LeakCanaryHelper(application);
        }

        return leakCanaryHelper;
    }

    public  RefWatcher getRefWatcher(Context context) {
        return refWatcher;
    }
}
