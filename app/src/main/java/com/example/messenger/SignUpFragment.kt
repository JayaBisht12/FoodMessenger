package com.example.messenger

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository
import java.time.LocalDate
import java.util.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.ContactsContract.DisplayNameSources.EMAIL
import android.provider.MediaStore
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import java.io.ByteArrayOutputStream
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import androidx.core.app.ActivityCompat.startActivityForResult
import com.facebook.CallbackManager
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback








private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignUpFragment : Fragment() {
    var navc: NavController?= null
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        getActivity()?.setTitle("Sign Up")
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val ivDOB = v.findViewById<ImageView>(R.id.ivDOB)
        val etDob = v.findViewById<EditText>(R.id.etDob)
        val tvSignIn =v.findViewById<TextView>(R.id.tvSignIn)
        val etPassword = v.findViewById<EditText>(R.id.etPassword)
        val etConfirmPass = v.findViewById<EditText>(R.id.etNewPass)
        val etName = v.findViewById<EditText>(R.id.etName)
        val etUserId = v.findViewById<EditText>(R.id.etUserId)
        val bSignUp = v.findViewById<Button>(R.id.bSignUp)
        val cal = Calendar.getInstance()
        val Year = cal.get(Calendar.YEAR)
        val Month = cal.get(Calendar.MONTH)
        val Day = cal.get(Calendar.DAY_OF_MONTH)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()
        val loginButton = v.findViewById<Button>(R.id.bFacebook)
        val bGoogle=v.findViewById<Button>(R.id.bGoogle)
        var  mGoogleSignInClient: GoogleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this.requireContext())
         val  callbackManager = CallbackManager.Factory.create()

        loginButton.setOnClickListener{

            val intent = Intent(this.requireContext(), fbSignin::class.java).apply{
                putExtra("activity","signup")
            }
//            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()



        }



        bGoogle.setOnClickListener{

            val intent = Intent(this.requireContext(), GoogleSignUp::class.java).apply {
                putExtra("activity","Signup")
            }
//            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(intent)
//            activity?.finish()



        }


        ivDOB.setOnClickListener {
            var flag=0
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // Display Selected date in TextView


                    if(Year.toString().toInt()-year.toString().toInt()==18)
                    {
                        flag=0
                       if(Month.toString().toInt()-month.toString().toInt()==0)
                       {    flag=0
                           if(Day.toString().toInt()-dayOfMonth.toString().toInt()>=0)
                           {    flag=0

                           }
                           else{
                               flag=1
                           }
                       }


                        else{
                            if((Month.toString().toInt()+1)-(month.toString().toInt()+1)>0){

                                flag=0
                                }
                           else{
                                flag=1
                           }



                        }

                    }
                    else{
                        if (Year.toString().toInt()-year.toString().toInt()>18){
//                            if(Month.toString().toInt()-month.toString().toInt()+1>=0)
                            etDob.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year)

                        }
                        else{
                            flag=1
                        }

                    }
                     if(flag==1)
                     {
                         etDob.setText(null)
                         Toast.makeText(context,"User must be atleast 18 years old",Toast.LENGTH_SHORT).show()

                     }
                    else{

                         etDob.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year)
                    }

                },
                Year,
                Month,
                Day
            )
            dpd.show()
        }


        bSignUp.setOnClickListener {
            validateUser()


          if(etName.text.toString().isEmpty()) {
              etName.error="Name is required"
          }
            else{
                if(etDob.text.toString().isEmpty())
                {
                    etDob.error="Dob is required"
                }
              else{
                  if(etUserId.text.toString().isEmpty()){
                      etUserId.error="user id is required"
                  }
                    else
                  {
                      if(etPassword.text.toString().isEmpty())
                      {
                          etPassword.error="password is required"
                      }
                      else{

                          if(etPassword.text.toString().length<6){
                              etPassword.error="password is too short"

                          }
                          //checking if user already exists
                          else
                          {      //checking if entered user id already exist in the database
                              var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!
                              val list=db.getAllusers() //fetching all the dta from the database as a list
                              var flag:Int=0
                              for(i in 0..list.size-1){
                                  if (list[i].userid==etUserId.text.toString())
                                      flag=1 //it means that user id already exists

                              }

                              if (flag==1){
                              Toast.makeText(context,"User already exists",Toast.LENGTH_SHORT).show()
                                }

                              else{

                              if (etConfirmPass.text.toString() != etPassword.text.toString()) {
                                  etConfirmPass.error = "Both passwords are mismatched!"
                              } else {
                                  val repo = UserRepository(context)

                                  val photo = BitmapFactory.decodeResource(
                                      resources,
                                      R.drawable.icon
                                  ) //this returns null


                                  val stream = ByteArrayOutputStream()
                                  photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                                  val image: ByteArray = stream.toByteArray()
                                  var authentication:Boolean=false
                                  AlertDialog.Builder(this.requireContext())
                                      .setTitle("Please Confirm!")
                                      .setMessage("Do you want to use Biometric Authentication for login?") // Specifying a listener allows you to take an action before dismissing the dialog.
                                      // The dialog is automatically dismissed when a dialog button is clicked.
                                      .setPositiveButton("No",
                                          DialogInterface.OnClickListener { dialog, which ->
                                          authentication=false

                                              val user: User = User("\n" + etName.text.toString(), etUserId.text.toString(),
                                                  etPassword.text.toString(), image, etDob.text.toString()
                                                  ,authentication)
                                              repo.insertUser(user)
                                              //move to main activity
                                              myEdit?.putString("username",etUserId.text.toString())
                                              myEdit?.putString("password",etPassword.text.toString())
                                              myEdit?.putString("logout", "0")
                                              myEdit?.apply()
                                              val intent = Intent(this.requireContext(), Dashboard1::class.java)
                                              Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                              startActivity(intent)
                                              activity?.finish()


                                          })
                                      .setIcon(R.drawable.alerticon)
                                      .setNegativeButton("Yes ",
                                          DialogInterface.OnClickListener { dialog, which ->
                                              authentication=true
                                              val user: User = User("\n" + etName.text.toString(), etUserId.text.toString(),
                                                  etPassword.text.toString(), image, etDob.text.toString()
                                                  ,authentication)
                                              repo.insertUser(user)
                                              //move to main activity
                                              myEdit?.putString("logout", "0")



                                              myEdit?.putString("username",etUserId.text.toString())
                                              myEdit?.putString("password",etPassword.text.toString())
                                              myEdit?.apply()
                                              val intent = Intent(this.requireContext(), BiometricActivity::class.java)
                                              Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                              startActivity(intent)
                                              activity?.finish()

                                          }) // A null listener allows the button to dismiss the dialog and take no further action.

                                      .show()



                              }
                          }
                          }
                      }
                  }
              }

            }


        }











        tvSignIn.setOnClickListener{


            navc= Navigation.findNavController(v)
            navc?.navigate(R.id.signinnFrag)


        }

            return v

        }

    private fun signIn() {
        TODO("Not yet implemented")

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }



    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,personPhoto)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                bitmap.recycle()

                val repo = UserRepository(context)

                val photo = BitmapFactory.decodeResource(
                    resources,
                    R.drawable.icon
                ) //this returns null
                val sharedPreferences: SharedPreferences? =
                    activity?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                val myEdit = sharedPreferences?.edit()


                photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image: ByteArray = byteArray
                var authentication:Boolean=false
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Please Confirm!")
                    .setMessage("Do you want to use Biometric Authentication for login?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("No",
                        DialogInterface.OnClickListener { dialog, which ->
                            authentication=false

                            val user: User = User("\n" +personName,personId.toString(),
                                "12345678", image,"10/09/2000"
                                ,authentication)
                            repo.insertUser(user)
                            //move to main activity
                            myEdit?.putString("username",personId.toString())
                            myEdit?.putString("password","12345678")
                            myEdit?.putString("logout", "0")
                            myEdit?.apply()
                            val intent = Intent(this.requireContext(), Dashboard1::class.java)
                            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            activity?.finish()


                        })
                    .setIcon(R.drawable.alerticon)
                    .setNegativeButton("Yes ",
                        DialogInterface.OnClickListener { dialog, which ->
                            authentication=true
                            val user: User = User("\n" +personName,personId.toString(),
                                "12345678", image,"10/09/2000"
                                ,authentication)
                            repo.insertUser(user)
                            //move to main activity
                            myEdit?.putString("logout", "0")



                            myEdit?.putString("username",personId.toString())
                            myEdit?.putString("password","12345678")
                            myEdit?.apply()
                            val intent = Intent(this.requireContext(), BiometricActivity::class.java)
                            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            activity?.finish()

                        }) // A null listener allows the button to dismiss the dialog and take no further action.

                    .show() }

            // Signed in successfully, show authenticated UI.
          //  updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
          //  updateUI(null)
        }
    }


    private fun validateUser():Boolean {
        return false;
    }


    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
