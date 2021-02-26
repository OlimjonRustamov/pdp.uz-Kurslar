package com.tuit_21019.pdpuzkurslar.models

import java.io.Serializable

class Kurs:Serializable {
    var id: Int? = null
    var kurs_nomi: String? = null

    constructor(id: Int?, kurs_nomi: String?) {

        this.id = id
        this.kurs_nomi = kurs_nomi
    }

    constructor(kurs_nomi: String?) {
        this.kurs_nomi = kurs_nomi
    }
}