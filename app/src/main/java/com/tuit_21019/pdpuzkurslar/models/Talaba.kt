package com.tuit_21019.pdpuzkurslar.models

class Talaba {
    var id: Int? = null
    var talaba_ismi: String? = null
    var talaba_familyasi: String? = null
    var talaba_otasining_ismi: String? = null
    var dars_boshlash_vaqti: String? = null
    var mentor_id: Int? = null
    var kunlar: String? = null
    var dars_vaqti: String? = null
    var guruh_id: Int? = null

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

    constructor(id: Int?, talaba_ismi: String?, talaba_familyasi: String?, talaba_otasining_ismi: String?, dars_boshlash_vaqti: String?, mentor_id: Int?, kunlar: String?, dars_vaqti: String?, guruh_id: Int?) {
        this.id = id
        this.talaba_ismi = talaba_ismi
        this.talaba_familyasi = talaba_familyasi
        this.talaba_otasining_ismi = talaba_otasining_ismi
        this.dars_boshlash_vaqti = dars_boshlash_vaqti
        this.mentor_id = mentor_id
        this.kunlar = kunlar
        this.dars_vaqti = dars_vaqti
        this.guruh_id = guruh_id
    }
}