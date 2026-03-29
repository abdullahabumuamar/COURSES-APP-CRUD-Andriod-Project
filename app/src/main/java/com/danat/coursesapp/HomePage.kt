package com.abdmu.coursesapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdmu.coursesapp.Adapter.MyAdapter
import com.abdmu.coursesapp.databinding.ActivityHomePageBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import com.abdmu.coursesapp.model.Admins
import com.abdmu.coursesapp.model.Course
import java.io.ByteArrayOutputStream

//عبدالله عدنان ابو معمر 120211482
class HomePage : AppCompatActivity() {

      lateinit var admin: Admins
      lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //هنا كان خطأ عشان المعلومات تضل محدثة نسخته في on start
        //استقبال بيانات عشان لما يروح ال home يكون في هيلو واسم الشخص
     //   val  name = intent.getStringExtra("adminName")!!
//        val sharedFile=getSharedPreferences("AdminDetails", MODE_PRIVATE)
//            val id  =sharedFile.getInt("id",-1)
//            val b1=MyDBHandlerA(this)
//           val admin= b1.getAdminBy(id)
//           binding.txtHello.text = "Hello, ${admin.name}"



        binding.btnAdd.setOnClickListener {
            val intent=Intent(this,AddCourse::class.java)
            startActivity(intent)
        }


      binding.btnSearch.setOnClickListener {
         val name=binding.txtForSearch.text.toString().trim()
          val b1=MyDBHandlerA(this)
          val b  =b1.searchForCourseBOOL(name)
          if(name.isNotEmpty()) {
              if (b) {
                  Toast.makeText(this, "is found", Toast.LENGTH_SHORT).show()
                  binding.txtForSearch.text.clear()
                  val i = Intent(this, SearchCourse::class.java)
                  val course = b1.searchForCourse(name)
                  i.putExtra("name", course.name)
                  i.putExtra("catagory", course.category)
                  i.putExtra("instructor", course.instructor)
                  i.putExtra("language", course.language)
                  i.putExtra("duration", course.duration)
                  i.putExtra("descrption", course.description)
                  val byteArrayOutputStream = ByteArrayOutputStream()
                  val bitmap = course.image
                  bitmap?.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
                  val byteArray = byteArrayOutputStream.toByteArray()
                  i.putExtra("imgByte", byteArray)
                  startActivity(i)

              } else {
                  Toast.makeText(this, " course is not found", Toast.LENGTH_SHORT).show()
                  binding.txtForSearch.text.clear()
              }

          }else{
              Toast.makeText(this, "please fill field", Toast.LENGTH_SHORT).show()

          }

     }






    }

    override fun onStart() {
        super.onStart()

        val sharedFile=getSharedPreferences("AdminDetails", MODE_PRIVATE)
        val id  =sharedFile.getInt("id",-1)
        val b1=MyDBHandlerA(this)
        val admin= b1.getAdminBy(id)
        binding.txtHello.text = "Hello, ${admin.name}"

        val b=MyDBHandlerA(this)
        var programmingArr=ArrayList<Course>()//1
        var LanguagesArr=ArrayList<Course>()//2
        var MedicalArr=ArrayList<Course>()//3

        val course =b.getAllCourses()
        for( i in 0 until course.size){
            if(course[i].category == "programming Coureses"){
                programmingArr.add(course[i])
            }
            if(course[i].category== "Languages Coureses"){
                LanguagesArr.add(course[i])
            }
            if(course[i].category == "Medical Coureses"){
                MedicalArr.add(course[i])
            }
        }



        val MyAdapter=MyAdapter(this,programmingArr)
        binding.rvProg.adapter=MyAdapter
        binding.rvProg.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        val MyAdapter2=MyAdapter(this,LanguagesArr)
        binding.rvLang.adapter=MyAdapter2
        binding.rvLang.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        val MyAdapter3=MyAdapter(this,MedicalArr)
        binding.rvMedical.adapter=MyAdapter3
        binding.rvMedical.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val b = MyDBHandlerA(this)
        when(item.itemId){
            R.id.myProfileItem ->{
                val intent=Intent(this,myProfile::class.java)
//                intent.putExtra("name",admin.name)
//                intent.putExtra("password",admin.password)
//                intent.putExtra("birthDate",admin.birthDate)

//                val stream = ByteArrayOutputStream()
//                admin.image.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val byteArray: ByteArray = stream.toByteArray()
//                intent.putExtra("image", byteArray)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}