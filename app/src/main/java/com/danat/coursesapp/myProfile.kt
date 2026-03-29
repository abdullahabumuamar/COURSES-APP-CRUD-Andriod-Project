package com.abdmu.coursesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abdmu.coursesapp.databinding.ActivityMyProfileBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import com.abdmu.coursesapp.model.Admins
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.ByteArrayOutputStream
import java.util.*


class myProfile : AppCompatActivity() {
    companion object {
        const val CAMERA_CODE = 200
        var hasImage = false
    }

    lateinit var binding: ActivityMyProfileBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val sharedFile=getSharedPreferences("AdminDetails", MODE_PRIVATE)
            val  id  =sharedFile.getInt("id",-1)
        //Toast.makeText(this,"$id",Toast.LENGTH_SHORT).show()
        val b = MyDBHandlerA(this)
          val   admin=b.getAdminBy(id)
        binding.txtNameProf.setText(admin.name)
        binding.txtDate2.setText(admin.birthDate)
        binding.txtPassProf.setText(admin.password)
       binding.profImage.setImageBitmap(admin.image)


        //***************************************************************
        //DatePiker
        binding.txtDate2.setOnClickListener {
            val calender = Calendar.getInstance()
            val year = calender.get(Calendar.YEAR)
            val month = calender.get(Calendar.MONTH)
            val day = calender.get(Calendar.DAY_OF_MONTH)

            val dataPiker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    binding.txtDate2.setText("$day/${month + 1}/$year")

                }, year, month, day
            )

            dataPiker.setTitle("Select Birthday:")
            dataPiker.show()
        }
        //***************************************************************
        binding.btnCam.setOnClickListener {
            openCam()
        }

        binding.btnSaveChange.setOnClickListener {
            val alert= AlertDialog.Builder(this)
            alert.setTitle("Change Details !")
            alert.setMessage("are you sure?")
            alert.setIcon(R.drawable.ic_change)
            alert.setCancelable(false)
            alert.setPositiveButton("confirm"){_,_ ->
                val  name=binding.txtNameProf.text.toString()
                val password=binding.txtPassProf.text.toString()
                val birthdate=binding.txtDate2.text.toString()
                //***********
                var isNotfound=true
                val arr=b.getAllAdmins(id)
                for(item in arr){
                    if(item.name==name){
                        isNotfound=false
                    }
                }

                //*********

                 if(isNotfound) {
                     if (hasImage) {
                         val bitmap = (binding.profImage.drawable as BitmapDrawable).bitmap
                         if (b.upDateAdmin(id, name, password, birthdate, bitmap)) {
                             Toast.makeText(this, "Admin modified successfully", Toast.LENGTH_SHORT)
                                 .show()
                             val admin = b.getAdminBy(id)
                             binding.txtNameProf.setText(admin.name)
                             binding.txtDate2.setText(admin.birthDate)
                             binding.txtPassProf.setText(admin.password)
                             hasImage = false


                         }


                     } else {
                         Toast.makeText(
                             this,
                             "Admin not modified successfully,at least you have to change img",
                             Toast.LENGTH_SHORT
                         ).show()

                     }
                 }else{
                     Toast.makeText(this, "this name is not valid, it is existed before", Toast.LENGTH_SHORT).show()


                 }

            }
            alert.setNegativeButton("cancel"){x,_->
                x.dismiss()

            }
            alert.create().show()








        }

        binding.btnLogOut.setOnClickListener {
            val sharedFile=getSharedPreferences("AdminDetails", MODE_PRIVATE).edit()
            sharedFile.remove("Remember me").commit()


            var inte = Intent(this, MainActivity::class.java)
            inte.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(inte)
            finishAffinity()

        }


    }




//*****************************************************************************

    private fun openCam() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, CAMERA_CODE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@myProfile,
                        "sorry, you can not use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1!!.continuePermissionRequest()
                }
            }).check()
    }

    //لعرض و وضع الصورة المختارة من الpiker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            CAMERA_CODE -> {
                if (resultCode == Activity.RESULT_OK && data!!.extras != null) {
                    val img = data.extras!!.get("data") as Bitmap
                    //هاي بت ماب
                    binding.profImage.setImageBitmap(img)
                    hasImage = true

                }

            }
        }
    }


}