package com.devflowteam.feature_home.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devflowteam.domain.model.Status
import com.devflowteam.feature_home.ui.page.PageFragment

class HomeTabsAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PageFragment.newInstance(Status.PENDING)
            1 -> PageFragment.newInstance(Status.OVERDUE)
            2 -> PageFragment.newInstance(Status.COMPLETE)
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}