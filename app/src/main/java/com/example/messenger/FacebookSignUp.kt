package com.example.messenger

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.login.widget.LoginButton



import com.facebook.login.LoginResult

import com.facebook.login.LoginManager
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.*


class FacebookSignUp : AppCompatActivity() {
       lateinit var callbackManager: CallbackManager
    var id = ""
    var firstName = ""
    var middleName = ""
    var lastName = ""
    var name = ""
    var picture = ""
    var email = ""
    var accessToken = ""
//    lateinit var loginButton: LoginButton
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_sign_up)
        //init
//        loginButton = findViewById(R.id.bFacebook)
//        callbackManager = CallbackManager.Factory.create()
//        loginButton.setPermissions(listOf("email", "user_birthday"))
//        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//                fun onSuccess(loginResult: LoginResult) {
//                    // App code
//                }
//
//                override fun onCancel() {
//                    // App code
//                }
//
//                override fun onError(exception: FacebookException) {
//                    // App code
//                }
//            })
//
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//}

    callbackManager = CallbackManager.Factory.create()
    LoginManager.getInstance().logOut()


    if (isLoggedIn()) {
        Log.d("LoggedIn? :", "YES")
        // Show the Activity with the logged in user
    } else {
        Log.d("LoggedIn? :", "NO")
        // Show the Home Activity
    }

//       Login Button made by Facebook
//
//
//        val loginButton = findViewById<LoginButton>(R.id.login_button)
//        loginButton.setReadPermissions(listOf("public_profile", "email"))
//        // If you are using in a fragment, call loginButton.setFragment(this)
//
//        // Callback registration
//        // If you are using in a fragment, call loginButton.setFragment(this)
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            override fun onSuccess(loginResult: LoginResult?) {
//                // Get User's Info
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//        })

//      Custom Login Button


//    facebook_login_btn.setOnClickListener {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, listOf("public_profile", "email"))
//    }


    LoginManager.getInstance().registerCallback(
        callbackManager,
        object : FacebookCallback<LoginResult?>
        // LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult?>
        {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d("TAG", "Success Login")
                getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
            }

            override fun onCancel() {
                Toast.makeText(this@FacebookSignUp, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(this@FacebookSignUp, exception.message, Toast.LENGTH_LONG).show()
            }
        }
    )
}

    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                // Facebook Access Token
                // You can see Access Token only in Debug mode.
                // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }
                accessToken = token.toString()

                // Facebook Id
                if (jsonObject != null) {
                    if (jsonObject.has("id")) {
                        val facebookId = jsonObject.getString("id")
                        Log.i("Facebook Id: ", facebookId.toString())
                        id = facebookId.toString()
                    } else {
                        Log.i("Facebook Id: ", "Not exists")
                        id = "Not exists"
                    }
                }


                // Facebook First Name
                if (jsonObject != null) {
                    if (jsonObject.has("first_name")) {
                        val facebookFirstName = jsonObject.getString("first_name")
                        Log.i("Facebook First Name: ", facebookFirstName)
                        firstName = facebookFirstName
                    } else {
                        Log.i("Facebook First Name: ", "Not exists")
                        firstName = "Not exists"
                    }
                }


                // Facebook Middle Name
                if (jsonObject != null) {
                    if (jsonObject.has("middle_name")) {
                        val facebookMiddleName = jsonObject.getString("middle_name")
                        Log.i("Facebook Middle Name: ", facebookMiddleName)
                        middleName = facebookMiddleName
                    } else {
                        Log.i("Facebook Middle Name: ", "Not exists")
                        middleName = "Not exists"
                    }
                }


                // Facebook Last Name
                if (jsonObject != null) {
                    if (jsonObject.has("last_name")) {
                        val facebookLastName = jsonObject.getString("last_name")
                        Log.i("Facebook Last Name: ", facebookLastName)
                        lastName = facebookLastName
                    } else {
                        Log.i("Facebook Last Name: ", "Not exists")
                        lastName = "Not exists"
                    }
                }


                // Facebook Name
                if (jsonObject != null) {
                    if (jsonObject.has("name")) {
                        val facebookName = jsonObject.getString("name")
                        Log.i("Facebook Name: ", facebookName)
                        name = facebookName
                    } else {
                        Log.i("Facebook Name: ", "Not exists")
                        name = "Not exists"
                    }
                }


                // Facebook Profile Pic URL
                if (jsonObject != null) {
                    if (jsonObject.has("picture")) {
                        val facebookPictureObject = jsonObject.getJSONObject("picture")
                        if (facebookPictureObject.has("data")) {
                            val facebookDataObject = facebookPictureObject.getJSONObject("data")
                            if (facebookDataObject.has("url")) {
                                val facebookProfilePicURL = facebookDataObject.getString("url")
                                Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                                picture = facebookProfilePicURL
                            }
                        }
                    } else {
                        Log.i("Facebook Profile Pic URL: ", "Not exists")
                        picture = "Not exists"
                    }
                }

                // Facebook Email
                if (jsonObject != null) {
                    if (jsonObject.has("email")) {
                        val facebookEmail = jsonObject.getString("email")
                        Log.i("Facebook Email: ", facebookEmail)
                        email = facebookEmail
                    } else {
                        Log.i("Facebook Email: ", "Not exists")
                        email = "Not exists"
                    }
                }
//
//                openDetailsActivity()
            }).executeAsync()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }


    fun logOutUser() {
        LoginManager.getInstance().logOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

//    private fun openDetailsActivity() {
//        val myIntent = Intent(this, DetailsActivity::class.java)
//        myIntent.putExtra("facebook_id", id)
//        myIntent.putExtra("facebook_first_name", firstName)
//        myIntent.putExtra("facebook_middle_name", middleName)
//        myIntent.putExtra("facebook_last_name", lastName)
//        myIntent.putExtra("facebook_name", name)
//        myIntent.putExtra("facebook_picture", picture)
//        myIntent.putExtra("facebook_email", email)
//        myIntent.putExtra("facebook_access_token", accessToken)
//        startActivity(myIntent)
 //   }
}

internal fun LoginManager.registerCallback(callbackManager: CallbackManager, callback: FacebookCallback<LoginResult?>) {

}

//fun LoginManager.registerCallback(callbackManager: CallbackManager, callback: FacebookCallback<LoginResult?>) {
//
//}

//private fun LoginManager.registerCallback(callbackManager: CallbackManager, callback: FacebookCallback<LoginResult?>) {
//
//
//
//}







