package com.example.messenger

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.graphics.drawable.BitmapDrawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.media.Image
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide.with
import com.squareup.picasso.Picasso.LoadedFrom
import kotlin.annotation.Target as Target1
import com.facebook.GraphResponse

import org.json.JSONObject

import com.facebook.GraphRequest
import com.facebook.AccessToken
import com.facebook.login.widget.ProfilePictureView
import jp.wasabeef.picasso.transformations.CropCircleTransformation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var id:String
    lateinit var list : User
    lateinit var tvName :TextView
    lateinit var ivProfilepic:ImageView

    lateinit var tvDob :TextView
   lateinit var tvUserId :TextView
   lateinit var FbProfile:ProfilePictureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_dash_home, container, false)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()  // Creating an Editor object to edit(write to the file)
        //val name = myEdit
        val intentValue = activity?.intent?.getStringExtra("login")
        var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!
        FbProfile=v.findViewById<ProfilePictureView>(R.id.image)
         id= sharedPreferences?.getString("username", null).toString()
        list = db.getuser(id.toString())
        tvName = v.findViewById<TextView>(R.id.tvName)
         ivProfilepic = v.findViewById<ImageView>(R.id.ivProfilepic)

         tvDob = v.findViewById<TextView>(R.id.tvDOB)
         tvUserId = v.findViewById<TextView>(R.id.tvUserId)

        if(intentValue=="Google")

        {
            Toast.makeText(context, "google function", Toast.LENGTH_SHORT).show()
            Google()
        }
        if(intentValue=="Facebook")
        {
            Facebook()
        }

        if(intentValue==null)
        {
            if (list.userid == id) {  //id= received from the signin fragment and converted the id into string
                tvName.text = list.username
                tvDob.text = list.userDob
                tvUserId.text = list.userid
                val imgbytes: ByteArray = list.userImage
                val bitmap = BitmapFactory.decodeByteArray( //converting the byte array into image
                    imgbytes, 0,
                    imgbytes.size
                )
                ivProfilepic.setImageBitmap(bitmap)
            }
        }

    return v


}

    private fun Facebook() {

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if (list.userid == id) {  //id= received from the signin fragment and converted the id into string
            tvName.text = list.username
            tvDob.text = list.userDob
            tvUserId.text = list.userid
            val imgbytes: ByteArray = list.userImage
            val bitmap = BitmapFactory.decodeByteArray( //converting the byte array into image
                imgbytes, 0,
                imgbytes.size
            )
            ivProfilepic.setImageBitmap(bitmap)
            val photo = BitmapFactory.decodeResource(
                resources,
                R.drawable.icon
            )
            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image: ByteArray = stream.toByteArray()
            if(isequal(image,list.userImage))
            {
                val request = GraphRequest.newMeRequest(
                    accessToken,
                    object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(
                            `object`: JSONObject?,
                            response: GraphResponse?
                        ) {
                            ivProfilepic.visibility=View.INVISIBLE
                            val pic  = `object`?.getString("id").toString()
                            FbProfile.setProfileId(pic);// Application code
//                    Picasso.get()
//                        .load(
//                          "http://graph.facebook.com/${`object`?.getString("id")}/picture?width=120&height=120&redirect=false"
//                        ) //extract as User instance method
//                        .transform(CropCircleTransformation())
//                        .resize(120, 120)
//                        .into(FbProfile)

                        }
                    })
                val parameters = Bundle()
                parameters.putString("fields", "id,name,link")
                request.parameters = parameters
                request.executeAsync()
            }


            else{
                ivProfilepic.setImageBitmap(bitmap)
                FbProfile.visibility=View.INVISIBLE

            }

        }

    }

    fun Google(){
        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl

        }


//        for (i in 0..list.size - 1) {      //Displayed all the data of the user into the dashboard
        if (list.userid == id) {  //id= received from the signin fragment and converted the id into string
            tvName.text = list.username
            tvDob.text = list.userDob
            tvUserId.text = list.userid
            val imgbytes: ByteArray = list.userImage
            val bitmap = BitmapFactory.decodeByteArray( //converting the byte array into image
                imgbytes, 0,
                imgbytes.size
            )
            val photo = BitmapFactory.decodeResource(
                resources,
                R.drawable.icon
            )
            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image: ByteArray = stream.toByteArray()
//                if(image[0]==list.userImage[0]&&image[image.size-1]==list.userImage[list.userImage.size-1])
            if(isequal(image,list.userImage)&&acct!=null)
            {
                Picasso.get().load(acct?.photoUrl).into(ivProfilepic)
            }


            else{
                ivProfilepic.setImageBitmap(bitmap)

            }

//               ivProfilepic.setImageBitmap(bitmap)



//                if(ivProfilepic.getDrawingCache()!=null) {
//                    val bmp: Bitmap = ivProfilepic.getDrawingCache()!!
//
//                    val stream: ByteArrayOutputStream = ByteArrayOutputStream()
//                    bmp.compress(Bitmap.CompressFormat.PNG, 98, stream)
//                    var byteArray: kotlin.ByteArray? = stream.toByteArray()
//                    bmp.recycle()
//                }

        }
//        }

    }

    fun isequal(newArray:ByteArray,oldArray:ByteArray):Boolean
    {
        for (i in 0..63) {
            if (newArray[i] != oldArray[i]) {
                return false
            }
        }

        return true

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}