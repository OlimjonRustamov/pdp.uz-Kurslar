package com.tuit_21019.pdpuzkurslar.DataBase

import com.tuit_21019.pdpuzkurslar.models.Kurs

interface DbMethods {
    fun insertKurs(kurs: Kurs)

    fun getAllKurs():ArrayList<Kurs>
}