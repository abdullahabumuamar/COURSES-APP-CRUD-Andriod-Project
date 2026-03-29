package com.abdmu.coursesapp.model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import org.intellij.lang.annotations.Language

data class Course(var id:Int,var name:String?,var category:String?,var description:String?,var instructor:String?,var duration:String?,var language:String?,var image: Bitmap?):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(instructor)
        parcel.writeString(duration)
        parcel.writeString(language)
        parcel.writeParcelable(image, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {


        const val TABLE_NAME="courses"

        const val COL_ID="id"
        const val COL_NAME="name"
        const val COL_CATEGORY="category"
        const val COL_DESCRIPTION="description"
        const val COL_INSTRUCTOR="instructor"
        const val COL_DURATION="duration"
        const val COL_LANGUAGE="language"
        const val COL_IMAGE="image"


        const val TABLE_CREATE= "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME TEXT UNIQUE NOT NULL,$COL_DESCRIPTION TEXT NOT NULL,$COL_CATEGORY TEXT NOT NULL," +
                "$COL_INSTRUCTOR TEXT NOT NULL,$COL_DURATION TEXT NOT NULL, " +
                "$COL_LANGUAGE TEXT NOT NULL," +
                "$COL_IMAGE BLOB NOT NULL)"















        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}