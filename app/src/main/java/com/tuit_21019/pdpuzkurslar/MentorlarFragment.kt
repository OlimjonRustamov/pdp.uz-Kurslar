package com.tuit_21019.pdpuzkurslar

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.add_kurs_dialog.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.*

private const val ARG_PARAM1 = "kurs"


class MentorlarFragment : Fragment() {
    private var param1: Kurs? = null

    lateinit var db: DbHelper
    lateinit var root:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        root = inflater.inflate(R.layout.fragment_mentorlar, container, false)
        db = DbHelper(root.context)

        setToolbar()

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
        root.toolbar.title=param1!!.kurs_nomi

        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        root.toolbar.setOnMenuItemClickListener { item ->
            if (item?.itemId == R.id.add_menu_btn) {
                Toast.makeText(root.context, "Hozir taxlaymiz", Toast.LENGTH_LONG).show()
            }
            true
        }
    }
}