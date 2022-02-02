package com.example.messenger

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
            var userid:String = sharedPreferences?.getString("username",null).toString()

        if (sharedPreferences?.getString("username", null)!= null) {
            //
            val i = Intent(this, Dashboard::class.java).apply {
                putExtra("Data",userid)
            }
            startActivity(i)
            finish()
        }

    }
}