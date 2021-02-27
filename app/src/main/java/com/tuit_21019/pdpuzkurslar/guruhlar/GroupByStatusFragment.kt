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
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.GroupByStatusAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.dialogs.EditGroupItemDialog
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Mentor
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
                mentors.addAll(db!!.getAllMentorsByKursId(param2!!))

                val beginTransaction = childFragmentManager.beginTransaction()
                val dialog = EditGroupItemDialog.newInstance(guruh, mentors)
                dialog.show(beginTransaction, "edit")

                dialog.setOnEditClick(object : EditGroupItemDialog.OnEditClick {
                    override fun onClick(guruh: Guruh) {
                        db?.updateGuruh(guruh)

                        adapter?.groupList = db!!.getAllGroupByStatus(status)
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
                val dialog = AlertDialog.Builder(root.context)
                dialog.setTitle("Guruhni o'chirish")
                dialog.setMessage("Diqqat! Ushbu guruh o'chirilganda unga tegishli bo'lgan barcha talabalar ham o'chirib yuboriladi!")
                dialog.setNegativeButton(
                    "Bekor qilish"
                ) { dialog, which -> dialog?.cancel() }
                dialog.setPositiveButton(
                    "O'chirish"
                ) { dialog, which ->

                    db?.deleteGuruh(guruh)
                    adapter?.notifyItemRemoved(position)
                    adapter?.groupList = db!!.getAllGroupByStatus(status)
                    dialog!!.cancel()
                }
                dialog.show()
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
        groupList = db?.getGroupByKursIdAndStatus(status, param2!!)

        studentCountList = ArrayList()
        for (i in 0 until groupList!!.size) {
            studentCountList?.add(db?.getAllStudentsByGroupId(groupList!![i].kurs_id!!)!!.size)
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