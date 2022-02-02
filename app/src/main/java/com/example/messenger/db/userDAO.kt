package com.example.messenger

import android.media.Image
import android.net.Uri
import androidx.room.*
import java.util.concurrent.Flow



@Dao
interface UserDAO {

    @Query("SELECT * FROM user_table")
    fun getAllusers():List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User )

  //  @Update
    //fun updateUsers(vararg users: User)

    @Query("UPDATE user_table SET userPassword= :pass WHERE userid= :user_id")
    open fun updateUser(user_id: String, pass: String?)


    @Query("DELETE FROM user_table WHERE userid=:user_id")
    fun deleteUser(user_id: String)


    @Query("UPDATE user_table SET userImage = :img WHERE userid= :user_id")
    open fun updateImage(user_id: String, img:ByteArray?)


}