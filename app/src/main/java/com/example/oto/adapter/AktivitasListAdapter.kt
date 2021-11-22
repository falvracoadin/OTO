package com.example.oto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oto.R
import com.example.oto.model.Aktivitas
import java.text.SimpleDateFormat
import java.util.*

class AktivitasListAdapter : ListAdapter<Aktivitas, AktivitasListAdapter.AktivitasViewHolder>(AktivitasComparator()){
    class AktivitasViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val namaAktivitas : TextView = view.findViewById(R.id.nama_aktivitas)
        private val tglAktivitas : TextView = view.findViewById(R.id.tgl_aktivitas)
        private val waktuAktivitas : TextView = view.findViewById(R.id.waktu_aktivitas)

        fun bind(namaAktivitas : String?, tglAktivitas:Long, waktuAktivitas : Long){
            this.namaAktivitas.text = namaAktivitas
            this.tglAktivitas.text = getDate(tglAktivitas, "dd/MM/yyyy")
            this.waktuAktivitas.text = (waktuAktivitas /(1000 * 60)).toString()
        }
        companion object{
            fun create(parent: ViewGroup) : AktivitasViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_aktivitas, parent, false)
                return AktivitasViewHolder(view)
            }
        }
        fun getDate(milis : Long, format : String) : String{
            val formater = SimpleDateFormat(format)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milis
            return formater.format(calendar.time)
        }
    }

    class AktivitasComparator : DiffUtil.ItemCallback<Aktivitas>(){
        override fun areItemsTheSame(oldItem: Aktivitas, newItem: Aktivitas): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Aktivitas, newItem: Aktivitas): Boolean {
            return (oldItem.nama == newItem.nama
                    && oldItem.deskripsi == newItem.deskripsi
                    && oldItem.waktu_mulai == newItem.waktu_mulai)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        return AktivitasViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nama, current.waktu_mulai, current.durasi)
    }

}