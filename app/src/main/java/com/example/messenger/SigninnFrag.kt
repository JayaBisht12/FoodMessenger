package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.messenger.db.AppDatabase
import android.content.SharedPreferences

import android.content.Context.MODE_PRIVATE
import com.facebook.CallbackManager
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import com.facebook.login.LoginManager
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SigninnFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SigninnFrag : Fragment() {
    // TODO: Rename and change types of parameters
    var navc: NavController? = null
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
        // Inflate the layout for this fragment

        getActivity()?.setTitle("Sign In")

        val v: View = inflater.inflate(R.layout.fragment_signinn, container, false)


        val bSignIn = v.findViewById<Button>(R.id.bSignIn)

        val username = v.findViewById<EditText>(R.id.username)
        val password = v.findViewById<EditText>(R.id.etCurrentPass)

        val tvSignUp = v.findViewById<TextView>(R.id.tvSignUp)

        val bFb=v.findViewById<Button>(R.id.bFb)
        val bGooglee=v.findViewById<Button>(R.id.bGooglee)



        // val  preferences: SharedPreferences? = this.getActivity()?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        //val editor=preferences?.edit()
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
        val name = myEdit
        //val uID = sharedPreferences?.getString("username", null)

        var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!
        val list = db.getAllusers()
        username.setText(sharedPreferences?.getString("username", null))
        password.setText(sharedPreferences?.getString("password", null))
//        for (i in 0..list.size - 1) {
//
//            if (list[i].userid == uID)
//                temp = 1
//
////        }
        if (sharedPreferences?.getString("username", null)!= null) {
            //
                val i = Intent(this.requireContext(), Dashboard1::class.java).apply {
                    putExtra("Data", username.text.toString())
                }
                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                startActivity(i)
                activity?.finish()
        }

        bGooglee.setOnClickListener{

            val intent = Intent(this.requireContext(), GoogleSignUp::class.java).apply {
                putExtra("activity","Signin")
            }
       // Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()

        }

      bFb.setOnClickListener{


        val i =  Intent(this.requireContext(), fbSignin::class.java).apply{
            putExtra("activity","signin")
        }
          startActivity(i)
          activity?.finish()

      }

        bSignIn.setOnClickListener {

            if (username.text.toString().isEmpty()) {

                username.error = "Username is required"
            } else {

                if (password.text.toString().isEmpty()) {

                    password.error = "Password is required"

                } else {
//get all data from database

                        //list of data
                        var flag: Int = 0;
                        for (i in 0..list.size - 1) {
                            if (list[i].userid == username.text.toString())     //Compare list of data with typed username and password
                            {
                                flag = 1
                                if (list[i].userPassword == password.text.toString()) {


// Storing the key and its value as the data fetched from edittext

// Storing the key and its value as the data fetched from edittext


// Once the changes have been made,
// we need to commit to apply those changes made,
    val myEdit = sharedPreferences?.edit()
                                    myEdit?.putString("logout", "0")



                                    myEdit?.putString("username",username.text.toString())
        myEdit?.putString("password",password.text.toString())
        myEdit?.apply()

                                    //   myEdit?.commit()
                                    val i =
                                        Intent(this.requireContext(),Dashboard1::class.java).apply {
                                            putExtra("Data", username.text.toString())
                                        }
                                    Toast.makeText(
                                        context,
                                        "Logged In Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(i)
                                    activity?.finish()

                                    // Check users sign in or not
                                    // sign in


                                } else {    //if username found but password typed wrong then show error toast and it should not go to dashboard

                                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }


                        if (flag == 0) //else no user
                        {
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }


                        //if found go to dashboard ,finish login activity


                    }

               // }

            }


        }


        tvSignUp.setOnClickListener {

            navc = Navigation.findNavController(v)
            navc?.navigate(R.id.signUpFragment)

        }

        return v
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SigninnFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SigninnFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}