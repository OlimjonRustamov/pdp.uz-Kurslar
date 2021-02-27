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
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter2
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
    private var db: DbHelper? = null
    private var spinnerAdapter: AddGroupSpinnerAdapter? = null
    private var spinnerAdapter2: AddGroupSpinnerAdapter2? = null

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
        spinnerAdapter2 = AddGroupSpinnerAdapter2()
        spinnerAdapter2?.setAdapter(time!!, root.context)
        root.add_student_add_time.adapter = spinnerAdapter2
    }

    private fun loadMentors() {
        spinnerAdapter = AddGroupSpinnerAdapter()
        spinnerAdapter?.setAdapter(mentors!!, root.context)
        root.add_student_add_mentor.adapter = spinnerAdapter
    }

    private fun loadData() {
        mentors = ArrayList()
        time = ArrayList()
        db=DbHelper(root.context)
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
    }
}