package com.example.messenger

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import java.io.ByteArrayOutputStream


class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val bChangePass= findViewById<TextView>(R.id.bChangePass)
        setTitle("Dashboard")
        var db: UserDAO = AppDatabase.getInstance(this.applicationContext)?.userDao()!!
        val list=db.getAllusers() //list of data
        val intentValue = intent.getStringExtra("Data")//userid received from the signin fragment
        val id: String = intentValue.toString() //received from the signin fragment and converted the id into string
       val tvName= findViewById<TextView>(R.id.tvName)
        val ivProfilepic=findViewById<ImageView>(R.id.ivProfilepic)
        val tvDob=findViewById<TextView>(R.id.tvDOB)
        val tvUserId = findViewById<TextView>(R.id.tvUserId)
        val bLogout=findViewById<Button>(R.id.bLogout)
        val bDelete=findViewById<Button>(R.id.bDelete)
        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()
        val name = myEdit

        for( i in 0..list.size-1) {      //Displayed all the data of the user into the dashboard
            if (list[i].userid == id) {  //id= received from the signin fragment and converted the id into string
                tvName.text = list[i].username
                tvDob.text =list[i].userDob
                tvUserId.text=list[i].userid
                val imgbytes: ByteArray =list[i].userImage
                val bitmap = BitmapFactory.decodeByteArray(
                    imgbytes, 0,
                    imgbytes.size
                )
                ivProfilepic.setImageBitmap(bitmap)

            }
        }


        ivProfilepic.setOnClickListener{


            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Change profile picture?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Remove",DialogInterface.OnClickListener { dialog, which ->
                    val repo = UserRepository(this)
                    val photo = BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icon
                    ) //this returns null


                    val stream = ByteArrayOutputStream()
                    photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image: ByteArray = stream.toByteArray()
                    repo.updateImage(id,image)
                    val imgbytes: ByteArray = image
                    val bitmap = BitmapFactory.decodeByteArray(
                        imgbytes, 0,
                        imgbytes.size
                    )
                    ivProfilepic.setImageBitmap(bitmap)
                    Toast.makeText(this,"Removed Successfully",Toast.LENGTH_SHORT).show()


                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Upload/Change ",
                    DialogInterface.OnClickListener { dialog, which ->
                        val image:Intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        val resultCode = startActivityForResult(image,1)
                        onActivityResult(1,1,image)

                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

        }




        bChangePass.setOnClickListener {

            val intent = Intent(this, ChangePassword::class.java).apply {
                putExtra("Data", id)
            }
            startActivity(intent) //starts the change password activity
        }

        bLogout.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)

            Toast.makeText(this,"Logged Out Successfully",Toast.LENGTH_SHORT).show()
            myEdit?.putString("username", null)
            myEdit?.putString("password", null)
            myEdit?.apply()
            startActivity(intent)
            finish()
        }

  bDelete.setOnClickListener{
      AlertDialog.Builder(this)
          .setTitle("Delete entry")
          .setMessage("Are you sure you want to delete this user?") // Specifying a listener allows you to take an action before dismissing the dialog.
          // The dialog is automatically dismissed when a dialog button is clicked.
          .setPositiveButton("No", null)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .setNegativeButton("Yes",
              DialogInterface.OnClickListener { dialog, which ->
                  // Continue with delete operation // (android.R.string.yes,
                  val repo = UserRepository(this)
                  Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                  repo.deleteUser(tvUserId.text.toString())
                  myEdit?.putString("username", null)
                  myEdit?.putString("password", null)
                  myEdit?.apply()
                  val intent = Intent(this,MainActivity::class.java)
                  startActivity(intent)
                  finish()
              }) // A null listener allows the button to dismiss the dialog and take no further action.

          .show()

  }



        }



    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?)
    {    val ivProfilepic=findViewById<ImageView>(R.id.ivProfilepic)
        super.onActivityResult(requestCode, resultCode, data)
        val intentValue = intent.getStringExtra("Data")
        val id: String = intentValue.toString()
        if(resultCode == AppCompatActivity.RESULT_OK){
            val selectedImageUri: Uri? = data?.getData()
            ivProfilepic.setImageURI(selectedImageUri)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            bitmap.recycle()
            val repo = UserRepository(this)
            repo.updateImage(id,byteArray)
            Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show()

        }
    }


}






