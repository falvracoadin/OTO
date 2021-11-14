package com.example.oto

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
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
        setContentView(binding.root)

        //cegah input pada time
        binding.editWaktu.inputType = InputType.TYPE_NULL

        //Mengecek extra dari intent
        if(intent.hasExtra("nama")){
            aktivitas = Aktivitas(
                intent.getStringExtra("nama"),
                intent.getStringExtra("deskripsi"),
                intent.getIntExtra("status",0),
                intent.getLongExtra("waktu_mulai",0)
            )
        }else{
            aktivitas = Aktivitas()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

        }
        return super.onOptionsItemSelected(item)
    }
}
