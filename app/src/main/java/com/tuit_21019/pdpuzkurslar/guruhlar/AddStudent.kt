package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Talaba
import kotlinx.android.synthetic.main.fragment_add_student.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.toolbar

private const val ARG_PARAM1 = "group_to_student_add"
private const val ARG_PARAM2 = "param2"

class AddStudent : Fragment() {
    // TODO: Rename and change types of parameters
    private var group: Guruh? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group = it.getSerializable(ARG_PARAM1) as Guruh?
            param2 = it.getString(ARG_PARAM2)
        }
        db = DbHelper(this.requireContext())
    }

    lateinit var root: View
    private var db: DbHelper? = null
    private var student: Talaba? = null

    private var mentorList: ArrayList<String>? = null
    private var daysList: ArrayList<String>? = null
    private var timeList: ArrayList<String>? = null
    private var groupList: ArrayList<String>? = null


    private var mentorSpinner: AddGroupSpinnerAdapter? = null
    private var daysSpinner: AddGroupSpinnerAdapter? = null
    private var timeSpinner: AddGroupSpinnerAdapter? = null
    private var groupsSpinner: AddGroupSpinnerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_add_student, container, false)
        Log.d("AAAA", "groupid: ${group?.guruh_nomi}")
        setToolbar()
        loadData()
        loadSpinners()
        loadDataToView()
        calendarClick()

        onSaveClick()
        return root
    }

    private fun onSaveClick() {
        root.add_student_save.setOnClickListener {
            val studentName = root.add_student_surname.text.toString()
            val studentSurname = root.add_student_name.text.toString()
            val studentPatronomic = root.add_student_patronomic.text.toString()
            val studentStartLessonTime = root.add_student_start_time.text.toString()
            val days = daysList!![root.add_student_days.selectedItemPosition]

            if (
                studentName.isNotEmpty()
                && studentSurname.isNotEmpty()
                && studentPatronomic.isNotEmpty()
                && studentStartLessonTime.isNotEmpty()
                && !days.equals("Kunlari", true)
            ) {

                db?.insertTalaba(
                    Talaba(
                        studentName,
                        studentSurname,
                        studentPatronomic,
                        studentStartLessonTime,
                        group?.mentor_id,
                        days,
                        group?.dars_vaqti,
                        group?.id
                    )
                )
                findNavController().popBackStack()
            } else {
                Snackbar.make(root,"Barcha maydonlarni to'ldiring",Snackbar.LENGTH_LONG).show()
            }


        }
    }

    @SuppressLint("NewApi")
    private fun calendarClick() {
        root.add_student_calendar.setOnClickListener {
            val dialog = DatePickerDialog(root.context)

            dialog.datePicker.setOnDateChangedListener { datePicker, year, month, day ->
                root.add_student_start_time.setText("$day/${month + 1}/$year")
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun loadDataToView() {
        root.add_student_mentor.adapter = mentorSpinner
        root.add_student_days.adapter = daysSpinner
        root.add_student_time.adapter = timeSpinner
        root.add_student_group.adapter = groupsSpinner
    }

    private fun setToolbar() {
        root.toolbar.title = "Talaba qo'shish"
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadSpinners() {
        mentorSpinner = AddGroupSpinnerAdapter()
        mentorSpinner?.setAdapter(mentorList!!, root.context)

        daysSpinner = AddGroupSpinnerAdapter()
        daysSpinner?.setAdapter(daysList!!, root.context)

        timeSpinner = AddGroupSpinnerAdapter()
        timeSpinner?.setAdapter(timeList!!, root.context)

        groupsSpinner = AddGroupSpinnerAdapter()
        groupsSpinner?.setAdapter(groupList!!, root.context)
    }

    private fun loadData() {
        mentorList = ArrayList()
        daysList = ArrayList()
        timeList = ArrayList()
        groupList = ArrayList()

        mentorList?.add(
            db?.getMentorByID(group?.mentor_id!!)?.mentor_nomi + " " + db?.getMentorByID(
                group?.mentor_id!!
            )?.mentor_familyasi
        )

        daysList?.add("Kunlari")
        daysList?.add("Juft kunlar")
        daysList?.add("Toq kunlar")

        timeList?.add(group?.dars_vaqti.toString())

        groupList?.add(group?.guruh_nomi.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(group: String, param2: String) =
            AddStudent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, group)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}