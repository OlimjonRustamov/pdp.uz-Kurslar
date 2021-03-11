package com.tuit_21019.pdpuzkurslar.kurslar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.fragment_barcha_kurslar.view.toolbar
import kotlinx.android.synthetic.main.fragment_kurshaqida.view.*

private const val ARG_PARAM1 = "kurs"

//kursni o'qib oladi
//keyinroq kurs_id kerak bo'ladi
class KurshaqidaFragment : Fragment() {
    private var param1: Kurs? = null

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_kurshaqida, container, false)

        setToolbar_tv()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Kurs) =
            KurshaqidaFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

    private fun setToolbar_tv() {
        root.toolbar.title = param1!!.kurs_nomi
        root.kurs_haqida_textview.text = param1!!.kurs_haqida
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        root.toolbar.setOnMenuItemClickListener { item ->
            if (item?.itemId == R.id.add_menu_btn) {
                val bundle = Bundle()
                bundle.putSerializable("kurs", param1)
                findNavController().navigate(R.id.mentorQoshishFragment, bundle)
            }
            true
        }
        root.kurs_haqida_talaba_qoshish.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("kurs", param1!!)
            findNavController().navigate(R.id.talabaQoshishFragment, bundle)
        }
    }
}