package com.example.messenger

import android.app.AlertDialog
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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.db.AppDatabase
import com.example.messenger.db.UserRepository
import java.io.ByteArrayOutputStream
import java.util.*

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
        val name = myEdit
         id=sharedPreferences?.getString("username", null)!!
        val bLogOut= v.findViewById<Button>(R.id.bLogOut)
        val tvChangePass=v.findViewById<TextView>(R.id.tvChangePass)
        val tvUsername=v.findViewById<TextView>(R.id.tvUsername)
        val tvAge=v.findViewById<TextView>(R.id.tvAge)
         ivProfilepic=v.findViewById<ImageView>(R.id.ivProfilepic)
        val cal = Calendar.getInstance()
        val Year = cal.get(Calendar.YEAR)


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

            }
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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Upload/Change ",
                    DialogInterface.OnClickListener { dialog, which ->
                        val image:Intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        val resultCode = startActivityForResult(image,1)
                        onActivityResult(1,1,image)

                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

        }

        tvChangePass.setOnClickListener {

            val intent = Intent(this.requireContext(), ChangePassword::class.java).apply {
                putExtra("Data", id)
            }
            startActivity(intent) //starts the change password activity
        }

        bLogOut.setOnClickListener {

            val intent = Intent(this.requireContext(),MainActivity::class.java)

            Toast.makeText(context,"Logged Out Successfully",Toast.LENGTH_SHORT).show()
            myEdit?.putString("username", null)
            myEdit?.putString("password", null)
            myEdit?.apply()
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
                        val intent = Intent(this.requireContext(),MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }) // A null listener allows the button to dismiss the dialog and take no further action.

                .show()

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

