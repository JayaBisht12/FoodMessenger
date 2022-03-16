package com.example.messenger.db

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.example.messenger.User
import com.example.messenger.UserDAO
import com.google.api.Authentication

class UserRepository(context: Context?) {

    var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!


    //    Fetch All the Users
    fun getAllUsers(): kotlin.collections.List<User> {
        return db.getAllusers()
    }

    fun getuser(id:String): User {
        return db.getuser(id)
    }

    //Update image


    open fun updateImage(user_id: String, img:ByteArray?){

        db.updateImage(user_id,img)
    }

    open fun updateAuthentication(user_id: String, authentication: Boolean){

        db.updateAuthentication(user_id,authentication)
    }



    // Insert new user
    fun insertUser(users: User) {
        InsertAsyncTask(db).execute(users)
    }

    open fun updateUser(user_id: String, pass: String?){

        db.updateUser(user_id,pass)
    }
    open fun updateUsername(user_id: String, username: String?){

        db.updateUsername(user_id,username)
    }

     fun deleteUser(user_id: String) {
        db.deleteUser(user_id)
    }


     // update user
   // fun updateUser(users: User) {
        //db.updateUsers(users)
      //  }



    private class InsertAsyncTask internal constructor(private val usersDao: UserDAO) :
        AsyncTask<User, Void, Void>() {

        override fun doInBackground(vararg params: User): Void? {
            usersDao.insert(params[0])
            val list = usersDao.getAllusers();
            for(i in 0 ..list.size-1) {
                Log.v("\n UserRepository", "databases value " + list[i])
            }


            return null
        }
    }
}