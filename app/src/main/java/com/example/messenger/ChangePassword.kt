package com.example.messenger

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setTitle("Change Password")
        val bSave = findViewById<Button>(R.id.bSave)
        val etCurrentPass = findViewById<EditText>(R.id.etCurrentPass)
        val etNewPass = findViewById<EditText>(R.id.etNewPass)
        val etConfirmPass = findViewById<EditText>(R.id.etconfirmpass)
        var db: UserDAO = AppDatabase.getInstance(this.applicationContext)?.userDao()!!
        var list = db.getAllusers() //list of data
        val intentValue = intent.getStringExtra("Data")//Receiving user id ;
        val userid: String = intentValue.toString()
        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()
        val name = myEdit


        bSave.setOnClickListener {
            if (etCurrentPass.text.toString().isEmpty()) {
                etCurrentPass.error = "Password is required"
            } else {
                if (etNewPass.text.toString().isEmpty()) {
                    etNewPass.error = "Password is required"
                } else if (etConfirmPass.text.toString().isEmpty()) {
                    etConfirmPass.error = "Password is required"
                }
            //
                else{



            if (etNewPass.text.toString() != etConfirmPass.text.toString()) {
                etConfirmPass.error = "Passwords mismatched"//textbox error
            }
                    var flag :Int=0
            for (i in 0..list.size - 1) {
                 flag=0
                if (list[i].userid == userid && list[i].userPassword == etCurrentPass.text.toString()) {
                    if (etCurrentPass.text.toString() == etNewPass.text.toString()) {
                        etNewPass.error = "New password should not be the current password"
                    }
                    else
                    {

                    if (etNewPass.text.toString() == etConfirmPass.text.toString()) {

                        val repo = UserRepository(this)

                        repo.updateUser(list[i].userid, etNewPass.text.toString())
                        Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT)
                            .show()

                        AlertDialog.Builder(this)
                            .setTitle("Alert")
                            .setMessage("Stay logged in?") // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("No", {dialog,which->
                                val intent = Intent(this, MainActivity::class.java)

                                myEdit?.putString("username", null)
                                myEdit?.putString("password", null)
                                myEdit?.apply()
                                startActivity(intent)
                                finish()
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton("Yes",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val intent = Intent(this, Dashboard::class.java).apply {
                                        putExtra("Data",userid)
                                    }
                                    myEdit?.putString("password", etNewPass.text.toString())
                                    startActivity(intent)
                                    // Continue with delete operation // (android.R.string.yes,

                                }) // A null listener allows the button to dismiss the dialog and take no further action.

                            .show()


                    }

                }

                } else {
                      flag=1

                   }

                }
                    if(flag==1)
                    {

                            etCurrentPass.error = "Invalid Password"


            }

        }}
        }
    }
}