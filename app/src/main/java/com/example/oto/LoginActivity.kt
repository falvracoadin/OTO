package com.example.oto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.oto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        //add click listener to buat akun
        binding.buatAkun.setOnClickListener { moveToRegistrasi() }

        binding.loginButton.setOnClickListener{login(binding.email.text.toString(), binding.password.text.toString())}
    }

    private fun login(email: String, pass:String){
        //Verifikasi kredensial login user
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Credential tidak ditemukan", Toast.LENGTH_LONG).show()
                binding.password.setText("")
            }
        }
    }

    private fun moveToRegistrasi(){
        //Membuka RegistrasiActivity
        val intent = Intent(this, RegistrasiActivity::class.java)
        startActivity(intent)
        finish()
    }
}
