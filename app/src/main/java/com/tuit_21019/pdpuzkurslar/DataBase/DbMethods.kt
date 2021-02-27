package com.tuit_21019.pdpuzkurslar.DataBase

import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import com.tuit_21019.pdpuzkurslar.models.Talaba

interface DbMethods {
    fun insertKurs(kurs: Kurs):Long

    fun getAllKurs(): ArrayList<Kurs>


    //mentor uchun methodlar
    fun insertMentor(mentor: Mentor):Long

    fun updateMentor(mentor: Mentor)
    //update qilishda o'sha mentor obyektni malumotlarini yangilash kerak
    //id o'rgarmasligi maqsadida

    fun deleteMentor(mentor: Mentor)

    fun getAllMentorsByKursId(id: Int): ArrayList<Mentor>

    fun getMentorByID(id: Int):Mentor


    //guruh uchun methodlar
    fun insertGuruh(guruh: Guruh):Long

    fun updateGuruh(updated: Guruh)

    fun deleteGuruh(guruh: Guruh)

    fun getAllGroupsByKursId(kurs_id: Int): ArrayList<Guruh>

    fun getAllGroupByStatus(ochilganligi: Int):ArrayList<Guruh>

    fun getGroupByKursIdAndStatus(ochilganligi: Int,kursID:Int):ArrayList<Guruh>

    //talaba uchun methodlar
    fun getAllStudentsByGroupId(guruh_id: Int): ArrayList<Talaba>

    fun insertTalaba(talaba: Talaba):Long

    fun updateTalaba(updated: Talaba)

    fun deleteTalaba(talaba_id: Int)
}