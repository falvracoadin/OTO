package com.example.oto

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.oto.adapter.KunciAplikasiAdapter
import com.example.oto.databinding.ActivityAddApkBinding
import com.example.oto.fragment.KunciAplikasiFragment
import com.example.oto.model.Aplikasi

class AddApk : AppCompatActivity(){
    companion object{
        val APK_LOCK = "added_apk_lock"
    }

    lateinit var binding : ActivityAddApkBinding
    lateinit var listApk : MutableList<ApplicationInfo>
    lateinit var alreadyLockList : ArrayList<Aplikasi>
    lateinit var canLockList : ArrayList<Aplikasi>
    var apkAdded : Aplikasi? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddApkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        canLockList = arrayListOf()
        setUpApk()


        binding.addApk.layoutManager = GridLayoutManager(this, 3)
        binding.addApk.adapter = KunciAplikasiAdapter(canLockList, true, View.OnClickListener {
            val pos = binding.addApk.getChildAdapterPosition(it)
            apkAdded = canLockList[pos]
            onDestroy()
        })

    }

    override fun onDestroy() {
        val replyIntent = Intent()
        if(apkAdded == null) {
            replyIntent.putExtra(APK_LOCK, "noApp")
            setResult(Activity.RESULT_CANCELED, replyIntent)
        }else{
            replyIntent.putExtra(APK_LOCK, apkAdded)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        super.onDestroy()
    }

    fun setUpApk(){
        val pm = packageManager
        listApk = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        alreadyLockList = intent.extras?.get(KunciAplikasiFragment.LOCKED_APP_LIST) as ArrayList<Aplikasi>

        for(apk in listApk){
            if(pm.getLaunchIntentForPackage(apk.packageName) == null)
                continue

            val apkname = pm.getApplicationLabel(apk)
            var add = true;
            for(apk2 in alreadyLockList){
                if(apkname == apk2.name) {
                    add= false;
                    break
                }
            }
            if(add)
                canLockList.add(
                    Aplikasi(
                    pm.getApplicationIcon(apk),
                    pm.getApplicationLabel(apk).toString()
                    )
                )
        }
    }
}