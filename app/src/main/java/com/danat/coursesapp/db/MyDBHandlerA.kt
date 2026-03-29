package com.abdmu.coursesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.abdmu.coursesapp.model.Admins
import com.abdmu.coursesapp.model.Course
import java.io.ByteArrayOutputStream
import java.util.concurrent.RecursiveTask

class MyDBHandlerA(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    private val db = writableDatabase
    private val db2 = readableDatabase

    companion object {
        const val DATABASE_NAME = "coadDb"
        const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Admins.TABLE_CREATE)
        db!!.execSQL(Course.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${Admins.TABLE_NAME}")
        onCreate(db)
    }

//**************************************************************************************************
                         //______________DML_______________

    //_____insert______

    fun insertAdmin(name: String, password: String, birthDate: String, bitmap: Bitmap): Boolean {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val cv = ContentValues()
        cv.put(Admins.COL_NAME, name)
        cv.put(Admins.COL_PASSWORD, password)
        cv.put(Admins.COL_BIRTHDATE, birthDate)
        cv.put(Admins.COL_IMAGE, byteArray)
        return db.insert(Admins.TABLE_NAME,null,cv)>0
    }


    //______isAdmin________
    fun isAdmin(name:String,password:String):Boolean{
        val c=db.rawQuery("select ${Admins.COL_ID} from ${Admins.TABLE_NAME} where ${Admins.COL_NAME}=? and ${Admins.COL_PASSWORD}=? ", arrayOf(name,password))

        return c.count > 0
    }

    fun getAdminBy(name:String,password:String):Admins{
        val c=db.rawQuery("select * from ${Admins.TABLE_NAME} where ${Admins.COL_NAME}=? and ${Admins.COL_PASSWORD}=? ", arrayOf(name,password))
        c.moveToFirst()
        val id=c.getInt(0)
        val name=c.getString(1)
        val password=c.getString(2)
        val birthDate=c.getString(3)
        val byteArray=c.getBlob(4)
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        val Admin=Admins(id,name,password,birthDate,bitmap)
        c.close()
        return Admin
    }
    //**********************************************************************************************
    fun getAdminBy(id:Int):Admins{
        val c=db.rawQuery("select * from ${Admins.TABLE_NAME} where ${Admins.COL_ID}=$id",null)
        c.moveToFirst()
        val id=c.getInt(0)
        val name=c.getString(1)
        val password=c.getString(2)
        val birthDate=c.getString(3)
        val byteArray=c.getBlob(4)
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        val Admin=Admins(id,name,password,birthDate,bitmap)
        c.close()
        return Admin
    }
    //**********************************************************************************************
    fun getAllAdmins(id: Int):ArrayList<Admins>{
        var arr=ArrayList<Admins>()
        val c=db.rawQuery("select * from ${Admins.TABLE_NAME} where ${Admins.COL_ID} != $id",null)
        c.moveToFirst()
        while (!c.isAfterLast){
            val id=c.getInt(0)
            val name=c.getString(1)
            val password=c.getString(2)
            val birthDate=c.getString(3)
            val byteArray=c.getBlob(4)
            val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            val Admin=Admins(id,name,password,birthDate,bitmap)
            arr.add(Admin)
            c.moveToNext()

        }
        c.close()

        return arr
    }



    //_______updateAdminDetails_____________________________________________________________________
      fun upDateAdmin(id:Int,Name:String,password:String,birthDate:String,bitmap: Bitmap):Boolean{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val cv=ContentValues()
        cv.put(Admins.COL_NAME,Name)
        cv.put(Admins.COL_PASSWORD,password)
        cv.put(Admins.COL_BIRTHDATE,birthDate)
        cv.put(Admins.COL_IMAGE,byteArray)
        return db.update(Admins.TABLE_NAME,cv,"${Admins.COL_ID}=$id",null)> 0
    }

    




    //_____numberOfAdmins
    fun numberOfAdmins():Int{
        return DatabaseUtils.queryNumEntries(readableDatabase,Admins.TABLE_NAME).toInt()
    }
    //*********************************************************************************************************************************************************************
    //___________________________________Course_____________________________________________________

    //insert Cousre

    fun insertCourse(name:String, category:String, description:String,instructor:String, duration:String,language:String, bitmap: Bitmap): Boolean {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val cv = ContentValues()
        cv.put(Course.COL_NAME, name)
        cv.put(Course.COL_CATEGORY, category)
        cv.put(Course.COL_DESCRIPTION, description)
        cv.put(Course.COL_INSTRUCTOR, instructor)
        cv.put(Course.COL_DURATION, duration)
        cv.put(Course.COL_LANGUAGE, language)
        cv.put(Course.COL_IMAGE, byteArray)
        return db.insert(Course.TABLE_NAME,null,cv)>0
    }

    fun getAllCourses():ArrayList<Course>{
        var arr=ArrayList<Course>()
        val c=db.rawQuery("select * from ${Course.TABLE_NAME}",null)
        c.moveToFirst()
        while (!c.isAfterLast){
         val byteArray=c.getBlob(7)
            val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            val course=Course(c.getInt(0),c.getString(1),c.getString(3),c.getString(2),c.getString(4),c.getString(5),c.getString(6),bitmap)
            arr.add(course)
            c.moveToNext()
        }
        c.close()
        return arr

    }

    fun searchForCourseBOOL(name:String):Boolean{
        val c=db.rawQuery("select * from ${Course.TABLE_NAME} where ${Course.COL_NAME}=?", arrayOf(name))
        return c.count>0

    }
    fun searchForCourse(name:String):Course{
        val c=db.rawQuery("select * from ${Course.TABLE_NAME} where ${Course.COL_NAME}=?", arrayOf(name))
        c.moveToFirst()
        val byteArray=c.getBlob(7)
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        val course=Course(c.getInt(0),c.getString(1),c.getString(3),c.getString(2),c.getString(4),c.getString(5),c.getString(6),bitmap)
        c.close()
        return  course

    }

    fun deleteCourseByName(id:Int):Boolean{

      return  db.delete(Course.TABLE_NAME,"${Course.COL_ID}=$id",null)>0

    }



    fun updateCourse(oldId:Int,name:String, category:String, description:String,instructor:String, duration:String,language:String, bitmap: Bitmap):Boolean{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val cv = ContentValues()
        cv.put(Course.COL_NAME, name)
        cv.put(Course.COL_CATEGORY, category)
        cv.put(Course.COL_DESCRIPTION, description)
        cv.put(Course.COL_INSTRUCTOR, instructor)
        cv.put(Course.COL_DURATION, duration)
        cv.put(Course.COL_LANGUAGE, language)
        cv.put(Course.COL_IMAGE, byteArray)
        return db.update(Course.TABLE_NAME,cv,"${Course.COL_ID}=$oldId",null)>0

    }










}
