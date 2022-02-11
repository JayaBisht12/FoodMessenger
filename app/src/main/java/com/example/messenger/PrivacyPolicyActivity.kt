package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        setTitle("Privacy Policy")
        val url="https://www.wipro.com/privacy-statement/"
        val WvPrivacy=findViewById<WebView>(R.id.WvPrivacy)
        WvPrivacy.webViewClient= WebViewClient()
        WvPrivacy.loadUrl(url)
    }
}