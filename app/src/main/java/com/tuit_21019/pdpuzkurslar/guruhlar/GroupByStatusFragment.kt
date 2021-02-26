package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.GroupByStatusAdapter
import com.tuit_21019.pdpuzkurslar.models.Guruh
import kotlinx.android.synthetic.main.fragment_group_by_status.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GroupByStatusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: Int? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
        db = DbHelper(this.context!!)
    }

    lateinit var root: View
    private var db: DbHelper? = null
    private var groupList: ArrayList<Guruh>? = null
    private var studentCountList:ArrayList<Int>?=null
    private var adapter:GroupByStatusAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        root = inflater.inflate(R.layout.fragment_group_by_status, container, false)
        loadData()
        loadAdapters()


        return root
    }

    private fun loadAdapters() {
        adapter = GroupByStatusAdapter(groupList!!, studentCountList!!)
        root.group_rv.adapter=adapter
        adapter?.notifyDataSetChanged()
    }

    private fun loadData() {
        var status = 1
        if (param1 == 1) {
            status = 1
        } else if (param1 == 2) {
            status = 0
        }

        groupList = ArrayList()
        groupList = db?.getAllGroupByStatus(status)

        studentCountList = ArrayList()
        for (i in 0 until groupList!!.size) {
            studentCountList?.add(db?.getAllStudentsByGroupId(groupList!![i].kurs_id!!)!!.size)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
                GroupByStatusFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                    }
                }
    }
}