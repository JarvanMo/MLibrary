package co.tton.mlibrary.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Mr.Mo on 2015/9/1.
 * this is a father view holder which will be used by {@link LazyAdapter }{@link EasyAdapter}<br/>
 * NOTE : in this holder, very view's name must the same as the id in the xml.
 * <br/> please never override the constructor
 */
public class BaseViewHolder  {

    public BaseViewHolder() {


    }
    public BaseViewHolder(View view) {
        ButterKnife.bind(this,view);
    }




}
