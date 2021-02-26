package com.tuit_21019.pdpuzkurslar.guruhlar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Guruh
import kotlinx.android.synthetic.main.item_guruh.view.*

class GroupByStatusAdapter(var groupList: ArrayList<Guruh>, var studentCountList: ArrayList<Int>) :
    RecyclerView.Adapter<GroupByStatusAdapter.VH>() {

    private var onViewClick: OnViewClick? = null
    private var onEditClick: OnEditClick? = null
    private var onDeleteClick: OnDeleteClick? = null

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun onBind(guruh: Guruh, studentCount: Int, position: Int) {
            itemView.group_name.text = guruh.guruh_nomi
            itemView.group_student_count.text = "O'quvchilar soni: ${studentCount.toString()}"

            itemView.group_view.setOnClickListener {
                if (onViewClick != null) {
                    onViewClick!!.onClick(guruh, position)
                }
            }

            itemView.group_edit.setOnClickListener {
                if (onEditClick != null) {
                    onEditClick!!.onClick(guruh, position)
                }
            }

            itemView.group_delete.setOnClickListener {
                if (onDeleteClick != null) {
                    onDeleteClick!!.onClick(guruh, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_guruh, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(groupList[position], studentCountList[position], position)
    }

    override fun getItemCount(): Int = groupList.size

    interface OnViewClick {
        fun onClick(guruh: Guruh, position: Int)
    }

    fun setOnViewClick(onViewClick: OnViewClick) {
        this.onViewClick = onViewClick
    }

    interface OnEditClick {
        fun onClick(guruh: Guruh, position: Int)
    }

    fun setOnEditClick(onEditClick: OnEditClick) {
        this.onEditClick = onEditClick
    }

    interface OnDeleteClick {
        fun onClick(guruh: Guruh, position: Int)
    }

    fun setOnDeleteClick(ondeleteClick: OnDeleteClick) {
        this.onDeleteClick = ondeleteClick
    }
}