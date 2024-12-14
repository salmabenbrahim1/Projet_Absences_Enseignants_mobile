package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.PowerBiViewModel;

public class PowerBiFragment extends Fragment {

    private PowerBiViewModel powerBiViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Charger le layout du fragment
        View view = inflater.inflate(R.layout.fragment_power_b_i, container, false);

        // Initialiser le ViewModel
        powerBiViewModel = new ViewModelProvider(this).get(PowerBiViewModel.class);

        // Configurer le WebView
        WebView webView = view.findViewById(R.id.webViewPowerBi);
        configureWebView(webView);

        // Observer l'URL et charger dans le WebView
        powerBiViewModel.getReportUrl().observe(getViewLifecycleOwner(), webView::loadUrl);

        return view;
    }

    private void configureWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Activer JavaScript
        webSettings.setDomStorageEnabled(true); // Activer le stockage DOM

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
