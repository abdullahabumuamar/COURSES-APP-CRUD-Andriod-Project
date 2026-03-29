package com.abdmu.coursesapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdmu.coursesapp.databinding.ActivitySearchCourseBinding

class SearchCourse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySearchCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //////////////////////////////////////////
        //استقبال بيانات
       val name=intent.getStringExtra("name")
       val  catagory=intent.getStringExtra("catagory")
       val instructor=intent.getStringExtra("instructor")
      val language=intent.getStringExtra("language")
      val   duration=intent.getStringExtra("duration")
      val description=intent.getStringExtra("descrption")
        val  byteArray = intent.getByteArrayExtra("imgByte")!!
        val bitmap= BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)


        binding.serNAM.setText(name)
        binding.serCAT.setText(catagory)
        binding.serINS.setText(instructor)
        binding.seLANG.setText(language)
        binding.serDURA.setText(duration)
        binding.serDESC.setText(description)
        binding.searchIMG.setImageBitmap(bitmap)





    }
}