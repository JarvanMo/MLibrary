package co.tton.mlibrary.adapter;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2015/9/1.
 */
public class ViewHolderBuilder {
    private static Builder mBuilder;


    private ViewHolderBuilder() {
    }


    /***/
    public static Builder with(Context context) {

        if (context == null) {
            throw new IllegalArgumentException("context must not be null.");
        }


        if (mBuilder == null) {
            mBuilder = Builder.getInstance(context);
        }
        return mBuilder;
    }


}


