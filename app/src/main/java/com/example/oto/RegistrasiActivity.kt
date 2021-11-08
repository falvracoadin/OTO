package com.example.oto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.oto.databinding.ActivityRegistrasiBinding
import com.example.oto.model.Mahasiswa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrasiBinding
    private lateinit var auth : FirebaseAuth
    private var mahasiswaRef = FirebaseDatabase.getInstance("https://ontimeon-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("mahasiswa")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.registrasiButton.setOnClickListener{
            when {
                isInputInvalid() -> {
                    Toast.makeText(this, "Input yang anda masukkan tidak valid",Toast.LENGTH_SHORT).show()
                }
                binding.checkBox.isChecked -> {
                    register(
                        binding.email.text.toString(),
                        binding.phone.text.toString(),
                        binding.password.text.toString()
                    )
                }
                else -> Toast.makeText(this, "Anda belum menyetujui aturan penggunaan aplikasi!",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isInputInvalid() : Boolean{
        return (
                TextUtils.isEmpty(binding.name.text.toString())
                or TextUtils.isEmpty(binding.email.text.toString())
                or TextUtils.isEmpty(binding.password.text.toString())
                or TextUtils.isEmpty(binding.phone.text.toString())
                or (binding.password.text.toString().length < 8)
                or (binding.name.text.toString().length < 4)
                or (binding.phone.text.toString().length < 11)
                )
    }

    private fun register(email: String, phone : String, pass : String){
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    moveToOTP(phone)
                }else{
                    Toast.makeText(this,"Pendaftaran Gagal dilakukan", Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun moveToOTP(phone: String){
        val intent = Intent(this, OtpActivity::class.java)
        intent.putExtra("phone", "+62"+phone)
        val mahasiswa = Mahasiswa(
            binding.name.text.toString(),
            binding.email.text.toString(),
            phone
        )
        mahasiswaRef.child(auth.currentUser!!.uid).setValue(mahasiswa)
        mahasiswaRef.child(auth.currentUser!!.uid).child("phone_verification").setValue(false)
        startActivity(intent)
        finish()
    }
}