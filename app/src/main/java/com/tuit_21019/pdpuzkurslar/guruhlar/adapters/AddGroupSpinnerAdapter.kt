package com.tuit_21019.pdpuzkurslar.guruhlar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.tuit_21019.pdpuzkurslar.R
import kotlinx.android.synthetic.main.item_spinner.view.*


class AddGroupSpinnerAdapter :BaseAdapter() {

    private var list:ArrayList<String>?=null
    private var context:Context?=null

    fun setAdapter(list: ArrayList<String>,context: Context) {
        this.list=list
        this.context=context
    }

    override fun getCount(): Int =list!!.size

    override fun getItem(p0: Int): String {
        return list!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.item_spinner,
            parent,
            false
        )

        if (position == 0) {
            view.spinner_tv.setTextColor(Color.GRAY)
        }

        view.spinner_tv.text=list!![position]

        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}