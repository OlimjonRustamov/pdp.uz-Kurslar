package com.tuit_21019.pdpuzkurslar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "mentorlar")
class Mentor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_idd")
    var id: Int? = null

    @ColumnInfo(name = "mentor_nomi")
    var mentor_nomi: String? = null

    @ColumnInfo(name = "mentor_familiyasi")
    var mentor_familyasi: String? = null

    @ColumnInfo(name = "mentor_otasining_ismi")
    var mentor_otasining_ismi: String? = null

    @ColumnInfo(name = "kurs_id")
    var kurs_id: Int? = null

    @Ignore
    constructor(mentor_nomi: String?, mentor_familyasi: String?, mentor_otasining_ismi: String?, kurs_id: Int?) {
        this.mentor_nomi = mentor_nomi
        this.mentor_familyasi = mentor_familyasi
        this.mentor_otasining_ismi = mentor_otasining_ismi
        this.kurs_id = kurs_id
    }

    constructor()


}