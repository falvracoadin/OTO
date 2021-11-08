package com.example.oto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.oto.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        //add click listener to buat akun
        binding.buatAkun.setOnClickListener { moveToRegistrasi() }
    }

    private fun login(email: String, pass:String){

    }

    private fun moveToRegistrasi(){
        val intent = Intent(this, RegistrasiActivity::class.java)
        startActivity(intent)
        finish()
    }
}