package com.example.oto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.example.oto.adapter.MainPagerAdapter
import com.example.oto.database.MainApplication
import com.example.oto.database.MainDatabase
import com.example.oto.databinding.ActivityMainBinding
import com.example.oto.model.Aktivitas
import com.example.oto.model.Mahasiswa
import com.example.oto.viewmodel.AktivitasViewModel
import com.example.oto.viewmodel.AktivitasViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private var mahasiswaRef = FirebaseDatabase.getInstance("https://ontimeon-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("mahasiswa")
    private var mahasiswa  = Mahasiswa()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        if(user != null){
            mahasiswaRef.child(user.uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    mahasiswa.name = snapshot.child("name").value.toString()
                    mahasiswa.phone = snapshot.child("phone").value.toString()
                    mahasiswa.email = snapshot.child("email").value.toString()
                    mahasiswa.phone_verification = snapshot.child("phone_verification").value.toString().toBoolean()

                    binding.mainGreet.text = "Halo, " + mahasiswa.name + "!"
                    if(!mahasiswa.phone_verification){
                        val intent = Intent(this@MainActivity, OtpActivity::class.java)
                        intent.putExtra("phone", mahasiswa.phone)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "Pengambilan data pada database error!", Toast.LENGTH_LONG).show()
                }

            })
        }else{
            moveToLogin()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = MainPagerAdapter(supportFragmentManager)
        supportActionBar?.hide()
        binding.tab.setupWithViewPager(binding.pager)

        //test logout
        binding.profileImg.setOnClickListener{logout()}
    }

    private fun moveToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun logout(){
        auth.signOut()
        moveToLogin()
    }
}