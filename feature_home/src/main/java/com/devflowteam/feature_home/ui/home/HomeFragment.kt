package com.devflowteam.feature_home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devflowteam.feature_home.databinding.FragmentHomeBinding
import com.devflowteam.feature_home.utils.HomeTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabAdapter = HomeTabsAdapter(this)

        binding.viewPager.adapter = tabAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(com.devflowteam.presentation.R.string.overdue)
                1 -> getString(com.devflowteam.presentation.R.string.pending)
                2 -> getString(com.devflowteam.presentation.R.string.complete)
                else -> ""
            }
        }.attach()

        binding.viewPager.setCurrentItem(1, false)
    }
}