package com.tuit_21019.pdpuzkurslar.guruhlar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tuit_21019.pdpuzkurslar.guruhlar.GroupByStatusFragment
import com.tuit_21019.pdpuzkurslar.models.Guruh

class GroupViewPagerAdapter(
    var groupList: ArrayList<Guruh>,
    fragmentManager: FragmentManager, var kursID:Int
) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return GroupByStatusFragment.newInstance(position + 1,kursID)
    }
}