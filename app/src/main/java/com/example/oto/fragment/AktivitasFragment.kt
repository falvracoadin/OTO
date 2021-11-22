package com.example.oto.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.oto.DetailActivity
import com.example.oto.R
import com.example.oto.adapter.AktivitasListAdapter
import com.example.oto.database.MainApplication
import com.example.oto.database.MainDatabase
import com.example.oto.model.Aktivitas
import com.example.oto.viewmodel.AktivitasViewModel
import com.example.oto.viewmodel.AktivitasViewModelFactory

class AktivitasFragment : Fragment() {

    private lateinit var db : MainDatabase
    private lateinit var viewModel: AktivitasViewModel
    private lateinit var recyclerView: RecyclerView
    private val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AktivitasViewModelFactory((context?.applicationContext as MainApplication).repository).create(AktivitasViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_aktivitas,container, false)
        recyclerView = view.findViewById(R.id.recycler_aktivitas_mendatang)
        val adapter = AktivitasListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.allAktivitas.observe(viewLifecycleOwner){
            aktivitass -> aktivitass.let {
                adapter.submitList(it)
            }
        }

        //listener tv tambah agenda
        view.findViewById<TextView>(R.id.tambah_agenda).setOnClickListener {
            startActivity(Intent(context, DetailActivity::class.java))
        }

        db = Room.databaseBuilder(context?.applicationContext!!, MainDatabase::class.java, "aktivitas-db").build()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == this.requestCode && resultCode == Activity.RESULT_OK){
            val aktivitas = Aktivitas(
                data?.getStringExtra("nama").toString(),
                data?.getStringExtra("deskripsi").toString(),
                data?.getIntExtra("status",0)!!,
                data.getLongExtra("waktu_mulai",0),
                data.getLongExtra("durasi",1)
            )
            viewModel.insert(aktivitas)
        }else{
            Toast.makeText(context, "Gagal menambahkan aktivitas baru", Toast.LENGTH_LONG).show()
        }
    }
}