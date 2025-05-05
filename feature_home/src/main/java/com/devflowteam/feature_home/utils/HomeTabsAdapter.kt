package com.devflowteam.feature_home.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devflowteam.feature_home.ui.complete.CompleteFragment
import com.devflowteam.feature_home.ui.overdue.OverdueFragment
import com.devflowteam.feature_home.ui.pending.PendingFragment

class HomeTabsAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverdueFragment()
            1 -> PendingFragment()
            2 -> CompleteFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}