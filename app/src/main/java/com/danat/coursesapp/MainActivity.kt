package com.abdmu.coursesapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.abdmu.coursesapp.databinding.ActivityMainBinding
import com.abdmu.coursesapp.db.MyDBHandlerA

//تبع تسجيل الدخول

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGoToSign.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
               finish()

                }


        val sharedFile=getSharedPreferences("AdminDetails", MODE_PRIVATE)
        if(sharedFile.contains("Remember me")){
            val intent=Intent(this,HomePage::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnlogin.setOnClickListener {
            val b = MyDBHandlerA(this)
            val name = binding.logName.text.toString()
            val password = binding.logPassword.text.toString()
            if(name.isNotEmpty()&&password.isNotEmpty()) {
                if (b.isAdmin(name, password)) {
                    var i = Intent(this, HomePage::class.java)
                    val admin = b.getAdminBy(name, password)
                    //   i.putExtra("adminName",admin.name)
                    val sharedFile = getSharedPreferences("AdminDetails", MODE_PRIVATE)
                    val editor = sharedFile.edit()
                    val b = editor.putInt("id", admin.id).commit()
                    if (binding.remeber.isChecked) {
                        val c = editor.putBoolean("Remember me", true).commit()

                    }

                    startActivity(i)
                    finish()

                } else {
                    Toast.makeText(this, "Error in name & password ,try again", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Please fill fields", Toast.LENGTH_SHORT).show()

            }


            binding.logName.text.clear()
            binding.logPassword.text.clear()


        }





    }
}