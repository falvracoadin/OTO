package com.example.oto.services

import android.app.Service
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.ResolveInfo
import android.os.Handler
import android.os.IBinder
import kotlinx.coroutines.Runnable
import java.util.*

class LockServices : Service() {

    private lateinit var appList : MutableList<ResolveInfo>
    private lateinit var packs : MutableList<PackageInfo>
    private var list = mutableListOf<String>()
    private lateinit var handler : Handler
    private lateinit var runnable: Runnable

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val packageManager = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        appList = packageManager.queryIntentActivities(mainIntent,0)

        Collections.sort(appList, ResolveInfo.DisplayNameComparator(packageManager))
        packs = packageManager.getInstalledPackages(0)

        for (i in 0..packs.size){
            val packageInfo : PackageInfo = packs[i]
            val appInfo : ApplicationInfo = packageInfo.applicationInfo
            if((ApplicationInfo.FLAG_SYSTEM and appInfo.flags) == 1){
                continue
            }
            list.add(packageInfo.packageName)
        }

    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)

    }
}