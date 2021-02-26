package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import kotlinx.android.synthetic.main.fragment_add_group.view.*

private const val ARG_PARAM1 = "kursID"
private const val ARG_PARAM2 = "param2"


class AddGroup : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = DbHelper(this.context!!)
        spinnerAdapter= AddGroupSpinnerAdapter()
    }

    lateinit var root: View
    private var mentors: ArrayList<String>? = null
    private var time: ArrayList<String>? = null
    private var db: DbHelper? = null
    private var spinnerAdapter:AddGroupSpinnerAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_add_group, container, false)
        loadData()
        loadMentors()
        loadTime()


        return root
    }

    private fun loadTime() {
        spinnerAdapter?.setAdapter(time!!, root.context)
        root.add_group_add_time.adapter=spinnerAdapter
    }

    private fun loadMentors() {
        spinnerAdapter?.setAdapter(mentors!!,root.context)
        root.add_group_add_mentor.adapter=spinnerAdapter
    }

    private fun loadData() {
        mentors = ArrayList()
        time = ArrayList()
        mentors!!.add("Mentorni tanlang")
        for (i in 0 until db?.getAllMentorsByKursId(param1!!)!!.size) {
            mentors?.add(
                db?.getAllMentorsByKursId(param1!!)!![i].mentor_familyasi+db?.getAllMentorsByKursId(param1!! )!![i].mentor_nomi
            )
        }
        time?.add("Vaqti")
        time?.add("16:30 - 18:30")
        time?.add("19:00 - 21:00")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGroup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}