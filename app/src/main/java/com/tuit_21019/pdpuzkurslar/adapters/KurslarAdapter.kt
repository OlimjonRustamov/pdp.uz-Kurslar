package com.tuit_21019.pdpuzkurslar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.item_kurslar.view.*

class KurslarAdapter() : RecyclerView.Adapter<KurslarAdapter.Vh>() {
    var kursItemClick: KursItemClick? = null

    private var kurslarList: ArrayList<Kurs>? = null

    fun setAdapter(kurslarList: ArrayList<Kurs>) {
        this.kurslarList = kurslarList
    }

    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun myBind(kurs: Kurs) {
            itemView.item_kurslar_title.text = kurs.kurs_nomi

            itemView.kurslar_item_cardview.setOnClickListener {
                if (kursItemClick != null) {
                    kursItemClick!!.kursitemClick(kurs)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_kurslar, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.myBind(kurslarList!![position])
    }

    override fun getItemCount(): Int = kurslarList!!.size

    interface KursItemClick {
        fun kursitemClick(kurs: Kurs)
    }

}