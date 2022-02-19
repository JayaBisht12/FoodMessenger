package com.example.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class TandCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tand_cactivity)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = ""
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var url: String
        val intentValue = intent.getStringExtra("flag")//userid received from the signin fragment
        val flag: Int = intentValue.toString().toInt()
        val wvTandC = findViewById<WebView>(R.id.wvTandC)
        wvTandC.webViewClient = WebViewClient()
        if (flag == 0) {
            actionbar!!.title = "Terms and Conditions"
            url = "https://careers.wipro.com/terms-of-use"
            wvTandC.loadUrl(url)
        }

        if (flag == 1) {

            actionbar!!.title = "Privacy Policy"
            url = "https://www.wipro.com/privacy-statement/"
            wvTandC.loadUrl(url)
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }
}