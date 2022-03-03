package com.example.messenger

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Person
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.example.messenger.db.UserRepository
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.plus.Plus
import com.google.android.gms.tasks.Task
import java.io.ByteArrayOutputStream

import com.google.android.gms.common.api.GoogleApiClient
import com.squareup.picasso.Picasso
import java.io.InputStream


class GoogleSignUp : AppCompatActivity() {
    lateinit var  mGoogleSignInClient: GoogleSignInClient
     lateinit var img: ImageView
     lateinit var uri: Uri

    @SuppressLint("WrongThread")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_up)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
          img=findViewById<ImageView>(R.id.img)

        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, 100)
        //updateUI(account)
       // signIn()
        val acct = GoogleSignIn.getLastSignedInAccount(this)

//            if (acct != null) {
//
//
//            val personName = acct.displayName
//            val personGivenName = acct.givenName
//            val personFamilyName = acct.familyName
//            val personEmail = acct.email
//            val personId = acct.id
//            val personPhoto: Uri? = acct.photoUrl
//            setTitle(personName.toString())
//            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,personPhoto)
//            val stream = ByteArrayOutputStream()
//           bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray: ByteArray = stream.toByteArray()
//            bitmap.recycle()
//
//            val repo = UserRepository(this)
//
//            val photo = BitmapFactory.decodeResource(
//                resources,
//                R.drawable.icon
//            ) //this returns null
//            val sharedPreferences: SharedPreferences? =
//                getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
//            val myEdit = sharedPreferences?.edit()
//
//
//            photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
//            val image: ByteArray = byteArray
//            var authentication:Boolean=false
//            AlertDialog.Builder(this)
//                .setTitle("Please Confirm!")
//                .setMessage("Do you want to use Biometric Authentication for login?") // Specifying a listener allows you to take an action before dismissing the dialog.
//                // The dialog is automatically dismissed when a dialog button is clicked.
//                .setPositiveButton("No",
//                    DialogInterface.OnClickListener { dialog, which ->
//                        authentication=false
//
//                        val user: User = User("\n" +personName,personId.toString(),
//                            "12345678", image,"10/09/2000"
//                            ,authentication)
//                        repo.insertUser(user)
//                        //move to main activity
//                        myEdit?.putString("username",personId.toString())
//                        myEdit?.putString("password","12345678")
//                        myEdit?.putString("logout", "0")
//                        myEdit?.apply()
//                        val intent = Intent(this, Dashboard1::class.java)
//                        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
//                        startActivity(intent)
//                        finish()
//
//
//                    })
//                .setIcon(R.drawable.alerticon)
//                .setNegativeButton("Yes ",
//                    DialogInterface.OnClickListener { dialog, which ->
//                        authentication=true
//                        val user: User = User("\n" +personName,personId.toString(),
//                            "12345678", image,"10/09/2000"
//                            ,authentication)
//                        repo.insertUser(user)
//                        //move to main activity
//                        myEdit?.putString("logout", "0")
//
//
//
//                        myEdit?.putString("username",personId.toString())
//                        myEdit?.putString("password","12345678")
//                        myEdit?.apply()
//                        val intent = Intent(this, BiometricActivity::class.java)
//                        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
//                        startActivity(intent)
//                        finish()
//
//                    }) // A null listener allows the button to dismiss the dialog and take no further action.
//
//                .show()
//
//           }
//
//    else
//        {
//            Toast.makeText(this, "Jaya", Toast.LENGTH_SHORT).show()
//        }
    }


//    private fun signIn() {
//        TODO("Not yet implemented")
//
//    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
            val account = completedTask.getResult(ApiException::class.java)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            bitmap.recycle()





            // Signed in successfully, show authenticated UI.
           // updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Catch successfully", Toast.LENGTH_SHORT).show()
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            //  updateUI(null)
        }
    }

//    private fun updateUI(account: GoogleSignInAccount?) {
//        if (account != null) {
////            text.setText("Sign in as :" + account.displayName)
////            email.setText(account.email)
//            val imgurl = account.photoUrl.toString()
//            Glide.with(this).load(imgurl).into(img)
////            sighIn.setVisibility(View.GONE)
////            sighOut.setVisibility(View.VISIBLE)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {

            GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnCompleteListener {
                    uri= it.result.photoUrl!!
                    if (it.isSuccessful){

                        // user successfully logged-in
                        setTitle(it.result?.displayName)
                     ////////////////////////////////////////////////////////////////////////////////////////////////////////

//                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,it.result.photoUrl)
//                        val stream = ByteArrayOutputStream()
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                        val byteArray: ByteArray = stream.toByteArray()
//                        bitmap.recycle()
                        uri= it.result.photoUrl!!
                        val photo = BitmapFactory.decodeResource(
                            resources,
                            R.drawable.icon
                        ) //this returns null

                       // val bitmap = BitmapFactory.decodeStream(input)
                        //input.close()

                      // img.setImageURI(it.result.photoUrl)
                        val stream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        val image: ByteArray = stream.toByteArray()
                          val selectedImageUri: Uri? = it.result.photoUrl
                       Picasso.get().load(it.result.photoUrl).into(img)
                        if(selectedImageUri==null)
                        {
                            Toast.makeText(this, "Image null", Toast.LENGTH_SHORT).show()
                        }

                        //Glide.with(this).load(it.result.photoUrl.toString()).into(img)

//                        val bitmap = MediaStore.Images.Media.getBitmap(this?.contentResolver, selectedImageUri)
//                        val stream = ByteArrayOutputStream()
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                        val byteArray: ByteArray = stream.toByteArray()
//                        bitmap.recycle()
                      //  mGoogleSignInClient.hasConnectedApi(Plus.API
                        val repo = UserRepository(this)
                        val user: User = User("\n" +it.result.displayName,it.result.email.toString(),
                            "12345678",image,"10/09/2000"
                            ,true)
                        repo.insertUser(user)

//                        val photo = BitmapFactory.decodeResource(
//                            resources,
//                            R.drawable.icon
//                        ) //this returns null
//                        val sharedPreferences: SharedPreferences? =
//                            getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
//                        val myEdit = sharedPreferences?.edit()
//
//
//                        photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
//                        val image: ByteArray = byteArray
//                        var authentication:Boolean=false
//                        AlertDialog.Builder(this)
//                            .setTitle("Please Confirm!")
//                            .setMessage("Do you want to use Biometric Authentication for login?") // Specifying a listener allows you to take an action before dismissing the dialog.
//                            // The dialog is automatically dismissed when a dialog button is clicked.
//                            .setPositiveButton("No",
//                                DialogInterface.OnClickListener { dialog, which ->
//                                    authentication=false
//
//                                    val user: User = User("\n" +it.result.displayName,it.result.email.toString(),
//                                        "12345678", image,"10/09/2000"
//                                        ,authentication)
//                                    repo.insertUser(user)
//                                    //move to main activity
//                                    myEdit?.putString("username",it.result.email.toString())
//                                    myEdit?.putString("password","12345678")
//                                    myEdit?.putString("logout", "0")
//                                    myEdit?.apply()
//                                    val intent = Intent(this, Dashboard1::class.java)
//                                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
//                                    startActivity(intent)
//                                    finish()
//
//
//                                })
//                            .setIcon(R.drawable.alerticon)
//                            .setNegativeButton("Yes ",
//                                DialogInterface.OnClickListener { dialog, which ->
//                                    authentication=true
//                                    val user: User = User("\n" +it.result.displayName,it.result.email.toString(),
//                                        "12345678", image,"10/09/2000"
//                                        ,authentication)
//                                    repo.insertUser(user)
//                                    //move to main activity
//                                    myEdit?.putString("logout", "0")
//
//
//
//                                    myEdit?.putString("username",it.result.email.toString())
//                                    myEdit?.putString("password","12345678")
//                                    myEdit?.apply()
//                                    val intent = Intent(this, BiometricActivity::class.java)
//                                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
//                                    startActivity(intent)
//                                    finish()
//
//                                }) // A null listener allows the button to dismiss the dialog and take no further action.
//
//                            .show()




                        //////////////////////////////////////////////////////////////////////////////////////////////////////




                    } else {
                        // authentication failed

                    }
                }
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

}