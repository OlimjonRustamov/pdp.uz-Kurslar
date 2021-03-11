package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.AppDatabase
import com.tuit_21019.pdpuzkurslar.DataBase.DbMethods
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.GroupByStatusAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.dialogs.EditGroupItemDialog
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.fragment_group_by_status.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GroupByStatusFragment : Fragment() {

    private var param1: Int? = null
    private var param2: Int? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        getDao = database!!.getDao()
    }

    lateinit var root: View
    private var database: AppDatabase? = null
    private var getDao: DbMethods? = null
    private var groupList: ArrayList<Guruh>? = null
    private var studentCountList: ArrayList<Int>? = null
    private var adapter: GroupByStatusAdapter? = null
    private var status = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_group_by_status, container, false)
        loadData()
        loadAdapters()
        onItemDeleteClick()
        onItemEditClick()
        onViewClick()
        return root
    }

    private fun onViewClick() {
        adapter?.setOnViewClick(object : GroupByStatusAdapter.OnViewClick {
            override fun onClick(guruh: Guruh, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("group", guruh)
                findNavController().navigate(R.id.groupItemFragment, bundle)
            }

        })
    }

    private fun onItemEditClick() {
        adapter?.setOnEditClick(object : GroupByStatusAdapter.OnEditClick {
            override fun onClick(guruh: Guruh, position: Int) {

                val mentors = ArrayList<Mentor>()
                mentors.add(Mentor("Mentorni tanlang", "", "", param2))
                mentors.addAll(getDao!!.getAllMentorsByKursId(param2!!))

                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog = EditGroupItemDialog.newInstance(guruh, mentors)
                dialog.show(beginTransaction, "edit")

                dialog.setOnEditClick(object : EditGroupItemDialog.OnEditClick {
                    override fun onClick(guruh: Guruh) {
                        getDao?.updateGuruh(
                            guruh.id!!,
                            guruh.guruh_nomi.toString(),
                            guruh.mentor_id!!,
                            guruh.ochilganligi!!,
                            guruh.kurs_id!!,
                            guruh.dars_vaqti.toString()
                        )

                        adapter?.groupList = getDao!!.getAllGroupByStatus(status) as ArrayList
                        adapter?.notifyItemChanged(position)
                        Snackbar.make(root, "Muvaffaqiyatli o'zgartirildi", Snackbar.LENGTH_LONG)
                            .show()
                    }
                })

            }
        })
    }

    private fun onItemDeleteClick() {
        adapter?.setOnDeleteClick(object : GroupByStatusAdapter.OnDeleteClick {
            override fun onClick(guruh: Guruh, position: Int) {

                if (getDao!!.getAllStudentsByGroupId(guruh.id!!).size == 0) {
                    val dialog = AlertDialog.Builder(root.context)
                    dialog.setTitle("Guruhni o'chirish")
                    dialog.setMessage("Diqqat! Ushbu guruh to'liqligicha o'chirib yuboriladi!")
                    dialog.setNegativeButton(
                        "Bekor qilish"
                    ) { dialog, which -> dialog?.cancel() }
                    dialog.setPositiveButton(
                        "O'chirish"
                    ) { dialog, which ->

                        getDao?.deleteGuruh(guruh)
                        adapter?.notifyItemRemoved(position)
                        adapter?.groupList = getDao!!.getAllGroupByStatus(status) as ArrayList
                        dialog!!.cancel()
                    }
                    dialog.show()
                } else {
                    Snackbar.make(
                        root,
                        "Ushbu guruhni o'chirish mumkin emas!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun loadAdapters() {
        adapter = GroupByStatusAdapter(groupList!!, studentCountList!!)
        root.group_rv.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun loadData() {
        if (param1 == 1) {
            status = 1
        } else if (param1 == 2) {
            status = 0
        }

        groupList = ArrayList()
        groupList = getDao?.getGroupByKursIdAndStatus(status, param2!!) as ArrayList

        studentCountList = ArrayList()
        for (i in 0 until groupList!!.size) {
            studentCountList?.add(getDao?.getAllStudentsByGroupId(groupList!![i].id!!)!!.size)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            GroupByStatusFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}