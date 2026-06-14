package id.danet.historiadanet;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bypass ngrok warning page
        WebView webView = getBridge().getWebView();
        WebSettings settings = webView.getSettings();
        String defaultUA = settings.getUserAgentString();
        settings.setUserAgentString(defaultUA + " ngrok-skip-browser-warning");

        // Enable JavaScript features needed for QR scanning
        settings.setJavaScriptEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        // Enable camera access in WebView
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
    }
}
