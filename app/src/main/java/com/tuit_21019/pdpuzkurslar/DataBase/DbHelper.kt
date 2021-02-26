package com.tuit_21019.pdpuzkurslar.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import com.tuit_21019.pdpuzkurslar.models.Talaba

class DbHelper(context: Context) : SQLiteOpenHelper(context, "pdpuz_kurslar", null, 1),DbMethods {
    override fun onCreate(db: SQLiteDatabase?) {
        val create_barcha_kurslar="CREATE TABLE `barcha_kurslar` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`kurs_nomi` TEXT NOT NULL)"
        val create_mentorlar = "CREATE TABLE `mentorlar` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`mentor_nomi` TEXT NOT NULL,`mentor_familyasi` TEXT NOT NULL,`mentor_otasining_ismi` TEXT NOT NULL, `kurs_id` INTEGER NOT NULL,FOREIGN KEY(`kurs_id`) REFERENCES `barcha_kurslar`(`id`))"
        val create_guruhlar = "CREATE TABLE `guruhlar` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`guruh_nomi` TEXT NOT NULL,`mentor_id` INTEGER NOT NULL,`ochilganligi` TEXT NOT NULL,`kurs_id` INTEGER NOT NULL,`dars_vaqti` TEXT NOT NULL,FOREIGN KEY(`kurs_id`) REFERENCES `barcha_kurslar`(`id`),FOREIGN KEY(`mentor_id`) REFERENCES `mentorlar`(`id`))"
        val create_talabalar = "CREATE TABLE `talabalar` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`talaba_ismi` TEXT NOT NULL,`talaba_familyasi` TEXT NOT NULL,`talaba_otasining_ismi` TEXT NOT NULL,`dars_boshlash_vaqti` TEXT NOT NULL,`mentor_id` INTEGER NOT NULL,`kunlar` TEXT NOT NULL,`dars_vaqti` TEXT NOT NULL,`guruh_id` INTEGER NOT NULL,FOREIGN KEY(`guruh_id`) REFERENCES `guruhlar`(`id`),FOREIGN KEY(`mentor_id`) REFERENCES `mentorlar`(`id`))"


        db?.execSQL(create_barcha_kurslar)
        db?.execSQL(create_mentorlar)
        db?.execSQL(create_guruhlar)
        db?.execSQL(create_talabalar)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table talabalar")
        db?.execSQL("drop table guruhlar")
        db?.execSQL("drop table mentorlar")
        db?.execSQL("drop table barcha_kurslar")
    }

    override fun insertKurs(kurs: Kurs) {
        val contentValues = ContentValues()
        val db = this.writableDatabase
        contentValues.put("kurs_nomi", kurs.kurs_nomi)
        db.insert("barcha_kurslar", null, contentValues)
        db.close()
    }

    override fun getAllKurs(): ArrayList<Kurs> {
        val db = this.readableDatabase
        val kursList = ArrayList<Kurs>()
        val cursor = db.rawQuery("select * from barcha_kurslar", null)
        if (cursor.moveToFirst()) {
            do {
                kursList.add(Kurs(cursor.getInt(0), cursor.getString(1)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return kursList
    }

    override fun insertMentor(mentor: Mentor) {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("mentor_nomi",mentor.mentor_nomi)
        contentValues.put("mentor_familyasi",mentor.mentor_familyasi)
        contentValues.put("mentor_otasining_ismi", mentor.mentor_otasining_ismi)
        contentValues.put("kurs_id", mentor.kurs_id)
        db.insert("mentorlar", null, contentValues)
        db.close()
    }

    override fun updateMentor(mentor: Mentor) {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("mentor_nomi",mentor.mentor_nomi)
        contentValues.put("mentor_familyasi",mentor.mentor_familyasi)
        contentValues.put("mentor_otasining_ismi", mentor.mentor_otasining_ismi)
        contentValues.put("kurs_id", mentor.kurs_id)
        db.update("mentorlar", contentValues, "id=?", arrayOf("${mentor.id}"))
        db.close()
    }

    override fun deleteMentor(mentor: Mentor) {
        val db = this.writableDatabase
        db.delete("talabalar", "mentor_id=?", arrayOf("${mentor.id}"))
        db.delete("guruhlar","mentor_id=?", arrayOf("${mentor.id}"))
        db.delete("mentorlar", "id=?", arrayOf("${mentor.id}"))
        db.close()
    }

    override fun getAllMentorsByKursId(id:Int): ArrayList<Mentor> {
        val db=this.readableDatabase
        val mentorList = ArrayList<Mentor>()
        val cursor = db.rawQuery("select * from mentorlar where id=$id", null)
        if (cursor.moveToFirst()) {
            do {
                mentorList.add(Mentor(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                id))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mentorList
    }

    override fun insertGuruh(guruh: Guruh) {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("guruh_nomi", guruh.guruh_nomi)
        contentValues.put("mentor_id", guruh.mentor_id)
        contentValues.put("ochilganligi", guruh.ochilganligi)
        contentValues.put("kurs_id", guruh.kurs_id)
        contentValues.put("dars_vaqti", guruh.dars_vaqti)
        db.insert("guruhlar", null, contentValues)
        db.close()
    }

    override fun updateGuruh(updated: Guruh) {
        val db=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("guruh_nomi", updated.guruh_nomi)
        contentValues.put("mentor_id", updated.mentor_id)
        contentValues.put("ochilganligi", updated.ochilganligi)
        contentValues.put("kurs_id", updated.kurs_id)
        contentValues.put("dars_vaqti", updated.dars_vaqti)
        db.update("guruhlar", contentValues, "id=?", arrayOf("${updated.id}"))
        db.close()
    }

    override fun deleteGuruh(guruh: Guruh) {
        val db = this.writableDatabase
        db.delete("talabalar","id=?", arrayOf("${guruh.id}"))
        db.delete("guruhlar", "id=?", arrayOf("${guruh.id}"))
        db.close()
    }

    override fun getAllGroupsByKursId(kurs_id: Int): ArrayList<Guruh> {
        val db=this.readableDatabase
        val groupByIdList = ArrayList<Guruh>()
        val cursor = db.rawQuery("select * from guruhlar where kurs_id=$kurs_id", null)
        if (cursor.moveToFirst()) {
            do {
                groupByIdList.add(Guruh(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getInt(2),
                                    cursor.getString(3),
                                    cursor.getInt(4),
                                    cursor.getString(5)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return groupByIdList
    }

    override fun getAllStudentsByGroupId(guruh_id: Int): ArrayList<Talaba> {
        val db=this.readableDatabase
        val studentsByGroupId = ArrayList<Talaba>()
        val cursor = db.rawQuery("select * from talabalar where guruh_id=$guruh_id", null)
        if (cursor.moveToFirst()) {
            do {
                studentsByGroupId.add(Talaba(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentsByGroupId
    }

    override fun insertTalaba(talaba: Talaba) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("talaba_ismi", talaba.talaba_ismi)
        contentValues.put("talaba_familyasi", talaba.talaba_familyasi)
        contentValues.put("talaba_otasining_ismi", talaba.talaba_otasining_ismi)
        contentValues.put("dars_boshlash_vaqti", talaba.dars_boshlash_vaqti)
        contentValues.put("mentor_id", talaba.mentor_id)
        contentValues.put("kunlar", talaba.kunlar)
        contentValues.put("dars_vaqti", talaba.dars_vaqti)
        contentValues.put("guruh_id", talaba.guruh_id)
        db.insert("talabalar", null, contentValues)
        db.close()
    }

    override fun updateTalaba(updated: Talaba) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("talaba_ismi", updated.talaba_ismi)
        contentValues.put("talaba_familyasi", updated.talaba_familyasi)
        contentValues.put("talaba_otasining_ismi", updated.talaba_otasining_ismi)
        contentValues.put("dars_boshlash_vaqti", updated.dars_boshlash_vaqti)
        contentValues.put("mentor_id", updated.mentor_id)
        contentValues.put("kunlar", updated.kunlar)
        contentValues.put("dars_vaqti", updated.dars_vaqti)
        contentValues.put("guruh_id", updated.guruh_id)
        db.update("talabalar", contentValues, "id=?", arrayOf("${updated.id}"))
        db.close()
    }

    override fun deleteTalaba(talaba_id: Int) {
        val db = this.writableDatabase
        db.delete("talabalar", "id=?", arrayOf("${talaba_id}"))
        db.close()
    }

}