package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class demoTandC : AppCompatActivity() {



       private lateinit var webView: WebView
        lateinit var progressBar: ProgressBar

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_tand_c)
           val actionbar = supportActionBar
           //set actionbar title
           actionbar!!.title = " Terms And Condition"
           //set back button
           actionbar.setDisplayHomeAsUpEnabled(true)
           actionbar.setDisplayHomeAsUpEnabled(true)

        // Initializing Webview and
            // progressBar from the layout file
            webView = findViewById(R.id.webView)
           val intentValue = intent.getStringExtra("flag")
           val flag: Int = intentValue.toString().toInt()
           var url:String="https://careers.wipro.com/terms-of-use"
            progressBar = findViewById(R.id.progressBar)


            // Setting a webViewClient
            webView.webViewClient = WebViewClient()
           if(flag==1){
               
               url="https://www.wipro.com/privacy-statement/"
               actionbar!!.title = "Privacy Policy"

           }

            // Loading a URL
            webView.loadUrl(url)
        }

        // Overriding WebViewClient functions
        inner class WebViewClient : android.webkit.WebViewClient() {

            // Load the URL
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            // ProgressBar will disappear once page is loaded
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }
}