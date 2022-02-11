package com.example.messenger

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.Uri.parse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.messenger.data.ProductService
import com.google.android.gms.common.util.HttpUtils.parse
import com.squareup.okhttp.HttpUrl.parse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.io.ByteArrayOutputStream
import java.net.HttpCookie.parse
import java.net.URI
import java.net.URL
import java.util.logging.Level.parse

class ProductDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_description)
        val ivPImage=findViewById<ImageView>(R.id.ivPImage)
        val tvPname=findViewById<TextView>(R.id.tvPname)
        val tvPdescription=findViewById<TextView>(R.id.tvPdescription)
        val tvPcategory=findViewById<TextView>(R.id.tvPcategory)
        val tvPprice=findViewById<TextView>(R.id.tvPprice)
        val intentValue = intent.getStringExtra("Data")//userid received from the signin fragment
        val i = intentValue.toString().toInt()
        val ivloading=findViewById<ImageView>(R.id.ivloading)
        val bBuyNow=findViewById<Button>(R.id.bBuyNow)
        val pro = ProductService.productsInstance.getProducts()


//
//        pro.enqueue(object : Callback<Products?> {
//            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
//                val data=response.body()!!//data from the server
//               tvPname.text=data.title
//                tvPdescription.text=data.description
//               tvPcategory.text=data.category
//               tvPprice.text=data.price.toString()
//                Picasso.get().load(data.image).into(ivPImage)
//
//
//
//            }
//
//
//            override fun onFailure(call: Call<Products?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })


      // val data=response.body()!!//data from the server
       pro.enqueue(object : Callback<List<Products>?> {
           override fun onResponse(
               call: Call<List<Products>?>,
               response: Response<List<Products>?>
           ) {
               val data=response.body()!!//data from the server
               tvPname.text=data[i].title
               tvPdescription.text=data[i].description
               tvPcategory.text=data[i].category
               tvPprice.text=data[i].price.toString()
               Picasso.get().load(data[i].image).into(ivPImage)
               ivloading.visibility= View.GONE
               bBuyNow.visibility=View.VISIBLE
           }

           override fun onFailure(call: Call<List<Products>?>, t: Throwable) {
            //   TODO("Not yet implemented")

           }
       })

        bBuyNow.setOnClickListener{
            val product=i+1
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"https://fakestoreapi.com/products/"+product.toString())
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))


        }


    }




}

