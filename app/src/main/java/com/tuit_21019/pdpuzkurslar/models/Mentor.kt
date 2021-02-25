package com.tuit_21019.pdpuzkurslar.models

class Mentor {
    var id: Int? = null
    var mentor_nomi: String? = null
    var mentor_familyasi: String? = null
    var mentor_otasining_ismi: String? = null
    var kurs_id: Int? = null

    constructor(id: Int?, mentor_nomi: String?, mentor_familyasi: String?, mentor_otasining_ismi: String?, kurs_id: Int?) {
        this.id = id
        this.mentor_nomi = mentor_nomi
        this.mentor_familyasi = mentor_familyasi
        this.mentor_otasining_ismi = mentor_otasining_ismi
        this.kurs_id = kurs_id
    }

    constructor(mentor_nomi: String?, mentor_familyasi: String?, mentor_otasining_ismi: String?, kurs_id: Int?) {
        this.mentor_nomi = mentor_nomi
        this.mentor_familyasi = mentor_familyasi
        this.mentor_otasining_ismi = mentor_otasining_ismi
        this.kurs_id = kurs_id
    }
}