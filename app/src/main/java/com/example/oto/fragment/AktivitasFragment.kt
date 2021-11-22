package com.example.oto.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.oto.DetailActivity
import com.example.oto.R
import com.example.oto.database.MainDatabase

class AktivitasFragment : Fragment() {

    private lateinit var db : MainDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_aktivitas,container, false)

        //listener tv tambah agenda
        view.findViewById<TextView>(R.id.tambah_agenda).setOnClickListener {
            startActivity(Intent(context, DetailActivity::class.java))
        }

        db = Room.databaseBuilder(context?.applicationContext!!, MainDatabase::class.java, "aktivitas-db").build()

        return view
    }
}