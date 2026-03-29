package com.abdmu.coursesapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.abdmu.coursesapp.databinding.ActivitySignUpBinding
import com.abdmu.coursesapp.db.MyDBHandlerA
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.util.Calendar

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGoToLogin.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.edDate.setOnClickListener {
            val calender=Calendar.getInstance()
            val year=calender.get(Calendar.YEAR)
            val month=calender.get(Calendar.MONTH)
            val day=calender.get(Calendar.DAY_OF_MONTH)

            val dataPiker=DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.edDate.setText("$day/${month+1}/$year")

            },year,month,day)

            dataPiker.setTitle("Select Birthday:")
            dataPiker.show()
        }


        binding.btnSignUp.setOnClickListener {
            if(binding.edName.text.isNotEmpty()&&binding.edPassword.text.isNotEmpty()&& binding.edDate.text.isNotEmpty()&&binding.chSign.isChecked){
                if(binding.edPassword.text.toString().length<8){
                    Toast.makeText(this, "PASSWORD SHOULD BE GREATER THAN 8 CHARACTER", Toast.LENGTH_SHORT).show()
                }else {
                    val MyDBHandlerA = MyDBHandlerA(this)
                    val name = binding.edName.text.toString()
                    val password = binding.edPassword.text.toString()
                    val birthDate = binding.edDate.text.toString()
                    val bitmap = AppCompatResources.getDrawable(applicationContext,R.drawable.icperson)?.toBitmap()!!
                    if (MyDBHandlerA.insertAdmin(name, password, birthDate, bitmap)){
                        Toast.makeText(this, "SIGN UP IS COMPLETED", Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "SIGN UP IS NOT COMPLETED,TRY AGAIN WITH DIFFERENT NAME", Toast.LENGTH_SHORT).show()
                    }

                   }
            }else{
                Toast.makeText(this,"FILL FIELDS TO SIGNUP",Toast.LENGTH_SHORT).show()
            }
            binding.edName.text.clear()
            binding.edPassword.text.clear()
            binding.edDate.text.clear()



        }








    }
}