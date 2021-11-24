package com.example.oto.adapter

import android.content.Context
import android.content.pm.ApplicationInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oto.R
import com.example.oto.model.Aplikasi

class KunciAplikasiAdapter(var list : ArrayList<Aplikasi>, val clickable : Boolean = false, val onClick: View.OnClickListener? = null) : RecyclerView.Adapter<KunciAplikasiAdapter.ViewHolder>(){
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var imageView = view.findViewById<ImageView>(R.id.logo_list_apk)
        var namaApk = view.findViewById<TextView>(R.id.nama_list_apk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_aplikasi,parent, false)
        view.setOnClickListener (onClick)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageDrawable(list[position].icon)
        holder.namaApk.text = list[position].name
    }

    override fun getItemCount(): Int = list.size
}