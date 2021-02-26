package com.tuit_21019.pdpuzkurslar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.mentor_item.view.*

class MentorAdapter(var mentorList: ArrayList<Mentor>):RecyclerView.Adapter<MentorAdapter.Vh>() {

    var onEditClick:OnEditClick?=null

    var onDeleteClick:OnDeleteClick?=null

    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun myBind(mentor: Mentor,position: Int) {
            itemView.mentor_item_ismi.text = mentor.mentor_nomi + " " + mentor.mentor_familyasi
            itemView.mentor_item_otasining_ismi.text = mentor.mentor_otasining_ismi

            itemView.mentor_edit_btn.setOnClickListener {
                if (onEditClick != null) {
                    onEditClick!!.editClick(mentor,position)
                }
            }
            itemView.mentor_delete_btn.setOnClickListener {
                if (onDeleteClick != null) {
                    onDeleteClick!!.deleteClick(mentor,position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.mentor_item,parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.myBind(mentorList[position],position)
    }

    override fun getItemCount(): Int =mentorList.size

    interface OnEditClick {
        fun editClick(mentor: Mentor,position:Int)
    }

    interface OnDeleteClick {
        fun deleteClick(mentor: Mentor,position: Int)
    }
}