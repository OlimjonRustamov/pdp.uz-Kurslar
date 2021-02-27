package com.tuit_21019.pdpuzkurslar.kurslar

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
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.fragment_add_group.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.toolbar
import kotlinx.android.synthetic.main.fragment_kurshaqida.view.*
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

        setToolbar_tv()
        loadData()
        loadMentors()
        loadTime()
        loadKunlar()
        loadGuruhlar()
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

    private fun setToolbar_tv() {
        root.toolbar.title = param1!!.kurs_nomi

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        root.add_student_btn.setOnClickListener {
            Snackbar.make(root,"Zerikmay turing",Snackbar.LENGTH_LONG).show()
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
}