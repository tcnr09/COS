package tw.tcnr.cos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.tcnr.cos.R;

public class C0501_Tab01 extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.c0501_tab01, container, false);

        WebView webView=(WebView)v.findViewById(R.id.c0501_w001);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.facebook.com/%E9%9B%B2%E7%AB%AF%E5%92%96%E5%95%A1%E5%BA%97-533187727179433/");

        return v;

    }




}
