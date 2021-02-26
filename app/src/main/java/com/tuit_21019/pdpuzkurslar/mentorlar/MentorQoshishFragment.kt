package com.tuit_21019.pdpuzkurslar.mentorlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.*
import kotlinx.android.synthetic.main.fragment_mentor_qoshish.view.*
import kotlinx.android.synthetic.main.fragment_mentor_qoshish.view.toolbar

private const val ARG_PARAM1 = "kurs"
private const val ARG_PARAM2 = "param2"

class MentorQoshishFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Kurs? = null
//    private var param2: String? = null

    lateinit var root:View
    lateinit var db:DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_mentor_qoshish, container, false)
        db= DbHelper(root.context)
        setToolbar()
        setClickBtn()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MentorQoshishFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                }
    }
    private fun setClickBtn() {
        root.add_mentor_btn.setOnClickListener {
            val mentor_ismi = root.add_mentor_ismi_et.text.toString().trim()
            val mentor_familyasi = root.add_mentor_familyasi_et.text.toString().trim()
            val mentor_otasining_ismi=root.add_mentor_otasining_ismi_et.text.toString().trim()
            if (mentor_ismi.isNotEmpty() && mentor_familyasi.isNotEmpty() && mentor_otasining_ismi.isNotEmpty()) {
                db.insertMentor(
                    Mentor(mentor_ismi,
                        mentor_familyasi,
                        mentor_otasining_ismi,
                        param1!!.id))
                Snackbar.make(root, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else Snackbar.make(root, "Barcha maydonlarni to'ldiring!", Snackbar.LENGTH_LONG)
                .show()

        }
    }
    private fun setToolbar() {
        root.toolbar.title = "Yangi mentor qo'shish"

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}