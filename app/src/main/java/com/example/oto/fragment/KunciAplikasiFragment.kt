package com.example.oto.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oto.AddApk
import com.example.oto.R
import com.example.oto.adapter.KunciAplikasiAdapter
import com.example.oto.model.Aplikasi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class KunciAplikasiFragment : Fragment() {
    companion object{
        val LOCKED_APP_LIST = "Lock_app_List"
        val RESULT_ADD_APP_LOCK = 12

    }
    lateinit var appLock : ArrayList<Aplikasi>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : KunciAplikasiAdapter
    lateinit var gson : Gson
    lateinit var shared : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gson = Gson()
        shared = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val str = shared.getString(LOCKED_APP_LIST,null)
        if(str != null)
            appLock = gson.fromJson(str, ArrayList::class.java) as ArrayList<Aplikasi>
        else appLock = arrayListOf()
        var ind = 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kunci_aplikasi,container,false)
        val fb = view.findViewById<FloatingActionButton>(R.id.tambah_apk)
        fb.setImageResource(R.drawable.plus)
        fb.setOnClickListener{
            val intent = Intent(context, AddApk::class.java)
            intent.putExtra(LOCKED_APP_LIST, appLock)
            startActivityForResult(intent, RESULT_ADD_APP_LOCK);
        }


        recyclerView = view.findViewById(R.id.recycler_kunci_aplikasi)
        adapter = KunciAplikasiAdapter(appLock)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_ADD_APP_LOCK && resultCode == Activity.RESULT_OK){
            val apk = data?.getSerializableExtra(AddApk.APK_LOCK)
            appLock[appLock.size] = apk as Aplikasi
            adapter.notifyItemInserted(appLock.size-1)
        }
    }

    override fun onDestroy() {
        val str = gson.toJson(appLock)
        val editor = shared.edit()
        editor.putString(LOCKED_APP_LIST, str)
        editor.apply()
        super.onDestroy()
    }
}