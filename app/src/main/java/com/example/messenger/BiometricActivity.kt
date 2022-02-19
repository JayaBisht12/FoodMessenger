package com.example.messenger

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository

class BiometricActivity : AppCompatActivity() {



    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication Error:$errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Successful")
                    val i = Intent(this@BiometricActivity, Dashboard1::class.java).apply {
                        //putExtra("Data", username.text.toString())
                    }

                    startActivity(i)
                    finish()
                }

            }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)
        setTitle("Happy Shop")

        checkBiometricSupport()
        val sharedPreferences: SharedPreferences? =
            this?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        var userid:String = sharedPreferences?.getString("username",null).toString()
        var flag=  sharedPreferences?.getString("logout",null).toString().toInt()
        val textView=findViewById<TextView>(R.id.textView)
        val start_authentication=findViewById<Button>(R.id.start_authentication)
        val bEnterPass=findViewById<Button>(R.id.bEnterPass)
        val etEnterPass=findViewById<EditText>(R.id.etEnterPass)
        val btSignIn=findViewById<ImageButton>(R.id.btSignIn)
        val repo = UserRepository(this)
       val user = repo.getuser(userid)
        if(user.Authentication==true){
            start_authentication.visibility=View.VISIBLE
            textView.visibility=View.VISIBLE
        }


        start_authentication.setOnClickListener{
            val biometricPrompt:BiometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Authentication is required")
                .setDescription("This app uses fingerprint protection to keep your data secure")
                .setNegativeButton("Cancel",this.mainExecutor, DialogInterface.OnClickListener{dialog,which->
                    notifyUser("Authentication cancelled")
                }).build()
           // biometricPrompt.authenticate(getCancellationSignal().mainExecutor,authenticationCallback)
            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCallback)

        }

        bEnterPass.setOnClickListener{
            etEnterPass.visibility= View.VISIBLE
            btSignIn.visibility= View.VISIBLE
        }

        btSignIn.setOnClickListener{

            val sharedPreferences: SharedPreferences? =
                this?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
            val name = myEdit
            val password=sharedPreferences?.getString("password", null)
            if(etEnterPass.text.toString()==password && password!=null)
            {
                val i = Intent(this@BiometricActivity, Dashboard1::class.java)
                Toast.makeText(
                    this,
                    "Logged In Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(i)
                finish()
            }

            else{
                etEnterPass.error="Invalid Password"
            }

        }


    }


    private fun notifyUser(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCancellationSignal():CancellationSignal{
        cancellationSignal= CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }

        return cancellationSignal as CancellationSignal
    }


    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE)as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("FingerPrint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Fingerprint authentication is not enabled")
            return false
        }
    return if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
    {
        true
    }else true
}


}
