package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class TandCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tand_cactivity)
        setTitle("Terms And Conditions")
        val url="https://careers.wipro.com/terms-of-use"
        val  wvTandC=findViewById<WebView>(R.id. wvTandC)
        wvTandC.webViewClient= WebViewClient()
        wvTandC.loadUrl(url)
    }
}