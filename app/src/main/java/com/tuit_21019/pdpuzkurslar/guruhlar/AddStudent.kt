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
private const val ARG_PARAM2 = "edit_student"

class AddStudent : Fragment() {
    // TODO: Rename and change types of parameters
    private var group: Guruh? = null
    private var talaba: Talaba? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group = it.getSerializable(ARG_PARAM1) as Guruh?
            talaba = it.getSerializable(ARG_PARAM2) as Talaba?
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_add_student, container, false)

        if (talaba != null) {
            root.add_student_save.text = "O'zgartirish"
        }
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
            val studentStartLessonTime = root.layout4.text.toString()
            val days = daysList!![root.add_student_days.selectedItemPosition]

            if (
                studentName.isNotEmpty()
                && studentSurname.isNotEmpty()
                && studentPatronomic.isNotEmpty()
                && studentStartLessonTime.isNotEmpty()
                && !days.equals("Kunlari", true)
            ) {
                if (talaba == null) {
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
                } else {
                    db?.updateTalaba(
                        Talaba(
                            talaba?.id,
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
                }
                findNavController().popBackStack()
            } else {
                Snackbar.make(root, "Barcha maydonlarni to'ldiring", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun calendarClick() {
        root.add_student_calendar.setOnClickListener {
            val dialog = DatePickerDialog(root.context)

            dialog.datePicker.setOnDateChangedListener { datePicker, year, month, day ->
                root.layout4.setText("$day/${month + 1}/$year")
                dialog.dismiss()
            }
            dialog.show()
        }
        root.layout4.setOnClickListener {
            val dialog = DatePickerDialog(root.context)

            dialog.datePicker.setOnDateChangedListener { datePicker, year, month, day ->
                root.layout4.setText("$day/${month + 1}/$year")
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun loadDataToView() {
        if (talaba != null) {
            root.add_student_surname.setText(talaba?.talaba_familyasi)
            root.add_student_name.setText(talaba?.talaba_ismi)
            root.add_student_patronomic.setText(talaba?.talaba_otasining_ismi)
            root.layout4.setText(talaba?.dars_boshlash_vaqti)
        }
        root.add_student_mentor.adapter = mentorSpinner
        root.add_student_days.adapter = daysSpinner
        root.add_student_time.adapter = timeSpinner
        root.add_student_group.adapter = groupsSpinner
    }

    private fun setToolbar() {
        if (talaba == null) {
            root.toolbar.title = "Talaba qo'shish"
        } else {
            root.toolbar.title = "Talabani o'zgartirish"
        }
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadSpinners() {
        mentorSpinner = AddGroupSpinnerAdapter()
        mentorSpinner?.setAdapter(mentorList!!, root.context)

        daysSpinner = AddGroupSpinnerAdapter()
        daysSpinner?.setAdapter(daysList!!, root.context)
        if (talaba != null) {
            var position = 1
            for (i in 0 until daysList!!.size) {
                if (daysList!![i].equals(talaba?.kunlar, true)) {
                    position = i
                }
            }
            Log.d("AAAA", "loadSpinners: ${position}")
//            root.add_student_days.setSelection(position,true)
            root.add_student_days.setSelection(daysList!!.indexOf(talaba?.kunlar.toString()),true)
        }

        timeSpinner = AddGroupSpinnerAdapter()
        timeSpinner?.setAdapter(timeList!!, root.context)

        groupsSpinner = AddGroupSpinnerAdapter()
        groupsSpinner?.setAdapter(groupList!!, root.context)
    }

    private fun loadData() {
        daysList = ArrayList()
        mentorList = ArrayList()
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

    override fun onResume() {
        super.onResume()
        if (talaba != null) {
            if (talaba?.kunlar.equals("Juft kunlar", true)) {
                root.add_student_days.setSelection(1)
            }else if (talaba?.kunlar.equals("Toq kunlar", true)) {
                root.add_student_days.setSelection(2)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(group: String, talaba: String) =
            AddStudent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, group)
                    putString(ARG_PARAM2, talaba)
                }
            }
    }
}