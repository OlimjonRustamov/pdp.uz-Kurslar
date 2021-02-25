package com.tuit_21019.pdpuzkurslar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.adapters.KurslarAdapter
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.add_kurs_dialog.view.*
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//komment qo'shdim

class Barcha_kurslarFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var db:DbHelper

    lateinit var root: View
    lateinit var kurslarAdapter: KurslarAdapter
    lateinit var kurslarList:ArrayList<Kurs>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        @SuppressLint("UseRequireInsteadOfGet")
        db=DbHelper(this.context!!)

        root = inflater.inflate(R.layout.fragment_barcha_kurslar, container, false)

        root.toolbar.title="Barcha kurslar"


        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        root.toolbar.setOnMenuItemClickListener(object : androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item?.itemId == R.id.add_menu_btn) {
                    val dialog = AlertDialog.Builder(root.context)
                    val view=LayoutInflater.from(root.context).inflate(R.layout.add_kurs_dialog,null,false)
                    dialog.setView(view)
                    dialog.setPositiveButton("Qo'shish", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val kurs_nomi_et=view.add_kurs_dialog_et.text.toString()
                            db.insertKurs(Kurs(kurs_nomi_et))
                            dialog?.cancel()
                            Toast.makeText(root.context, "Muvaffaqiyatli qo'shildi!", Toast.LENGTH_LONG).show()
                            kurslarList.add(Kurs(kurs_nomi_et))
                            kurslarAdapter.notifyItemInserted(kurslarList.size-1)
                            kurslarAdapter.notifyItemRangeChanged(kurslarList.size-1,kurslarList.size)
                        }
                    })
                    dialog.setNegativeButton("Yopish", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.cancel()
                        }
                    })
                    dialog.show()
                }
                return true
            }

        })

        kurslarList = db.getAllKurs()

        kurslarAdapter=KurslarAdapter(kurslarList)
        root.kurslar_recyclerview.adapter = kurslarAdapter
        root.kurslar_recyclerview.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Barcha_kurslarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}