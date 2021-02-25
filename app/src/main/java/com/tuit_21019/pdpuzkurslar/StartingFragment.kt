package com.tuit_21019.pdpuzkurslar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.fragment_starting.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StartingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var root:View
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            activity?.window?.statusBarColor=Color.WHITE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_starting, container, false)

        btnsClick()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun btnsClick() {
        root.mentorlar_cardview.setOnClickListener{
            findNavController().navigate(R.id.mentorlarFragment)
        }
        root.guruhlar_cardview.setOnClickListener{
            findNavController().navigate(R.id.guruhlarFragment)
        }
        root.kurslar_cardview.setOnClickListener {
            findNavController().navigate(R.id.barcha_kurslarFragment)
        }
    }
}