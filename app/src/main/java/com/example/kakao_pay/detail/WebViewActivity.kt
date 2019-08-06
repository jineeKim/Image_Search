package com.example.kakao_pay.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kakao_pay.R
import kotlinx.android.synthetic.main.activity_webview.*
import android.webkit.WebView
import android.webkit.WebViewClient



class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        var url = intent.getStringExtra("url")

        act_web_wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }

        val settings = act_web_wv.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        act_web_wv.loadUrl(url)

    }
}
