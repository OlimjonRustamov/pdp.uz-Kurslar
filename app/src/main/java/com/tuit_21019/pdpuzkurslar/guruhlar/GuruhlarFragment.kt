package com.tuit_21019.pdpuzkurslar.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.tuit_21019.pdpuzkurslar.DataBase.DbHelper
import com.tuit_21019.pdpuzkurslar.R
import com.tuit_21019.pdpuzkurslar.guruhlar.adapters.GroupViewPagerAdapter
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import kotlinx.android.synthetic.main.fragment_guruhlar.view.*
import kotlinx.android.synthetic.main.tab_item.view.*

private const val ARG_PARAM1 = "kurs"

//kursni olib keladi
//keyinchalik id kerak
class GuruhlarFragment : Fragment() {
    private var param1: Kurs? = null

    lateinit var root: View
    lateinit var db: DbHelper
    private var groupList: ArrayList<Guruh>? = null
    private var adapter: GroupViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs
        }
        setHasOptionsMenu(true)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = DbHelper(this.context!!)
        root = inflater.inflate(R.layout.fragment_guruhlar, container, false)
        Log.d("AAAA", "KursID: ${param1?.id}")
        setToolBar()

        loadData()
        loadAdapters()
        addClick()
        backBtnClick()

        return root
    }

    private fun setToolBar() {
        root.toolbar.title=param1!!.kurs_nomi
        root.toolbar.menu.getItem(0).isVisible = false
    }

    private fun backBtnClick() {
        root.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addClick() {
        root.toolbar.setOnMenuItemClickListener { it->
            if (it.itemId == R.id.add_menu_btn) {
                val bundle = Bundle()
                bundle.putInt("kursID", param1!!.id!!)
                findNavController().navigate(R.id.addGroup,bundle)
            }
            true
        }

    }

    private fun loadAdapters() {
        adapter = GroupViewPagerAdapter(groupList!!, childFragmentManager, param1?.id!!)
        root.group_view_pager.adapter = adapter
        root.tab_layout.setupWithViewPager(root.group_view_pager)
        setTabs()
    }

    @SuppressLint("SetTextI18n")
    private fun setTabs() {

        val tabCount = root.tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.tab_layout.getTabAt(i)
            tab?.customView = tabView
            when (i) {
                0 -> tabView.title_tv.text = "Ochilgan guruhlar"
                1 -> tabView.title_tv.text = "Ochilayotgan guruhlar"
            }
        }

        root.tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    if (!root.toolbar.menu.getItem(0).isVisible) {
                        root.toolbar.menu.getItem(0).isVisible = true
                    }
                }
                if (tab?.position == 0) {
                    root.toolbar.menu.getItem(0).isVisible = false
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    if (root.toolbar.menu.getItem(0).isVisible) {
                        root.toolbar.menu.getItem(0).isVisible = false
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun loadData() {
        groupList = ArrayList()
        if (db.getAllGroupByStatus(1).isEmpty()) {
            db.insertGuruh(Guruh("Android 5", 1, 1, param1?.id, "10:00-12:00"))
            db.insertGuruh(Guruh("Android 4", 1, 1, param1?.id, "10:00-12:00"))
        }

        groupList!!.addAll(db.getGroupByKursIdAndStatus(1,param1?.id!!))
        groupList!!.addAll(db.getGroupByKursIdAndStatus(0,param1?.id!!))

        Log.d("AAAA", "loadData: ${groupList!!.size}")
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