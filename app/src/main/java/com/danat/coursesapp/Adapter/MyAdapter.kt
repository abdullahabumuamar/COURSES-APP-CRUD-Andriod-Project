package com.abdmu.coursesapp.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abdmu.coursesapp.CourseDetails
import com.abdmu.coursesapp.MainActivity
import com.abdmu.coursesapp.R
import com.abdmu.coursesapp.databinding.ActivityAddCourseBinding
import com.abdmu.coursesapp.databinding.CourseitemBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import com.abdmu.coursesapp.model.Course
import java.io.ByteArrayOutputStream

class MyAdapter(var activity: Activity,var data:ArrayList<Course>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: CourseitemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       var binding=CourseitemBinding.inflate(activity.layoutInflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.binding.courseImg.setImageBitmap(data[position].image)
        holder.binding.cName.text=data[position].name
        holder.binding.cduration.text=data[position].duration+" Hour"
         //عرض التفاصيل
        holder.binding.root.setOnClickListener {
            val i= Intent(activity,CourseDetails::class.java)
         //   i.putExtra("course",data[position])
            i.putExtra("courseID",data[position].id)
            i.putExtra("courseName",data[position].name)
            i.putExtra("courseCat",data[position].category)
            i.putExtra("courseLang",data[position].language)
            i.putExtra("courseDisc",data[position].description)
            i.putExtra("courseInstr",data[position].instructor)
            i.putExtra("courseDuration",data[position].duration)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap=data[position].image
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            i.putExtra("imgByte",byteArray)

            activity.startActivity(i)
        }
        //حدف الكورس
        holder.binding.root.setOnLongClickListener {
            val alert= AlertDialog.Builder(activity)
            alert.setTitle("Delete !")
            alert.setMessage("are you sure?")
            alert.setIcon(R.drawable.ic_delete)
            alert.setCancelable(false)
            alert.setPositiveButton("confirm"){_,_ ->
               val b=MyDBHandlerA(activity)
            if(b.deleteCourseByName(data[position].id)){
                Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                data.removeAt(position)
                notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"Delete Failed", Toast.LENGTH_SHORT).show()
            }

            }
            alert.setNegativeButton("cancel"){x,_->
                x.dismiss()

            }
            alert.create().show()



            true
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }


}