package tw.tcnr.cos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.tcnr.cos.R;

public class C0501_Tab02 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.c0501_tab02, container, false);

        WebView webView=(WebView)v.findViewById(R.id.c0502_w001);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://docs.google.com/forms/d/1LlvraNnjMCv_4KSkUdavg_90GXP-RFcT48DiA63Rw9k/edit");



        return v;


    }

}
