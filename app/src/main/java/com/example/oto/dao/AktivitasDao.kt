package com.example.oto.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.oto.model.Aktivitas
import kotlinx.coroutines.flow.Flow

@Dao
interface AktivitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAktivitass(aktivitas:Aktivitas)

    @Insert
    fun insertAktivitas(aktivitas: Aktivitas)

    @Update
    fun updateAktivitas(aktivitas : Aktivitas)

    @Delete
    fun deleteAktivitas(aktivitas: Aktivitas)

    @Query("select * from aktivitas order by waktu_mulai asc")
    fun allAktivitas() : Flow<List<Aktivitas>>

    @Query("delete from aktivitas")
    fun deleteAll()
}