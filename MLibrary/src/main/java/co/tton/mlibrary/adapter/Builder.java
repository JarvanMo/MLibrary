package co.tton.mlibrary.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/9/1.
 *
 */
public class Builder {
    private static Builder mBuilder;
    private Context mContext;
    private View mConvertView;

    private Builder(Context context) {
        this.mContext = context;

    }

    public static Builder getInstance(Context context) {

        if (mBuilder == null) {
            mBuilder = new Builder(context);
        }

        return mBuilder;
    }


    public Builder load(View convertView) {
        if (mBuilder == null) {
            throw new IllegalArgumentException("builder failed");
        }

//        mHolderBuilder.initView();
        mBuilder.mConvertView = convertView;
        return mBuilder;

    }

    public void into(BaseViewHolder viewHolder) {


        Field[] field = viewHolder.getClass().getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            String propertyName = f.getName();
            try {
                int id = mBuilder.getViewID(propertyName);
                Class<?> fieldType = f.getType();
                Object castValue = fieldType.cast(mBuilder.mConvertView.findViewById(id));

                if (castValue == null) {
                    throw new IllegalStateException("findViewById(" + "id" + ") gave null for" + f.toString() + ", can't not reject");
                }
                Log.e("--", id + "");
                /**give the field a value*/
                f.set(viewHolder, castValue);
                f.setAccessible(false);
//                f.getGenericType();
//                f.set(propertyName,mBuilder.mConvertView.findViewById(id));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * to get view's id in xx.R.id
     *
     * @param propertyName name of view's id
     **/
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private int getViewID(String propertyName) {

        String packageName = mContext.getPackageName();

        try {
            Class r = Class.forName(packageName + ".R");
            Class idClass[] = r.getClasses();

            for (Class clazz : idClass) {

                String name = clazz.getSimpleName();
                /**get class R.id*/
                if (name.contains("id")) {
                    Field field = clazz.getField(propertyName);
                    int id = (int) field.get(clazz);
                    return id;
                }
            }

            return 0;
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return 0;

    }

}
