package com.tuit_21019.pdpuzkurslar.models

class Guruh {
    var id: Int? = null
    var guruh_nomi: String? = null
    var mentor_id: Int? = null
    var ochilganligi: String? = null
    var kurs_id: Int? = null
    var dars_vaqti: String? = null

    constructor(id: Int?, guruh_nomi: String?, mentor_id: Int?, ochilganligi: String?, kurs_id: Int?, dars_vaqti: String?) {
        this.id = id
        this.guruh_nomi = guruh_nomi
        this.mentor_id = mentor_id
        this.ochilganligi = ochilganligi
        this.kurs_id = kurs_id
        this.dars_vaqti = dars_vaqti
    }

    constructor(guruh_nomi: String?, mentor_id: Int?, ochilganligi: String?, kurs_id: Int?, dars_vaqti: String?) {
        this.guruh_nomi = guruh_nomi
        this.mentor_id = mentor_id
        this.ochilganligi = ochilganligi
        this.kurs_id = kurs_id
        this.dars_vaqti = dars_vaqti
    }
}