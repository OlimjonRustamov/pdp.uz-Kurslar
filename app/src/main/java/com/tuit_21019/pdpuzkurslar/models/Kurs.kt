package com.tuit_21019.pdpuzkurslar.models

import java.io.Serializable

class Kurs : Serializable {
    var id: Int? = null
    var kurs_nomi: String? = null
    var kurs_haqida:String?=null

    constructor(id: Int?, kurs_nomi: String?,kurs_haqida:String) {
        this.id = id
        this.kurs_nomi = kurs_nomi
        this.kurs_haqida=kurs_haqida
    }

    constructor(kurs_nomi: String?,kurs_haqida: String) {
        this.kurs_nomi = kurs_nomi
        this.kurs_haqida=kurs_haqida
    }
}