package com.example.messenger

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.Uri.parse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
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
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Products"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val ivPImage=findViewById<ImageView>(R.id.ivPImage)
        val tvPname=findViewById<TextView>(R.id.tvPname)
        val tvPdescription=findViewById<TextView>(R.id.tvPdescription)
        val tvPcategory=findViewById<TextView>(R.id.tvPcategory)
        val tvPprice=findViewById<TextView>(R.id.tvPprice)
        val intentValue = intent.getStringExtra("Data")//userid received from the recycler view(products fragment)
        val i = intentValue.toString().toInt()+1
        val tvLoading=findViewById<TextView>(R.id.tvLoading)
        val bBuyNow=findViewById<Button>(R.id.bBuyNow)
        val pro = ProductService.productsInstance.getProduct(i)
        val pBar=findViewById<ProgressBar>(R.id.pBar)


//
        pro.enqueue(object : Callback<Products?> {
            override fun onResponse(call: Call<Products?>, response: Response<Products?>) {
                val data=response.body()!!//data from the server
                actionbar!!.title =data.title
               tvPname.text=data.title
                tvPdescription.text=data.description
               tvPcategory.text=data.category
               tvPprice.text=data.price.toString()
                Picasso.get().load(data.image).into(ivPImage)
                tvLoading.visibility= View.GONE
                pBar.visibility=View.GONE
                bBuyNow.visibility=View.VISIBLE
                tvPname.visibility=View.VISIBLE
                tvPdescription.visibility=View.VISIBLE
                tvPcategory.visibility=View.VISIBLE
                tvPprice.visibility=View.VISIBLE
                ivPImage.visibility=View.VISIBLE



            }


            override fun onFailure(call: Call<Products?>, t: Throwable) {
                tvLoading.text="Oops Something went wrong!"
                Toast.makeText(this@ProductDescription, "Failed to retrieve details " + t.message, Toast.LENGTH_SHORT).show()
            }
        })



        bBuyNow.setOnClickListener{
            val product=i
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"https://fakestoreapi.com/products/"+product.toString())
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))


        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

}

