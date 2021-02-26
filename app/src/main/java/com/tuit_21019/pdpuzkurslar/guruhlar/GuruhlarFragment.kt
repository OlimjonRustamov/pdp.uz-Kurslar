package com.tuit_21019.pdpuzkurslar.guruhlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.models.Kurs

private const val ARG_PARAM1 = "kurs"

//kursni olib keladi
//keyinchalik id kerak
class GuruhlarFragment : Fragment() {
    private var param1: Kurs? = null

    lateinit var root: View

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
        root = inflater.inflate(R.layout.fragment_guruhlar, container, false)

        return root
    }

    companion object {
        fun newInstance(param1: Kurs) =
                GuruhlarFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }
}