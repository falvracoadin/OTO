package com.example.oto.database

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.oto.dao.AktivitasDao
import com.example.oto.model.Aktivitas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Database(entities = [Aktivitas::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase(){
    abstract fun aktivitasDao() : AktivitasDao

    companion object{
        private var INSTANCE : MainDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope) : MainDatabase?{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "aktivitas-db"
                ).addCallback(MainDatabaseCallback(scope)).build()
                INSTANCE = instance
                INSTANCE
            }
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }

    private class MainDatabaseCallback(
        private val scope:CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    populateDatabase(it.aktivitasDao())
                }
            }
        }
        suspend fun populateDatabase(aktivitasDao: AktivitasDao){
            aktivitasDao.deleteAll()

            var dummy = Aktivitas(
                "aktivitas 1",
                "ini adalah aktivitas pertama",
                0,
                System.currentTimeMillis(),
                1*60*60*1000
            )
            aktivitasDao.insertAktivitas(dummy)
        }
    }
}