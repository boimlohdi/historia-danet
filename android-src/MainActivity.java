package id.danet.historiadanet;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = getBridge().getWebView();
        WebSettings settings = webView.getSettings();
        String defaultUA = settings.getUserAgentString();
        settings.setUserAgentString(defaultUA + " ngrok-skip-browser-warning");
    }
}
