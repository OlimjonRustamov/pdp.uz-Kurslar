package com.tuit_21019.pdpuzkurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuit_21019.pdpuzkurslar.models.Kurs

private const val ARG_PARAM1 = "kurs"
//kursni o'qib oladi
//keyinroq kurs_id kerak bo'ladi
class KurshaqidaFragment : Fragment() {
    private var param1: Kurs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kurshaqida, container, false)
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
}