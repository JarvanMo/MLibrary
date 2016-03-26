package co.tton.mlibrary.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.rtp.RtpStream;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewHelper {
    private WebViewHelper helper;

    private WebViewHelper(Context context) {
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebViewSettings(WebView web) {
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.getSettings().setDisplayZoomControls(false);
        web.getSettings().setDomStorageEnabled(true);
    }
}
