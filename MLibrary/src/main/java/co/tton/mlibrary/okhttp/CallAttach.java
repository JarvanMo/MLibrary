package co.tton.mlibrary.okhttp;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2015/9/21.
 *
 */
public class CallAttach {

    private Call call;

    public CallAttach( Call call) {
        this.call = call;
    }

    /**
     * i don't if it works or not
     * */
    @Deprecated
    public void cancel(Object tag) {

        if (tag == null) {
            throw new IllegalArgumentException("can't cancel a call with null tag");
        }else if(call.request().tag() == tag){
            call.cancel();
        }
    }


}
