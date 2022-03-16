package com.example.messenger

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository
import com.facebook.login.LoginManager
import java.io.ByteArrayOutputStream
import java.util.*
import androidx.annotation.NonNull
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.widget.ProfilePictureView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var ivProfilepic:ImageView
    lateinit var id:String
    lateinit var FbImage:ProfilePictureView
    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val v:View=  inflater.inflate(R.layout.fragment_dash_settings, container, false)
        val bDelete=v.findViewById<Button>(R.id.bDelete)
        var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!
        val list=db.getAllusers()
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val myEdit = sharedPreferences?.edit()
       // val name = myEdit
         id=sharedPreferences?.getString("username", null)!!
        val bLogOut= v.findViewById<Button>(R.id.bLogOut)
        val tvChangePass=v.findViewById<TextView>(R.id.tvChangePass)
        val tvUsername=v.findViewById<TextView>(R.id.tvUsername)
        val tvAge=v.findViewById<TextView>(R.id.tvAge)
         ivProfilepic=v.findViewById<ImageView>(R.id.ivProfilepic)
        val cal = Calendar.getInstance()
        val Year = cal.get(Calendar.YEAR)
        val tvShare=v.findViewById<TextView>(R.id.tvShare)
        val tvPrivacyPolicy=v.findViewById<TextView>(R.id.tvPrivacyPolicy)
         val tvTandC=v.findViewById<TextView>(R.id.tvTandC)
        val bEdit=v.findViewById<ImageButton>(R.id.bEdit)
        val toggleButton=v.findViewById<ToggleButton>(R.id.toggleButton)
        FbImage=v.findViewById<ProfilePictureView>(R.id.image)
//        Picasso.with(this)
//            .load(
//                "https://graph.facebook.com/v2.2/" + user.getUserId()
//                    .toString() + "/picture?height=120&type=normal"
//            ) //extract as User instance method
//            .transform(CropCircleTransformation())
//            .resize(120, 120)
//            .into(profilePic)


        for( i in 0..list.size-1) {      //Displayed all the data of the user into the dashboard
            if (list[i].userid == id) {  //id= received from the signin fragment and converted the id into string
                tvUsername.text=list[i].username
                tvAge.text =  (Year.toString().toInt()- list[i].userDob.takeLast(4).toInt()).toString()+" years"

                val imgbytes: ByteArray =list[i].userImage
                val bitmap = BitmapFactory.decodeByteArray(
                    imgbytes, 0,
                    imgbytes.size
                )
                ivProfilepic.setImageBitmap(bitmap)
                if(list[i].Authentication==true){
                    toggleButton.text = toggleButton.textOn
                    toggleButton.setChecked(true)
                }else{
                    toggleButton.text = toggleButton.textOff

                }
                FbImage.visibility=View.INVISIBLE
                val photo = BitmapFactory.decodeResource(
                    resources,
                    R.drawable.icon
                )
                val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image: ByteArray = stream.toByteArray()
//                if(image[0]==list.userImage[0]&&image[image.size-1]==list.userImage[list.userImage.size-1])
                val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
                if(isequal(image,list[i].userImage)&&acct!=null)
                {
                    Picasso.get().load(acct?.photoUrl).into(ivProfilepic)
                }

                else if(activity?.intent?.getStringExtra("login").toString()=="Facebook"){


                    val imgbytes: ByteArray = list[i].userImage
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
                    if(isequal(image,list[i].userImage))
                    {
                        val accessToken = AccessToken.getCurrentAccessToken()
                        val isLoggedIn = accessToken != null && !accessToken.isExpired


                        val request = GraphRequest.newMeRequest(
                            accessToken,
                            object : GraphRequest.GraphJSONObjectCallback {
                                override fun onCompleted(
                                    `object`: JSONObject?,
                                    response: GraphResponse?
                                ) {
                                    ivProfilepic.visibility=View.INVISIBLE
                                    val pic  = `object`?.getString("id").toString()
                                    FbImage.setProfileId(pic);// Application code
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
                        FbImage.visibility=View.INVISIBLE

                    }

                }



                else{
                    ivProfilepic.setImageBitmap(bitmap)

                }

            }
        }
        toggleButton.setOnClickListener{
            val repo = UserRepository(this.requireContext())


            if(toggleButton.isChecked){

                repo.updateAuthentication( id,true)
                toggleButton.text = toggleButton.textOn

            }
            else{
                repo.updateAuthentication( id,false)
                toggleButton.text = toggleButton.textOff
            }


        }
        bEdit.setOnClickListener{

           // fun showdialog(){
                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this.requireContext())
                builder.setTitle("Change name")

// Set up the input
                val input = EditText(this.requireContext())
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setHint("Enter name")
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

// Set up the buttons
                builder.setPositiveButton("Change", DialogInterface.OnClickListener { dialog, which ->
                    // Here you get get input text from the Edittext

                   if(input.text.toString().length>0){

                       val repo = UserRepository(this.requireContext())

                       repo.updateUsername( id,input.text.toString())
                       tvUsername.text = input.text.toString()
                   Toast.makeText(this.requireContext(),"Changed successfully",Toast.LENGTH_SHORT)}
                    else{

                        input.error="Name is required"
                       Toast.makeText(this.requireContext(),"Name is required",Toast.LENGTH_SHORT)
                    }

                })

                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
           // }



        }

        FbImage.setOnClickListener{


            AlertDialog.Builder(this.requireContext())
                .setTitle("Alert")

                .setMessage("Change profile picture?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Remove",DialogInterface.OnClickListener { dialog, which ->
                    val repo = UserRepository(this.requireContext())
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
                    Toast.makeText(this.context,"Removed Successfully",Toast.LENGTH_SHORT).show()


                })
                .setIcon(R.drawable.alerticon)
                .setNegativeButton("Upload/Change ",
                    DialogInterface.OnClickListener { dialog, which ->
                        val image:Intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        val resultCode = startActivityForResult(image,1)
                        onActivityResult(1,1,image)
                        ivProfilepic.visibility=View.VISIBLE
                        FbImage.visibility=View.INVISIBLE

                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

        }



        ivProfilepic.setOnClickListener{


            AlertDialog.Builder(this.requireContext())
                .setTitle("Alert")

                .setMessage("Change profile picture?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Remove",DialogInterface.OnClickListener { dialog, which ->
                    val repo = UserRepository(this.requireContext())
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
                    Toast.makeText(this.context,"Removed Successfully",Toast.LENGTH_SHORT).show()


                })
                .setIcon(R.drawable.alerticon)
                .setNegativeButton("Upload/Change ",
                    DialogInterface.OnClickListener { dialog, which ->
                        val image:Intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        val resultCode = startActivityForResult(image,1)
                        onActivityResult(1,1,image)

                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

        }


        tvShare.setOnClickListener {


            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Jaya")
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))
        }

        tvChangePass.setOnClickListener {

            val intent = Intent(this.requireContext(), ChangePassword::class.java).apply {
                putExtra("Data", id)
            }
            startActivity(intent) //starts the change password activity
        }

        tvTandC.setOnClickListener{

            val intent = Intent(this.requireContext(),demoTandC::class.java).apply {
                putExtra("flag","0")
            }
            startActivity(intent)
        }

        tvPrivacyPolicy.setOnClickListener{
            val intent = Intent(this.requireContext(),demoTandC::class.java).apply {
                putExtra("flag","1")
            }
            startActivity(intent)
        }

        bLogOut.setOnClickListener {

            val intent = Intent(this.requireContext(),RecentUserActivity::class.java)
            myEdit?.putString("logout", "1")

            myEdit?.apply()
            LoginManager.getInstance().logOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
             val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
            mGoogleSignInClient.signOut()


            Toast.makeText(context,"Logged Out Successfully",Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()



        }


        bDelete.setOnClickListener{

            AlertDialog.Builder(context)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this user?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Yes",
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation // (android.R.string.yes,
                        val repo = UserRepository(this.requireContext())
                        Toast.makeText(context,"Deleted Successfully", Toast.LENGTH_SHORT).show()
                        repo.deleteUser(id.toString())
                        myEdit?.putString("username", null)
                        myEdit?.putString("password", null)
                        myEdit?.apply()
                        LoginManager.getInstance().logOut()
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build()
                        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                        mGoogleSignInClient.signOut()
                        val intent = Intent(this.requireContext(),MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

        }




        return v
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
         * @return A new instance of fragment DashSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?)
    {    //val ivProfilepic=findViewById<ImageView>(R.id.ivProfilepic)
        super.onActivityResult(requestCode, resultCode, data)
//        val intentValue = intent.getStringExtra("Data")
//        val id: String = intentValue.toString()
        if(resultCode == AppCompatActivity.RESULT_OK){
            val selectedImageUri: Uri? = data?.getData()

            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImageUri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            bitmap.recycle()


                ivProfilepic.setImageURI(selectedImageUri)
                val repo = UserRepository(this.requireContext())
                repo.updateImage(id, byteArray)
                Toast.makeText(this.context, "Updated Successfully", Toast.LENGTH_SHORT).show()



        }
    }

}

