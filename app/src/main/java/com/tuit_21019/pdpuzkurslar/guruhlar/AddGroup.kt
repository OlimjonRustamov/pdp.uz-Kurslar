package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter2
import com.tuit_21019.pdpuzkurslar.models.Guruh
import kotlinx.android.synthetic.main.fragment_add_group.view.*
import kotlinx.android.synthetic.main.fragment_add_group.view.toolbar
import kotlinx.android.synthetic.main.fragment_guruhlar.view.*

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
    }

    lateinit var root: View
    private var mentors: ArrayList<String>? = null
    private var time: ArrayList<String>? = null
    private var db: DbHelper? = null
    private var spinnerAdapter: AddGroupSpinnerAdapter? = null
    private var spinnerAdapter2: AddGroupSpinnerAdapter2? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_add_group, container, false)

        toolbarConfiguration()
        loadData()
        loadMentors()
        loadTime()
        onSaveClick()


        return root
    }

    private fun toolbarConfiguration() {
        root.toolbar.menu.getItem(0).isVisible = false
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onSaveClick() {
        root.add_group_save.setOnClickListener {
            var courseName = root.add_group_course_name.text.toString()
            var mentor = mentors!![root.add_group_add_mentor.selectedItemPosition]
            var time = time!![root.add_group_add_time.selectedItemPosition]
            var mentorID = 0

            if (courseName.isNotEmpty() && !mentor.equals("Mentorni tanlang",true) && !time.equals("Vaqti", true)) {
                for (i in 0 until db?.getAllMentorsByKursId(param1!!)!!.size) {
                    if (mentor.equals(
                            db!!.getAllMentorsByKursId(param1!!)[i].mentor_familyasi + " " + db!!.getAllMentorsByKursId(
                                param1!!
                            )[i].mentor_nomi, true
                        )
                    ) {
                        mentorID = db!!.getAllMentorsByKursId(param1!!)[i].id!!
                        break
                    }
                }

                db?.insertGuruh(Guruh(courseName, mentorID, 0, param1, time))
                findNavController().popBackStack()
                Snackbar.make(root, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(root, "Barcha maydonlarni to'ldiring!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadTime() {
        spinnerAdapter2 = AddGroupSpinnerAdapter2()
        spinnerAdapter2?.setAdapter(time!!, root.context)
        root.add_group_add_time.adapter = spinnerAdapter2
    }

    private fun loadMentors() {
        spinnerAdapter = AddGroupSpinnerAdapter()
        spinnerAdapter?.setAdapter(mentors!!, root.context)
        root.add_group_add_mentor.adapter = spinnerAdapter
    }

    private fun loadData() {
        mentors = ArrayList()
        time = ArrayList()
        mentors!!.add("Mentorni tanlang")
        for (i in 0 until db?.getAllMentorsByKursId(param1!!)!!.size) {
            mentors?.add(
                db?.getAllMentorsByKursId(param1!!)!![i].mentor_familyasi + " " + db?.getAllMentorsByKursId(
                    param1!!
                )!![i].mentor_nomi
            )
        }
        time?.add("Vaqti")
        time?.add("16:30 - 18:30")
        time?.add("19:00 - 21:00")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            AddGroup().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}