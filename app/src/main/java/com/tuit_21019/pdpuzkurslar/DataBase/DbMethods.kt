package com.tuit_21019.pdpuzkurslar.DataBase

import androidx.room.*
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import com.tuit_21019.pdpuzkurslar.models.Talaba

@Dao
interface DbMethods {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKurs(kurs: Kurs)

    @Query("SELECT * from barcha_kurslar")
    fun getAllKurs(): List<Kurs>

    //mentor uchun methodlar
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMentor(mentor: Mentor)

    @Update
    fun updateMentor(mentor: Mentor)
    //update qilishda o'sha mentor obyektni malumotlarini yangilash kerak
    //id o'rgarmasligi maqsadida

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Query("select * from mentorlar where kurs_id=:id")
    fun getAllMentorsByKursId(id: Int): List<Mentor>

    @Query("select * from mentorlar where mentor_idd=:id")
    fun getMentorByID(id: Int): Mentor


    //guruh uchun methodlar
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuruh(guruh: Guruh)

    @Query("update guruhlar set guruh_nomi=:guruhNomi,mentor_id=:mentorID,ochilganligi=:ochilganligi,kurs_id=:kursID,dars_vaqti=:darsVaqti where group_id=:groupID")
    fun updateGuruh(
        groupID: Int,
        guruhNomi: String,
        mentorID: Int,
        ochilganligi: Int,
        kursID: Int,
        darsVaqti: String
    )

    @Delete
    fun deleteGuruh(guruh: Guruh)

    @Query("select * from guruhlar where kurs_id=:kurs_id")
    fun getAllGroupsByKursId(kurs_id: Int): List<Guruh>

    @Query("select * from guruhlar where ochilganligi=:ochilganligi")
    fun getAllGroupByStatus(ochilganligi: Int): List<Guruh>

    @Query("select * from guruhlar where ochilganligi=:ochilganligi and kurs_id=:kursID")
    fun getGroupByKursIdAndStatus(ochilganligi: Int, kursID: Int): List<Guruh>

    @Query("select * from talabalar where mentor_id=:mentor_id")
    fun getAllGroupsByMentorId(mentor_id: Int): List<Guruh>


    //talaba uchun methodlar
    @Query("select * from talabalar where guruh_id=:guruh_id")
    fun getAllStudentsByGroupId(guruh_id: Int): List<Talaba>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTalaba(talaba: Talaba)

    @Query("update talabalar set talaba_ismi=:talabaIsmi , talaba_familyasi=:talabaFamiliyasi , talaba_otasining_ismi=:talabaPatronomic , dars_boshlash_vaqti=:lessonStartTime , mentor_id=:mentorID , kunlar=:kunlar , dars_vaqti=:darsVaqti , guruh_id=:guruhID where  student_id=:studentID")
    fun updateTalaba(
        studentID: Int,
        talabaIsmi: String,
        talabaFamiliyasi: String,
        talabaPatronomic: String,
        lessonStartTime: String,
        mentorID: Int,
        kunlar: String,
        darsVaqti: String,
        guruhID: Int
    )

    @Query("delete from talabalar where student_id=:talaba_id")
    fun deleteTalaba(talaba_id: Int)

    @Query("select * from talabalar where mentor_id=:mentor_id")
    fun getAllStudentsByMentorId(mentor_id: Int): List<Talaba>


}