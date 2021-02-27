package com.tuit_21019.pdpuzkurslar.guruhlar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Talaba
import kotlinx.android.synthetic.main.item_students.view.*

class StudentsAdapter() : RecyclerView.Adapter<StudentsAdapter.VH>() {

    private var studentList: ArrayList<Talaba>? = null
    private var onEditClick: StudentsAdapter.OnEditClick? = null
    private var onDeleteClick: StudentsAdapter.OnDeleteClick? = null

    fun setAdapter(studentList: ArrayList<Talaba>) {
        this.studentList = studentList
    }

    inner class VH(itemview: View) : RecyclerView.ViewHolder(itemview) {
        @SuppressLint("SetTextI18n")
        fun onBind(talaba: Talaba, position: Int) {
            itemView.student_name.text = talaba.talaba_ismi + " " + talaba.talaba_familyasi
            itemView.student_otchestvo.text = talaba.talaba_otasining_ismi
            itemView.student_start_lesson_time.text = talaba.dars_boshlash_vaqti

            itemView.student_edit.setOnClickListener {
                if (onEditClick != null)
                    onEditClick!!.onClick(talaba, position)
            }

            itemView.student_delete.setOnClickListener {
                if (onDeleteClick != null) {
                    onDeleteClick!!.onClick(talaba, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_students, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(studentList!![position],position)
    }

    override fun getItemCount(): Int = studentList!!.size

    interface OnEditClick {
        fun onClick(talaba: Talaba, position: Int)
    }

    fun setOnEditClick(onEditClick: OnEditClick) {
        this.onEditClick = onEditClick
    }

    interface OnDeleteClick {
        fun onClick(talaba: Talaba, position: Int)
    }

    fun setOnDeleteClick(ondeleteClick: OnDeleteClick) {
        this.onDeleteClick = ondeleteClick
    }
}