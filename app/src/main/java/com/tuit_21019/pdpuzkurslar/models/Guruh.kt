package com.tuit_21019.pdpuzkurslar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "guruhlar")
class Guruh : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    var id: Int? = null

    @ColumnInfo(name = "guruh_nomi")
    var guruh_nomi: String? = null

    @ColumnInfo(name = "mentor_id")
    var mentor_id: Int? = null

    @ColumnInfo(name = "ochilganligi")
    var ochilganligi: Int? = null

    @ColumnInfo(name = "kurs_id")
    var kurs_id: Int? = null

    @ColumnInfo(name = "dars_vaqti")
    var dars_vaqti: String? = null


    constructor()


    @Ignore
    constructor(
        guruh_nomi: String?,
        mentor_id: Int?,
        ochilganligi: Int?,
        kurs_id: Int?,
        dars_vaqti: String?
    ) {
        this.guruh_nomi = guruh_nomi
        this.mentor_id = mentor_id
        this.ochilganligi = ochilganligi
        this.kurs_id = kurs_id
        this.dars_vaqti = dars_vaqti
    }

    @Ignore
    constructor(
        id: Int?,
        guruh_nomi: String?,
        mentor_id: Int?,
        ochilganligi: Int?,
        kurs_id: Int?,
        dars_vaqti: String?
    ) {
        this.id = id
        this.guruh_nomi = guruh_nomi
        this.mentor_id = mentor_id
        this.ochilganligi = ochilganligi
        this.kurs_id = kurs_id
        this.dars_vaqti = dars_vaqti
    }

}