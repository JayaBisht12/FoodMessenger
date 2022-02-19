package com.example.messenger

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messenger.db.AppDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)




        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        var userid:String = sharedPreferences?.getString("username",null).toString()

        var db: UserDAO = AppDatabase.getInstance(this)?.userDao()!!
        val list = db.getAllusers()
        var authentication:Boolean=false

        for( i in 0..list.size-1) {      //Displayed all the data of the user into the dashboard
            if (list[i].userid == userid) {  //id= received from the signin fragment and converted the id into string

                if(list[i].Authentication==true)
                    authentication=true

                else
                    authentication=false

            }
        }



        if (sharedPreferences?.getString("username", null)!= null) {
            if(authentication==true) {
                val i = Intent(this, RecentUserActivity::class.java).apply {
                    putExtra("Data", userid)
                }
                startActivity(i)
                finish()
            }
            else
            {
                val i = Intent(this, RecentUserActivity::class.java).apply {
                    putExtra("Data", userid)
                }
                startActivity(i)
                finish()
            }
        }

    }
}