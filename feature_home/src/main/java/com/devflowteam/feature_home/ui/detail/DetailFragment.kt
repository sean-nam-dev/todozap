package com.devflowteam.feature_home.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devflowteam.core.utils.TimeFormatManager
import com.devflowteam.domain.model.ToDo
import com.devflowteam.feature_home.R
import com.devflowteam.feature_home.databinding.FragmentDetailBinding
import com.devflowteam.feature_home.ui.home.HomeUIAction
import com.devflowteam.feature_home.ui.home.HomeViewModel
import com.devflowteam.presentation.utils.getThemeColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel by activityViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { toDoList ->
                    val currentToDoItem = toDoList.firstOrNull { it.id == args.id }

                    if (currentToDoItem != null) {
                        binding.topAppBar.title = TimeFormatManager.transform(
                            currentToDoItem.date,
                            TimeFormatManager.Format.DayMonthYearFormat
                        )

                        binding.title.text = currentToDoItem.title

                        binding.text.text = currentToDoItem.text

                        binding.swipeButton.apply {
                            if (args.isPending) {
                                visibility = View.VISIBLE
                                isEnabled = true

                                setOnActiveListener {
                                    viewModel.onHomeUIAction(HomeUIAction.CompleteClickAction(currentToDoItem))
                                }
                            } else {
                                visibility = View.GONE
                                isEnabled = false
                            }
                        }

                        binding.topAppBar.apply {
                            setOnMenuItemClickListener { menuItem ->
                                when (menuItem.itemId) {
                                    com.devflowteam.presentation.R.id.delete -> {
                                        viewModel.onHomeUIAction(HomeUIAction.DeleteClickAction(currentToDoItem))
                                        true
                                    }
                                    else -> {
                                        false
                                    }
                                }
                            }
                            setNavigationOnClickListener {
                                findNavController().popBackStack()
                            }
                            menu.children.forEach { menuItem ->
                                MenuItemCompat.setIconTintList(
                                    menuItem,
                                    ColorStateList.valueOf(
                                        requireContext().getThemeColor(com.google.android.material.R.attr.colorOnPrimary)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collectLatest { event ->
                        when (event) {
                            HomeViewModel.Events.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            else -> {

                            }
                        }
                    }
            }
        }
    }
}