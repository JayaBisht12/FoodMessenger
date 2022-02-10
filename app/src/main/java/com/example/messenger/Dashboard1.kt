package com.example.messenger

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class Dashboard1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard1)
        val tabLayout=findViewById<TabLayout>(R.id.tabLayout)
        var navc: NavController? = null
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragmentManager=supportFragmentManager
                val fragmentTransaction=fragmentManager.beginTransaction()
                if(tabLayout.getSelectedTabPosition()==0)
                {
                fragmentTransaction.replace(R.id.fragmentContainerView6,DashHomeFragment())
                fragmentTransaction.commit()
                }
                if(tabLayout.getSelectedTabPosition()==1) {
                    fragmentTransaction.replace(R.id.fragmentContainerView6, Product())
                    fragmentTransaction.commit()
                }
                if(tabLayout.getSelectedTabPosition()==2) {
                    fragmentTransaction.replace(R.id.fragmentContainerView6, DashSettingsFragment())
                    fragmentTransaction.commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

//                val fragmentManager=supportFragmentManager
//                val fragmentTransaction=fragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.fragmentContainerView6,Product())
//                fragmentTransaction.commit()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                val fragmentManager=supportFragmentManager
//                val fragmentTransaction=fragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.fragmentContainerView6,DashSettingsFragment())
//                fragmentTransaction.commit()
            }
        })


    }
}




