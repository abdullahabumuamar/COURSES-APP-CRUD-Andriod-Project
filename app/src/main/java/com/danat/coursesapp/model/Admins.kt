package com.abdmu.coursesapp.model

import android.graphics.Bitmap

data class Admins(var id:Int,var name:String,var password:String,var birthDate:String,var image:Bitmap):java.io.Serializable {

    companion object{
        const val TABLE_NAME="admins"

        const val COL_ID="id"
        const val COL_NAME="name"
        const val COL_PASSWORD="password"
        const val COL_BIRTHDATE="birthDate"
        const val COL_IMAGE="image"
        const val TABLE_CREATE= "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," + "$COL_NAME TEXT UNIQUE NOT NULL,$COL_PASSWORD TEXT NOT NULL," + "$COL_BIRTHDATE TEXT NOT NULL," + "$COL_IMAGE BLOB NOT NULL)"

    }

}