package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit_21019.pdpuzkurslar.DataBase.AppDatabase
import com.tuit_21019.pdpuzkurslar.DataBase.DbMethods
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.StudentsAdapter
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Talaba
import kotlinx.android.synthetic.main.fragment_group_item.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "group"
private const val ARG_PARAM2 = "param2"

class GroupItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var group: Guruh? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group = it.getSerializable(ARG_PARAM1) as Guruh?
            param2 = it.getString(ARG_PARAM2)
        }
        adapter = StudentsAdapter()
        database = AppDatabase.get.getDatabase()
        getDao = database!!.getDao()
    }

    lateinit var root: View
    private var database: AppDatabase? = null
    private var getDao: DbMethods? = null
    private var studentList: ArrayList<Talaba>? = null
    private var adapter: StudentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_group_item, container, false)
        Log.d("AAAA", "groupID: ${group?.id}")
        setToolbar()
        loadDataToView()
        loadData()
        loadAdapters()
        deleteItemClick()
        addClick()
        itemEditClick()
        startLessonClick()

        return root
    }

    private fun startLessonClick() {
        if (group?.ochilganligi == 0) {
            root.item_group_start_lesson.setOnClickListener {

                if (studentList!!.isNotEmpty()) {
                    val dialog = AlertDialog.Builder(root.context)
                    dialog.setTitle("Darsni boshlash")
                    dialog.setCancelable(false)
                    dialog.setMessage("Rostdan ham guruhga darsni boshlamoqchimisiz?")
                    dialog.setPositiveButton("Ha", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            getDao?.updateGuruh(
                                    group?.id!!,
                                group?.guruh_nomi.toString(),
                                group?.mentor_id!!,
                                    1,
                                group?.kurs_id!!,
                                group?.dars_vaqti!!
                            )
                            p0?.cancel()
                            findNavController().popBackStack()
                        }
                    })
                    dialog.setNegativeButton("Orqaga", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.cancel()
                        }
                    })
                    dialog.show()
                } else {
                    Toast.makeText(root.context, "Avval talaba qo'shing!", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    private fun itemEditClick() {
        adapter?.setOnEditClick(object : StudentsAdapter.OnEditClick {
            override fun onClick(talaba: Talaba, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("group_to_student_add", group)
                bundle.putSerializable("edit_student", talaba)
                findNavController().navigate(R.id.addStudent, bundle)
            }

        })
    }

    private fun addClick() {
        root.toolbar.setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("group_to_student_add", group)
            findNavController().navigate(R.id.addStudent, bundle)
            true
        }
    }

    private fun deleteItemClick() {
        adapter?.setOnDeleteClick(object : StudentsAdapter.OnDeleteClick {
            override fun onClick(talaba: Talaba, position: Int) {
                val dialog = AlertDialog.Builder(root.context)
                dialog.setTitle("Talabani o'chirish")
                dialog.setMessage("Ushbu o'quvchini o'chirmoqchimisiz?")
                dialog.setPositiveButton("Ha") { p0, p1 ->
                    getDao?.deleteTalaba(talaba.id!!)
                    adapter?.notifyItemRemoved(position)
                    adapter?.setAdapter(getDao?.getAllStudentsByGroupId(group?.id!!) as ArrayList)
                    p0.cancel()
                    Toast.makeText(root.context, "O'chirildi", Toast.LENGTH_SHORT).show()
                }
                dialog.setNegativeButton("Yo'q") { p0, p1 ->
                    p0?.cancel()
                }
                dialog.show()
            }

        })
    }

    private fun setToolbar() {
        root.toolbar.title = group?.guruh_nomi

        if (group?.ochilganligi == 1) {
            root.toolbar.menu.getItem(0).isVisible = false
        } else if (group?.ochilganligi == 0) {
            root.toolbar.menu.getItem(0).isVisible = true
        }

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadAdapters() {
        adapter?.setAdapter(studentList!!)
        root.item_group_rv.adapter = adapter
    }

    private fun loadData() {
        studentList = ArrayList()
        studentList = getDao?.getAllStudentsByGroupId(group?.id!!) as ArrayList
    }

    @SuppressLint("SetTextI18n")
    private fun loadDataToView() {
        root.item_group_name.text = "PDP ${group?.guruh_nomi}"
        val studentCount = getDao?.getAllStudentsByGroupId(group?.id!!)?.size
        root.item_group_student_count.text = "O'quvchilar soni: $studentCount"
        root.item_group_time.text = "Vaqti: ${group?.dars_vaqti}"

        if (group?.ochilganligi == 1) {
            root.item_group_start_lesson.visibility = View.GONE
        } else {
            root.item_group_start_lesson.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(group: String, param2: String) =
            GroupItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, group)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}