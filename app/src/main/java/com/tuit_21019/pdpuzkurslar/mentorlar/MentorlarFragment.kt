package com.tuit_21019.pdpuzkurslar.mentorlar

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.adapters.MentorAdapter
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.edit_mentor_dialog.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.toolbar
import kotlinx.android.synthetic.main.fragment_mentor_qoshish.view.*
import kotlinx.android.synthetic.main.fragment_mentorlar.view.*
import kotlinx.android.synthetic.main.mentor_item.view.*

private const val ARG_PARAM1 = "kurs"


class MentorlarFragment : Fragment() {
    private var param1: Kurs? = null

    lateinit var db: DbHelper
    lateinit var root: View
    lateinit var mentorAdapter:MentorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_mentorlar, container, false)
        db = DbHelper(root.context)

        setToolbar()

        mentorAdapter = MentorAdapter(db.getAllMentorsByKursId(param1!!.id!!))
        root.mentorlar_recyclerview.layoutManager = LinearLayoutManager(root.context)
        root.mentorlar_recyclerview.adapter=mentorAdapter

        setBtnsClick()
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Kurs) =
                MentorlarFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }

    private fun setToolbar() {
        root.toolbar.title = param1!!.kurs_nomi

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        root.toolbar.setOnMenuItemClickListener { item ->
            if (item?.itemId == R.id.add_menu_btn) {
                val bundle = Bundle()
                bundle.putSerializable("kurs", param1)
                findNavController().navigate(R.id.mentorQoshishFragment,bundle)
            }
            true
        }
    }
    fun setBtnsClick() {
        mentorAdapter.onEditClick=object:MentorAdapter.OnEditClick{
          override fun editClick(mentor: Mentor,position:Int) {
              val dialog = AlertDialog.Builder(root.context)
              dialog.setTitle("Mentor ma'lumotlarini tahrirlash")
              val view =
                  LayoutInflater.from(root.context).inflate(R.layout.edit_mentor_dialog, null, false)
              view.edit_mentor_ismi_et.setText(mentor.mentor_nomi)
              view.edit_mentor_familyasi_et.setText(mentor.mentor_familyasi)
              view.edit_mentor_otasining_ismi_et.setText(mentor.mentor_otasining_ismi)
              dialog.setView(view)
              dialog.setNegativeButton("Yopish", object : DialogInterface.OnClickListener {
                  override fun onClick(dialog: DialogInterface?, which: Int) {
                      dialog?.cancel()
                  }
              })
              dialog.setPositiveButton("O'zgartirish", object : DialogInterface.OnClickListener {
                  override fun onClick(dialog: DialogInterface?, which: Int) {
                      mentor.mentor_nomi = view.edit_mentor_ismi_et.text.toString()
                      mentor.mentor_familyasi = view.edit_mentor_familyasi_et.text.toString()
                      mentor.mentor_otasining_ismi=view.edit_mentor_otasining_ismi_et.text.toString()
                      db.updateMentor(mentor)
                      Snackbar.make(root,"Muvaffaqiyatli qo'shildi",Snackbar.LENGTH_LONG).show()
                      mentorAdapter.notifyItemChanged(position)
                      dialog!!.cancel()
                  }
              })
              dialog.show()
          }
      }

        mentorAdapter.onDeleteClick=object:MentorAdapter.OnDeleteClick{
            override fun deleteClick(mentor: Mentor, position: Int) {
                val dialog = AlertDialog.Builder(root.context)
                dialog.setTitle("Mentorni o'chirish")
                dialog.setMessage("Diqqat! Ushbu mentor o'chirilganda unga tegishli bo'lgan kurslar va barcha talabalar ham o'chirib yuboriladi!")
                dialog.setNegativeButton("Bekor qilish"
                ) { dialog, which -> dialog?.cancel() }
                dialog.setPositiveButton("O'chirish"
                ) { dialog, which ->
                    db.deleteMentor(mentor)
                    mentorAdapter.notifyItemRemoved(position)
                    mentorAdapter.mentorList=db.getAllMentorsByKursId(param1!!.id!!)
                    dialog!!.cancel()
                }
                dialog.show()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mentorAdapter.notifyDataSetChanged()
    }
}