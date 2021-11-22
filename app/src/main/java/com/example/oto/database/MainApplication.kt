package com.example.oto.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MainDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MainRepository(database!!.aktivitasDao()) }
}