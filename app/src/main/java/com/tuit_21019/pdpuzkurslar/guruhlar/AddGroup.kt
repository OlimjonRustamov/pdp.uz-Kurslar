package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.AppDatabase
import com.tuit_21019.pdpuzkurslar.DataBase.DbMethods
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter2
import com.tuit_21019.pdpuzkurslar.models.Guruh
import kotlinx.android.synthetic.main.fragment_add_group.view.*

private const val ARG_PARAM1 = "kursID"
private const val ARG_PARAM2 = "param2"


class AddGroup : Fragment() {
    // TODO: Rename and change types of parameters
    private var kursID: Int? = null
    private var param2: String? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            kursID = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        getDao = database!!.getDao()
    }

    lateinit var root: View
    private var mentors: ArrayList<String>? = null
    private var time: ArrayList<String>? = null
    private var database: AppDatabase? = null
    private var getDao: DbMethods? = null
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

    private fun checkGroupName(groupName: String): Boolean {
        // bir xil guruh nomi bo'lmasligini tekshirish
        var exist = false
        for (i in 0 until getDao!!.getAllGroupsByKursId(kursID!!).size) {
            if (getDao!!.getAllGroupsByKursId(kursID!!)[i].guruh_nomi.equals(groupName, true)) {
                exist = true
                break
            }
        }
        return exist
    }

    private fun toolbarConfiguration() {
        // back button
        root.toolbar.menu.getItem(0).isVisible = false
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onSaveClick() {
        root.add_group_save.setOnClickListener {
            val courseName = root.add_group_course_name.text.toString()
            val mentor = mentors!![root.add_group_add_mentor.selectedItemPosition]
            val time = time!![root.add_group_add_time.selectedItemPosition]
            var mentorID = 0

            if (courseName.isNotEmpty() && !mentor.equals("Mentorni tanlang", true) && !time.equals(
                    "Vaqti",
                    true
                )
            ) {
                if (!checkGroupName(courseName)) {
                    // bir xil nomli guruh yo'qligini tekshirish
                    for (i in 0 until getDao?.getAllMentorsByKursId(kursID!!)!!.size) {
                        //mentorID ni olish
                        if (mentor.equals(
                                getDao!!.getAllMentorsByKursId(kursID!!)[i].mentor_familyasi + " " + getDao!!.getAllMentorsByKursId(
                                    kursID!!
                                )[i].mentor_nomi, true
                            )
                        ) {
                            mentorID = getDao!!.getAllMentorsByKursId(kursID!!)[i].id!!
                            break
                        }
                    }

                    getDao?.insertGuruh(Guruh(courseName, mentorID, 0, kursID, time))
                    findNavController().popBackStack()
                    Snackbar.make(root, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(root,"$courseName nomli guruh mavjud",Snackbar.LENGTH_LONG).show()
                }
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
        for (i in 0 until getDao?.getAllMentorsByKursId(kursID!!)!!.size) {
            mentors?.add(
                getDao?.getAllMentorsByKursId(kursID!!)!![i].mentor_familyasi + " " + getDao?.getAllMentorsByKursId(
                    kursID!!
                )!![i].mentor_nomi
            )
        }
        time?.add("Vaqti")
        time?.add("16:30 - 18:30")
        time?.add("19:00 - 21:00")
    }

    companion object {
        @JvmStatic
        fun newInstance(kursID: Int, param2: String) =
            AddGroup().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, kursID)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}