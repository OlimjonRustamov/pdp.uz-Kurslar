package com.tuit_21019.pdpuzkurslar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "barcha_kurslar")
class Kurs : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    var id: Int? = null

    @ColumnInfo(name = "kurs_nomi")
    var kurs_nomi: String? = null

    @ColumnInfo(name = "kurs_haqida")
    var kurs_haqida:String?=null

    @Ignore
    constructor(kurs_nomi: String?,kurs_haqida: String) {
        this.kurs_nomi = kurs_nomi
        this.kurs_haqida=kurs_haqida
    }

    constructor()


}