package com.example.messenger

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.db.UserRepository
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.Authentication
import de.hdodenhof.circleimageview.CircleImageView

class RecentUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Happy Shop")
        setContentView(R.layout.activity_recent_user)
        val ivProfilepic=findViewById<CircleImageView>(R.id.ivProfilepic)
        val UserId=findViewById<TextView>(R.id.UserId)
        val tvClick=findViewById<TextView>(R.id.tvClick)
        val bPlus =findViewById<ImageButton>(R.id.bPLus)


        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
        //val name = myEdit
        var logout=  sharedPreferences?.getString("logout",null)
        //var flag: Int = logout?.toInt()!!

        val id=sharedPreferences?.getString("username", null).toString()
        val repo = UserRepository(this)
        val user= repo.getuser(id)

        ivProfilepic.setImageResource(R.drawable.icon)

        // sets the text to the textview from our itemHolder class
      UserId.text = user.userid+" -> "
        val imgbytes: ByteArray = user.userImage
        val bitmap = BitmapFactory.decodeByteArray( //converting the byte array into image
            imgbytes, 0,
            imgbytes.size
        )
      ivProfilepic.setImageBitmap(bitmap)

        bPlus.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Please Confirm!")
                .setMessage("Do you want to remove $id and log in as another user?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("No",
                    DialogInterface.OnClickListener { dialog, which ->



                    })
                .setIcon(R.drawable.alerticon)
                .setNegativeButton("Yes ",
                    DialogInterface.OnClickListener { dialog, which ->
                        myEdit?.putString("username", null)
                        myEdit?.putString("password", null)
                        myEdit?.apply()
                        LoginManager.getInstance().logOut()
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build()
                        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                        mGoogleSignInClient.signOut()

                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        finish()

                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()



        }



        tvClick.setOnClickListener {

            if (user.Authentication == true) {
                val i = Intent(this, BiometricActivity::class.java)
                startActivity(i)
                finish()
            } else{
//            { if(flag==1)
//            {val i = Intent(this, BiometricActivity::class.java)
//
//                myEdit?.putString("logout", "0")
//                myEdit?.apply()
//                startActivity(i)
//                finish()
//            }

                //else{
                val i = Intent(this, Dashboard1::class.java)
                startActivity(i)
                finish()

                //}
            }
        }





    }
}