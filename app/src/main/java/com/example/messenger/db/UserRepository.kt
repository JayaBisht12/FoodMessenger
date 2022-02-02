package com.example.messenger.db

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.example.messenger.User
import com.example.messenger.UserDAO

class UserRepository(context: Context?) {

    var db: UserDAO = AppDatabase.getInstance(context)?.userDao()!!


    //    Fetch All the Users
    fun getAllUsers(): kotlin.collections.List<User> {
        return db.getAllusers()
    }

    //Update image

    open fun updateImage(user_id: String, img:ByteArray?){

        db.updateImage(user_id,img)
    }


    // Insert new user
    fun insertUser(users: User) {
        InsertAsyncTask(db).execute(users)
    }

    open fun updateUser(user_id: String, pass: String?){

        db.updateUser(user_id,pass)
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
          Log.v("UserRepository","databases value "+list)



            return null
        }
    }
}