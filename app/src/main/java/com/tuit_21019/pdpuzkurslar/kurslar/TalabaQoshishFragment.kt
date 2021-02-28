package com.tuit_21019.pdpuzkurslar.kurslar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Talaba
import kotlinx.android.synthetic.main.fragment_add_student.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.toolbar
import kotlinx.android.synthetic.main.fragment_talaba_qoshish.view.*

private const val ARG_PARAM1 = "kurs"

class TalabaQoshishFragment : Fragment() {
    private var param1: Kurs? = null
    lateinit var root:View

    private var mentors: ArrayList<String>? = null
    private var time: ArrayList<String>? = null
    private var kunlar: ArrayList<String>? = null
    private var guruhlar: ArrayList<String>? = null
    private var db: DbHelper? = null

    private var mentorAdapter: AddGroupSpinnerAdapter? = null
    private var kunlarAdapter: AddGroupSpinnerAdapter? = null
    private var timeAdapter: AddGroupSpinnerAdapter? = null
    private var guruhlarAdapter: AddGroupSpinnerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_talaba_qoshish, container, false)

        setToolbartv()
        loadData()
        loadMentors()
        loadTime()
        loadKunlar()
        loadGuruhlar()
        calendarClick()

        addStudentClick()
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Kurs) =
            TalabaQoshishFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

    private fun setToolbartv() {
        root.toolbar.title = param1!!.kurs_nomi

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadTime() {
        timeAdapter = AddGroupSpinnerAdapter()
        timeAdapter?.setAdapter(time!!, root.context)
        root.add_student_add_time.adapter = timeAdapter
    }

    private fun loadMentors() {
        mentorAdapter = AddGroupSpinnerAdapter()
        mentorAdapter?.setAdapter(mentors!!, root.context)
        root.add_student_add_mentor.adapter = mentorAdapter
    }

    private fun loadData() {
        mentors = ArrayList()
        time = ArrayList()
        kunlar = ArrayList()
        guruhlar= ArrayList()

        db= DbHelper(root.context)
        mentors!!.add("Mentorni tanlang")
        for (i in 0 until db?.getAllMentorsByKursId(param1!!.id!!)!!.size) {
            mentors?.add(
                db?.getAllMentorsByKursId(param1!!.id!!)!![i].mentor_familyasi + " " + db?.getAllMentorsByKursId(
                    param1!!.id!!
                )!![i].mentor_nomi
            )
        }
        time?.add("Vaqti")
        time?.add("16:30 - 18:30")
        time?.add("19:00 - 21:00")

        guruhlar!!.add("Guruhlar")
        for (i in 0 until db!!.getAllGroupsByKursId(param1!!.id!!).size) {
            guruhlar!!.add(db!!.getAllGroupsByKursId(param1!!.id!!)[i].guruh_nomi!!)
        }

        kunlar!!.add("Kunlar")
        kunlar!!.add("Juft kunlar")
        kunlar!!.add("Toq kunlar")
    }
    private fun loadKunlar() {
        kunlarAdapter = AddGroupSpinnerAdapter()
        kunlarAdapter!!.setAdapter(kunlar!!,root.context)
        root.add_student_add_kunlar.adapter=kunlarAdapter
    }
    private fun loadGuruhlar() {
        guruhlarAdapter = AddGroupSpinnerAdapter()
        guruhlarAdapter!!.setAdapter(guruhlar!!, root.context)
        root.add_student_add_guruhlar.adapter=guruhlarAdapter
    }

    @SuppressLint("NewApi")
    private fun calendarClick() {
        root.add_talaba_calendar.setOnClickListener {
            val dialog = DatePickerDialog(root.context)

            dialog.datePicker.setOnDateChangedListener { datePicker, year, month, day ->
                root.add_talaba_boshlash_vaqti_et.setText("$day/${month + 1}/$year")
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun addStudentClick() {

        root.add_student_btn.setOnClickListener {
            val talaba_ismi=root.add_talaba_ismi_et.text.toString().trim()
            val talaba_familyasi=root.add_talaba_familyasi_et.text.toString().trim()
            val talaba_otasining_ismi=root.add_talaba_otasining_ismi_et.text.toString().trim()
            val dars_boshlash_vaqti=root.add_talaba_boshlash_vaqti_et.text.toString().trim()
            val t_kunlar=kunlar!![root.add_student_add_kunlar.selectedItemPosition]
            val dars_vaqti=time!![root.add_student_add_time.selectedItemPosition]

            //spinnerni positionini tekshirdim
            //agar 0 selected bo'lsa 0 ketdi. 0 bo'lmasa position-1 dagi elementni berib yubordim
            var mentor_id = 0
            if (root.add_student_add_mentor.selectedItemPosition==0) { mentor_id=0 } else {
                mentor_id=db!!.getAllMentorsByKursId(param1!!.id!!)[root.add_student_add_mentor.selectedItemPosition-1].id!!
            }
            var guruh_id = 0
            if (root.add_student_add_guruhlar.selectedItemPosition == 0) { guruh_id = 0 } else {
                guruh_id=db!!.getAllGroupsByKursId(param1!!.id!!)[root.add_student_add_guruhlar.selectedItemPosition - 1].id!!
            }


            if (talaba_ismi != "" && talaba_familyasi != "" && talaba_otasining_ismi != "" && dars_boshlash_vaqti != ""
                && mentor_id != 0 && t_kunlar != "Kunlar" && dars_vaqti != "Vaqti" && guruh_id != 0) {
                db!!.insertTalaba(Talaba(talaba_ismi,talaba_familyasi,talaba_otasining_ismi,dars_boshlash_vaqti,mentor_id,t_kunlar,dars_vaqti,guruh_id))
                Snackbar.make(root, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else {
                Snackbar.make(root,"Barcha maydonlarni to'ldiring!",Snackbar.LENGTH_LONG).show()
            }
        }
    }
}