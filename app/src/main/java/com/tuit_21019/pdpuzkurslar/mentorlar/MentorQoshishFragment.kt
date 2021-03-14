package com.tuit_21019.pdpuzkurslar.mentorlar

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
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import kotlinx.android.synthetic.main.fragment_mentor_qoshish.view.*

private const val ARG_PARAM1 = "kurs"
private const val ARG_PARAM2 = "param2"

class MentorQoshishFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var kurs: Kurs? = null
//    private var param2: String? = null

    lateinit var root: View
    private var database: AppDatabase? = null
    private var getDao: DbMethods? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            kurs = it.getSerializable(ARG_PARAM1) as Kurs
//            param2 = it.getString(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        getDao = database!!.getDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_mentor_qoshish, container, false)
        setToolbar()
        setClickBtn()
        return root
    }

    private fun checkMentorName(mentorName: String): Boolean {
        var exist = false
        for (i in 0 until getDao!!.getAllMentorsByKursId(kurs?.id!!).size) {
            if ((getDao!!.getAllMentorsByKursId(kurs?.id!!)[i].mentor_nomi + " " + getDao!!.getAllMentorsByKursId(
                    kurs?.id!!
                )[i].mentor_familyasi).equals(mentorName, true)
            ) {
                exist = true
                break
            }
        }
        return exist
    }

    companion object {
        @JvmStatic
        fun newInstance(kurs: String, param2: String) =
            MentorQoshishFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, kurs)
//                        putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setClickBtn() {
        root.add_mentor_btn.setOnClickListener {
            val mentor_ismi = root.add_mentor_ismi_et.text.toString().trim()
            val mentor_familyasi = root.add_mentor_familyasi_et.text.toString().trim()
            val mentor_otasining_ismi = root.add_mentor_otasining_ismi_et.text.toString().trim()
            if (mentor_ismi.isNotEmpty() && mentor_familyasi.isNotEmpty() && mentor_otasining_ismi.isNotEmpty()) {
                if (!checkMentorName(mentor_ismi + " " + mentor_familyasi)) {
                    getDao?.insertMentor(
                        Mentor(
                            mentor_ismi,
                            mentor_familyasi,
                            mentor_otasining_ismi,
                            kurs!!.id
                        )
                    )
                    Snackbar.make(root, "Muvaffaqiyatli qo'shildi", Snackbar.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(
                        root,
                        "$mentor_ismi $mentor_familyasi nomli mentor mavjud",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
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