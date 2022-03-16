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
import android.widget.Button
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
import androidx.annotation.NonNull
import com.example.messenger.db.AppDatabase
import com.google.android.gms.tasks.OnCompleteListener


class GoogleSignUp : AppCompatActivity() {
    lateinit var  mGoogleSignInClient: GoogleSignInClient
    // lateinit var img: ImageView
     lateinit var uri: Uri
     lateinit var intentValue : String

    @SuppressLint("WrongThread")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_up)
      //  val SignOut=findViewById<Button>(R.id.SignOut)

        uri= Uri.parse("")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
       intentValue= intent.getStringExtra("activity")!!
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //  img=findViewById<ImageView>(R.id.img)

        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, 100)
        //updateUI(account)
       // signIn()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {

            val account = completedTask.getResult(ApiException::class.java)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

//            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            //  updateUI(null)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {

            GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnCompleteListener {

                    if (it.isSuccessful){

                        val photo = BitmapFactory.decodeResource(
                            resources,
                            R.drawable.icon
                        )
                        val stream = ByteArrayOutputStream()
                        photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        val image: ByteArray = stream.toByteArray()
                          val selectedImageUri: Uri? = it.result.photoUrl
                       //Picasso.get().load(it.result.photoUrl).into(img)
                        if(selectedImageUri==null)
                        {
                            Toast.makeText(this, "Image null", Toast.LENGTH_SHORT).show()
                        }

                        //Glide.with(this).load(it.result.photoUrl.toString()).into(img)


                        val repo = UserRepository(this)
//                        var db: UserDAO = AppDatabase.getInstance(this)?.userDao()!!
//                        val list = usersDao.getAllusers();
                       val list=repo.getAllUsers()
                        var  flag:Int=0
                        for(i in 0 ..list.size-1) {
                            if(list[i].userid==it.result.email.toString())
                            {
                             flag=1
                                if(intentValue=="Signin"){
                                    val sharedPreferences: SharedPreferences? =
                                        this@GoogleSignUp?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
                                    val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
                                    myEdit?.putString("username",it.result.email.toString())
                                    myEdit?.putString("password","12345678")
                                    myEdit?.apply()


                                    val i = Intent(this@GoogleSignUp, Dashboard1::class.java).apply{
                                        putExtra("login","Google")
                                    }
                                    startActivity(i)
                                    finish()

                                }
                            }


                        }

                         if(intentValue=="Signin"&&flag==0){

                             Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT)
                                 .show()
                             val i = Intent(this@GoogleSignUp, MainActivity::class.java)
                             startActivity(i)
                             finish()

                        }
                       else if(flag==0&&intentValue=="Signup")
                       {
                           val user: User = User("\n" +it.result.displayName,it.result.email.toString(),
                               "12345678",image,"10/09/2000"
                               ,true)
                           repo.insertUser(user)
                           val sharedPreferences: SharedPreferences? =
                               this@GoogleSignUp?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
                           val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
                           myEdit?.putString("username",it.result.email.toString())
                           myEdit?.putString("password","12345678")
                           myEdit?.apply()

                           Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
                           val i = Intent(this@GoogleSignUp, Dashboard1::class.java).apply{
                               putExtra("login","Google")
                           }
                           startActivity(i)
                           finish()
                       }



                       else if(intentValue=="Signup") {
                               Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT)
                                   .show()
                               val i = Intent(this@GoogleSignUp, MainActivity::class.java)
                               startActivity(i)
                               finish()
                           }




                    } else {
                        // authentication failed

                    }

//                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                }
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

}