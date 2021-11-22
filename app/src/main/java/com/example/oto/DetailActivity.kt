package com.example.oto

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.oto.databinding.ActivityDetailBinding
import com.example.oto.model.Aktivitas
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var aktivitas : Aktivitas
    private val calendar = Calendar.getInstance()
    private val time = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        setContentView(binding.root)

        //cegah input pada time
        binding.editWaktu.inputType = InputType.TYPE_NULL

        //Mengecek extra dari intent
        if(intent.hasExtra("nama")){
            aktivitas = Aktivitas(
                intent.getStringExtra("nama")!!,
                intent.getStringExtra("deskripsi")!!,
                intent.getIntExtra("status",0),
                intent.getLongExtra("waktu_mulai",0),
                intent.getLongExtra("durasi",0)
            )
        }

        //Buat datepick listener
        val pickListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            calendar.set(Calendar.YEAR,p1)
            calendar.set(Calendar.MONTH, p2)
            calendar.set(Calendar.DAY_OF_MONTH, p3)
            updateDate()
        }

        //Listener untuk tv waktu
        binding.tvWaktu.setOnClickListener{
            DatePickerDialog(
                this,
                pickListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        //listener time pick
        val timepick = TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
            time.set(Calendar.HOUR_OF_DAY, p1)
            time.set(Calendar.MINUTE, p2)
            updateTime()
        }

        //Menambahkan pada edit waktu
        binding.editWaktu.setOnClickListener {
            TimePickerDialog(
                this,
                timepick,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                android.text.format.DateFormat.is24HourFormat(this)
            ).show()
        }


    }

    private fun updateDate(){
        val date = SimpleDateFormat(resources.getString(R.string.format_waktu))
        binding.tvWaktu.text = date.format(calendar.time)
        aktivitas.waktu_mulai = calendar.timeInMillis
    }
    private fun updateTime(){
        val format = SimpleDateFormat(resources.getString(R.string.format_jam))
        binding.editWaktu.setText(format.format(time.time))
        aktivitas.durasi = time.timeInMillis
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.simpan ->{
                val timemilis = calendar.timeInMillis + time.timeInMillis
                val current = System.currentTimeMillis()
                val replyIntent = Intent()
                if(timemilis < current){
                    print("Waktu aktivitas tidak boleh kurang dari waktu sekarang!")
                }else if(!TextUtils.isEmpty(binding.editDeskripsi.text) and !TextUtils.isEmpty(binding.tvJudul.text) and !TextUtils.isEmpty(binding.durasi.text)){
                    if(binding.durasi.text.toString().toInt() > 0) {
                        replyIntent.putExtra("nama", binding.tvJudul.text.toString())
                        replyIntent.putExtra("deskripsi", binding.editDeskripsi.text.toString())
                        replyIntent.putExtra("status", 0)
                        replyIntent.putExtra("waktu_mulai", timemilis)
                        replyIntent.putExtra("durasi", binding.durasi.text.toString())
                        setResult(Activity.RESULT_OK, replyIntent)
                        finish()
                    }else{
                        print("Durasi harus lebih dari 0")
                    }
                }else{
                    print("Input aktivitas tidak memenuhi format")
                }
            }
            R.id.hapus -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun print(message : String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
