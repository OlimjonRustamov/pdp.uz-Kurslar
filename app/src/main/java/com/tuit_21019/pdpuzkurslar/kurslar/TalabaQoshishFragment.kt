package com.tuit_21019.pdpuzkurslar.kurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs

private const val ARG_PARAM1 = "kurs"

class TalabaQoshishFragment : Fragment() {
    private var param1: Kurs? = null
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
    ): View {
        root = inflater.inflate(R.layout.fragment_talaba_qoshish, container, false)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Kurs) =
            TalabaQoshishFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}