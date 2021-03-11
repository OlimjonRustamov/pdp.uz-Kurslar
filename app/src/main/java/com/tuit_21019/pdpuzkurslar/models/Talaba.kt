package com.tuit_21019.pdpuzkurslar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "talabalar")
class Talaba : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    var id: Int? = null

    @ColumnInfo(name = "talaba_ismi")
    var talaba_ismi: String? = null

    @ColumnInfo(name = "talaba_familyasi")
    var talaba_familyasi: String? = null

    @ColumnInfo(name = "talaba_otasining_ismi")
    var talaba_otasining_ismi: String? = null

    @ColumnInfo(name = "dars_boshlash_vaqti")
    var dars_boshlash_vaqti: String? = null

    @ColumnInfo(name = "mentor_id")
    var mentor_id: Int? = null

    @ColumnInfo(name = "kunlar")
    var kunlar: String? = null

    @ColumnInfo(name = "dars_vaqti")
    var dars_vaqti: String? = null

    @ColumnInfo(name = "guruh_id")
    var guruh_id: Int? = null

    @Ignore
    constructor(talaba_ismi: String?, talaba_familyasi: String?, talaba_otasining_ismi: String?, dars_boshlash_vaqti: String?, mentor_id: Int?, kunlar: String?, dars_vaqti: String?, guruh_id: Int?) {
        this.talaba_ismi = talaba_ismi
        this.talaba_familyasi = talaba_familyasi
        this.talaba_otasining_ismi = talaba_otasining_ismi
        this.dars_boshlash_vaqti = dars_boshlash_vaqti
        this.mentor_id = mentor_id
        this.kunlar = kunlar
        this.dars_vaqti = dars_vaqti
        this.guruh_id = guruh_id
    }
    constructor()
}