package com.example.messenger.data

import com.example.messenger.Products
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL="https://fakestoreapi.com/?"


interface ProductInterface {

  @GET("products")
  fun getProducts():Call<List<Products>>
  @GET("products/{id}")
   fun getProduct(@Path("id")id:Int):Call<Products>



}

object ProductService{

    val productsInstance:ProductInterface
    init {
        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productsInstance=retrofit.create(ProductInterface::class.java)
    }




}