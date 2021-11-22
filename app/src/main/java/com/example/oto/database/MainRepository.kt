package com.example.oto.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.oto.dao.AktivitasDao
import com.example.oto.model.Aktivitas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class MainRepository(private val aktivitasDao: AktivitasDao) {

    val allAktivitas : Flow<List<Aktivitas>> = aktivitasDao.allAktivitas()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(aktivitas:Aktivitas){
        aktivitasDao.insertAktivitas(aktivitas)
    }
}