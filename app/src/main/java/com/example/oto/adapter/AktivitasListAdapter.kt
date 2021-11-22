package com.example.oto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oto.R
import com.example.oto.model.Aktivitas
import java.text.SimpleDateFormat
import java.util.*

class AktivitasListAdapter(var list : List<Aktivitas>) : RecyclerView.Adapter<AktivitasListAdapter.AktivitasViewHolder>() {

    class AktivitasViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var namaAktivitas = view.findViewById<TextView>(R.id.nama_aktivitas)
        var tglAktivitas = view.findViewById<TextView>(R.id.tgl_aktivitas)
        var waktuAktivitas = view.findViewById<TextView>(R.id.waktu_aktivitas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_aktivitas, parent, false)
        return AktivitasViewHolder(view)
    }

    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val show = itemCount-1-position
        holder.namaAktivitas.text = list[show].nama
        holder.tglAktivitas.text = getDate(list[show].waktu_mulai, "d/m/y")
        holder.waktuAktivitas.text = list[show].durasi.toString()
    }

    fun getDate(milis : Long, format : String) : String{
        val formater = SimpleDateFormat(format)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milis
        return formater.format(calendar.time)
    }

    override fun getItemCount(): Int = list.size
}