package com.example.messenger

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Products(

    @Expose
    @SerializedName( "id")
    var id :Int,
    @Expose
    @SerializedName( "title")
    var title:String,
    @Expose
    @SerializedName( "price")
    var price:Float,
    @Expose
    @SerializedName( "description")
    var description:String,
    @Expose
    @SerializedName( "category")
    var category:String,
    @Expose
    @SerializedName( "Img")
    var Img:String)

