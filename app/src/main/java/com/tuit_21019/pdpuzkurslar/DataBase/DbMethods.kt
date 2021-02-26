package com.tuit_21019.pdpuzkurslar.DataBase

import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor

interface DbMethods {
    fun insertKurs(kurs: Kurs)

    fun getAllKurs():ArrayList<Kurs>

    fun insertMentor(mentor: Mentor)

    fun updateMentor(mentor: Mentor)
    //update qilishda o'sha mentor obyektni malumotlarini yangilash kerak
    //id o'rgarmasligi maqsadida

    fun deleteMentor(mentor: Mentor)

    fun getAllMentorsByKursId(id:Int):ArrayList<Mentor>
}