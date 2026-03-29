package com.abdmu.coursesapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.abdmu.coursesapp.databinding.ActivityCourseDetailsBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import com.abdmu.coursesapp.model.Course

class CourseDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityCourseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
   //    val course=intent.getParcelableExtra<Course>("course")!!
        // هاذ الجزء الخاص التفاصيل تبع الكورس عن الدعس على الكورس
        val id=intent.getIntExtra("courseID",-1)
        val courseName=intent.getStringExtra("courseName")
        val courseCategory=intent.getStringExtra("courseCat")
        val courseLang=intent.getStringExtra("courseLang")
        val courseInstrcto=intent.getStringExtra("courseInstr")
        val courseduration=intent.getStringExtra("courseDuration")
        val courseDescription=intent.getStringExtra("courseDisc")
        val  byteArray = intent.getByteArrayExtra("imgByte")!!
        val bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)



        binding.detName.setText(courseName)
        binding.detCategory.setText(courseCategory)
        binding.detLanguage.setText(courseLang)
        binding.detInstructor.setText(courseInstrcto)
        binding.detDuration.setText(courseduration+" Hour")
        binding.detDescription.setText(courseDescription)
       binding.detImg.setImageBitmap(bitmap)
        //***************************************













        binding.btnEditCourse.setOnClickListener {
           val intent=Intent(this,EditCoures::class.java)
            intent.putExtra("cId",id)
          //  Toast.makeText(this,"$id",Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.myProfileItem ->{
                val intent= Intent(this,myProfile::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}