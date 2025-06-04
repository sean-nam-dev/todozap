package com.devflowteam.feature_home.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.devflowteam.feature_home.databinding.FragmentHomeBinding
import com.devflowteam.feature_home.utils.HomeTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()

        binding.viewPager.adapter = HomeTabsAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(com.devflowteam.presentation.R.string.pending)
                1 -> getString(com.devflowteam.presentation.R.string.overdue)
                2 -> getString(com.devflowteam.presentation.R.string.complete)
                else -> ""
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collectLatest { event ->
                        when (event) {
                            is HomeViewModel.Events.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is HomeViewModel.Events.NavigateToDetail -> {
                                val action = HomeFragmentDirections.fromFeatureHomeToFeatureDetail(
                                    event.id,
                                    event.isPending
                                )

                                findNavController().navigate(action)
                            }
                        }
                    }
            }
        }
    }
}