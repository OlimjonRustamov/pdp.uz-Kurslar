package com.tuit_21019.pdpuzkurslar.DataBase

import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import com.tuit_21019.pdpuzkurslar.models.Talaba

interface DbMethods {
    fun insertKurs(kurs: Kurs)

    fun getAllKurs():ArrayList<Kurs>


    //mentor uchun methodlar
    fun insertMentor(mentor: Mentor)

    fun updateMentor(mentor: Mentor)
    //update qilishda o'sha mentor obyektni malumotlarini yangilash kerak
    //id o'rgarmasligi maqsadida

    fun deleteMentor(mentor: Mentor)

    fun getAllMentorsByKursId(id:Int):ArrayList<Mentor>


    //guruh uchun methodlar
    fun insertGuruh(guruh: Guruh)

    fun updateGuruh(updated: Guruh)

    fun deleteGuruh(guruh: Guruh)

    fun getAllGroupsByKursId(kurs_id: Int): ArrayList<Guruh>

    //talaba uchun methodlar
    fun getAllStudentsByGroupId(guruh_id:Int):ArrayList<Talaba>

    fun insertTalaba(talaba: Talaba)

    fun updateTalaba(updated: Talaba)

    fun deleteTalaba(talaba_id: Int)
}