package com.example.oto.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "aktivitas")
class Aktivitas(
    @ColumnInfo(name = "nama") var nama : String,
    @ColumnInfo(name = "deskripsi") var deskripsi : String,
    @ColumnInfo(name = "status") var status : Int,
    @ColumnInfo(name = "waktu_mulai") var waktu_mulai : Long,
    @ColumnInfo(name = "durasi") var durasi : Long
){
    @PrimaryKey(autoGenerate = true) var id : Int? = null
}