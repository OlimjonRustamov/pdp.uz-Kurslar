package com.tuit_21019.pdpuzkurslar.guruhlar.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.AddGroupSpinnerAdapter2
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.edit_group_item_dialog.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditGroupItemDialog : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: Guruh? = null
    private var param2: ArrayList<Mentor>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Guruh
            param2 = it.getSerializable(ARG_PARAM2) as ArrayList<Mentor>
        }
    }

    lateinit var root: View
    private var spinnerAdapter: AddGroupSpinnerAdapter? = null
    private var spinnerAdapter2: AddGroupSpinnerAdapter2? = null
    private var mentors: ArrayList<String>? = null
    private var time: ArrayList<String>? = null
    private var onEditClick: OnEditClick? = null
    private var db: DbHelper? = null
    private var mentorID = 0
    private var kursID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.edit_group_item_dialog, container, false)
        isCancelable=false

        loadData()
        loadDataToView()
        setSpinners()
        editClick()
        cancelClick()

        return root
    }

    private fun cancelClick() {
        root.cancel_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun editClick() {
        root.edit_btn.setOnClickListener {
            val name = root.edit_group_course_name.text.toString()
            val mentor = mentors!![root.edit_group_mentor.selectedItemPosition]
            val time = time!![root.edit_group_time.selectedItemPosition]

            for (i in 0 until param2!!.size) {
                if (mentor.equals(
                        param2!![i].mentor_nomi + " " + param2!![i].mentor_familyasi,
                        true
                    )
                ) {
                    mentorID = param2!![i].id!!
                    kursID = param2!![i].kurs_id!!
                }
                break
            }

            if (name.isNotEmpty() && !mentor.equals(
                    "Mentorni tanlang",
                    true
                ) && !time.equals("Vaqti", true) && onEditClick != null
            ) {
                onEditClick!!.onClick(Guruh(param1?.id,name, mentorID, param1?.ochilganligi, kursID, time))
                dismiss()
            } else {
                Toast.makeText(root.context, "Bo'sh joylarni to'ldiring!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadData() {
        mentors = ArrayList()

        for (i in 0 until param2!!.size) {
            mentors?.add(param2!![i].mentor_nomi + " " + param2!![i].mentor_familyasi)
        }

        time = ArrayList()
        time?.add("Vaqti")
        time?.add("16:30 - 18:30")
        time?.add("19:00 - 21:00")
    }

    private fun setSpinners() {

        spinnerAdapter = AddGroupSpinnerAdapter()
        spinnerAdapter?.setAdapter(mentors!!, root.context)
        root.edit_group_mentor.adapter = spinnerAdapter
        var mentorPosition = 1

        for (i in 0 until param2!!.size) {
            if (param2!![i].id == param1?.mentor_id) {
                mentorPosition = i
                break
            }
        }
        root.edit_group_mentor.setSelection(mentorPosition)


        spinnerAdapter2 = AddGroupSpinnerAdapter2()
        spinnerAdapter2?.setAdapter(time!!, root.context)
        root.edit_group_time.adapter = spinnerAdapter2
        var position = 1
        for (i in 0 until time!!.size) {
            if (time!![i].equals(param1?.dars_vaqti, true)) {
                position = i
                break
            }
        }
        root.edit_group_time.setSelection(position)
    }

    private fun loadDataToView() {
        root.edit_group_course_name.setText(param1?.guruh_nomi)
    }

    interface OnEditClick {
        fun onClick(guruh: Guruh)
    }

    fun setOnEditClick(onEditClick: OnEditClick) {
        this.onEditClick = onEditClick
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Guruh, param2: ArrayList<Mentor>) =
            EditGroupItemDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}