package id.danet.historiadanet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;
import com.getcapacitor.BridgeActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = getBridge().getWebView();
        WebSettings settings = webView.getSettings();

        // Bypass ngrok warning
        settings.setUserAgentString(settings.getUserAgentString() + " ngrok-skip-browser-warning");

        // Enable all WebView features
        settings.setJavaScriptEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);

        // Inject QR scanner bridge
        webView.addJavascriptInterface(new QRBridge(), "AndroidQR");
    }

    // Bridge untuk trigger QR scan native
    class QRBridge {
        @JavascriptInterface
        public void startScan() {
            runOnUiThread(() -> {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Arahkan ke QR Code perangkat");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String qrData = result.getContents();
                // Kirim hasil scan ke JavaScript
                getBridge().getWebView().post(() ->
                    getBridge().getWebView().evaluateJavascript(
                        "if(window.onAndroidQRResult) window.onAndroidQRResult('" + qrData + "');",
                        null
                    )
                );
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
