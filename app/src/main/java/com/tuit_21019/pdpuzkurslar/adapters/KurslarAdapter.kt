package com.tuit_21019.pdpuzkurslar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.item_kurslar.view.*

class KurslarAdapter(var kurslarList: ArrayList<Kurs>) : RecyclerView.Adapter<KurslarAdapter.Vh>() {
    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun myBind(str:String) {
            itemView.item_kurslar_title.text=str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_kurslar,parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.myBind(kurslarList[position].kurs_nomi!!)
    }

    override fun getItemCount(): Int =kurslarList.size

}