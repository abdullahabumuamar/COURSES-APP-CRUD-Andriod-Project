package com.abdmu.coursesapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.abdmu.coursesapp.databinding.ActivityAddCourseBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class AddCourse : AppCompatActivity() {
    lateinit var binding:ActivityAddCourseBinding
    companion object{
        const val GALLERY_CODE=100

        var hasImage=false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnGallery.setOnClickListener {
                    openGallary()
        }


        binding.btnAddCourse.setOnClickListener {

            val  MyDBHandlerA = MyDBHandlerA(this)
            val  name=binding.coNAME.text.toString()
            val  category=binding.coCAT.selectedItem.toString()
            val  instrctor=binding.coINS.text.toString()
            val  discription=binding.coDES.text.toString()
            val language=binding.coLANG.selectedItem.toString()
            val duaration=binding.coDue.text.toString()
            if(hasImage&&name.isNotEmpty()&&instrctor.isNotEmpty()&&discription.isNotEmpty()&&duaration.isNotEmpty()){
                val bitmap=(binding.coIMG.drawable as BitmapDrawable).bitmap
                if (MyDBHandlerA.insertCourse(name,category,discription,instrctor,duaration,language,bitmap)){
                    Toast.makeText(this, "adding course is completed", Toast.LENGTH_SHORT).show()
                    binding.coNAME.text.clear()
                    binding.coINS.text.clear()
                    binding.coDES.text.clear()
                    binding.coDue.text.clear()
                    binding.coIMG.setImageResource(R.drawable.course)
                    hasImage=false
                }else{
                    Toast.makeText(this, "adding course is not completed", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "adding course is not completed ,fill all fields", Toast.LENGTH_SHORT).show()
            }
        }






    }


    //GALLARY
    private fun openGallary() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent=Intent(Intent.ACTION_PICK)
                    intent.type="image/*"
                    startActivityForResult(intent, GALLERY_CODE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@AddCourse,"sorry you can not use this feature",Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1!!.continuePermissionRequest()
                }
            }).check()


    }
    //**********************************************************************************

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_CODE ->{
                if(resultCode==Activity.RESULT_OK){
                    val imgUri=data!!.data
                    binding.coIMG.setImageURI(imgUri)
                    hasImage=true

                }

            }

            GALLERY_CODE ->{
                if(resultCode==Activity.RESULT_OK && data!!.extras !=null){
                    val img=  data.extras!!.get("data") as Bitmap
                    //هاي بت ماب
                    binding.coIMG.setImageBitmap(img)
                    hasImage=true

                }



            }
        }
    }






    //***************************************************************************************************************************************

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