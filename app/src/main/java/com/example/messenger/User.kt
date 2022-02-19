package com.example.messenger

import android.media.Image
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.api.Authentication


@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "username")val username:String,
    @PrimaryKey @ColumnInfo(name = "userid")val userid:String,
    @ColumnInfo(name = "userPassword") var userPassword:String,
    @ColumnInfo(name = "userImage")val userImage: ByteArray,
    @ColumnInfo(name = "userDob")val userDob:String,
    @ColumnInfo(name="Authentication")val Authentication:Boolean,




)