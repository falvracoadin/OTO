package com.example.oto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.oto.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
import kotlin.math.floor

class OtpActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var verifId : String
    private lateinit var callback : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var timer : CountDownTimer
    private var isTimerFinished = true
    private val mahasiswaRef = FirebaseDatabase.getInstance("https://ontimeon-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("mahasiswa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code  = p0.smsCode

                if(code != null){
                    setOTP(code)
                    verifyOTP(code)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@OtpActivity, p0.message,Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verifId = p0
            }
        }

        binding = ActivityOtpBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        sendOTP()

        binding.konfirmasi.setOnClickListener{verifyOTP(getOTPFromView())}

        binding.resend.setOnClickListener{sendOTP()}
    }

    private fun startTimer(timeout : Long){
        //Start waktu untuk memasukkan kode OTP
        isTimerFinished = false
        timer = object : CountDownTimer(timeout * 1000, 1000){
            override fun onTick(p0: Long) {
                binding.timer.text = resources.getString(R.string.input_kode) + " " + floor((p0/1000).toDouble()).toString() + " s"
            }

            override fun onFinish() {
                isTimerFinished = true
                binding.timer.text = "Kirim Kembali OTP jika anda belum mendapatkan kode OTP pada nomor telepon anda."
            }
        }
        timer.start()
    }

    private fun sendOTP(timeout: Long = 60){
        //Kirim masukan OTP ke server
        if(isTimerFinished) {

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(intent.getStringExtra("phone")!!)
                .setTimeout(timeout, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callback)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
            startTimer(timeout)

        }else{
            Toast.makeText(this, "Tunggu timer selesai untuk mengirimkan kembali kode otp",Toast.LENGTH_LONG).show()
        }
    }

    private fun getOTPFromView() : String{
        return (
                binding.kode1.text.toString()
                + binding.kode2.text.toString()
                + binding.kode3.text.toString()
                + binding.kode4.text.toString()
                + binding.kode5.text.toString()
                + binding.kode6.text.toString()
                )
    }

    private fun setOTP(code : String){
        val sp_code = code.split("")
        binding.kode1.setText(sp_code[1])
        binding.kode2.setText(sp_code[2])
        binding.kode3.setText(sp_code[3])
        binding.kode4.setText(sp_code[4])
        binding.kode5.setText(sp_code[5])
        binding.kode6.setText(sp_code[6])
    }

    private fun verifyOTP(code : String){
        //Verifikasi OTP masukan user
        val credential = PhoneAuthProvider.getCredential(verifId, code)
        auth.currentUser?.linkWithCredential(credential)
            ?.addOnCompleteListener{
                if(it.isSuccessful) {
                    mahasiswaRef.child(auth.currentUser!!.uid).child("phone_verification").setValue("true")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "proses link akun gagal dilakukan",Toast.LENGTH_LONG).show()
                }
            }
    }

}
