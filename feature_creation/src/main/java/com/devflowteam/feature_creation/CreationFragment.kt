package com.devflowteam.feature_creation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.MenuItemCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.devflowteam.core.utils.TimeFormatManager
import com.devflowteam.feature_create.R
import com.devflowteam.feature_create.databinding.FragmentCreationBinding
import com.devflowteam.presentation.utils.getThemeColor
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.util.Date

class CreationFragment : Fragment(R.layout.fragment_creation) {

    private var _binding: FragmentCreationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<CreationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
        applyViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyViews() {
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    com.devflowteam.presentation.R.id.schedule -> {
                        viewModel.onCreationUIAction(CreationUIAction.OnCalendarClick)
                        true
                    }
                    else -> false
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

        binding.saveButton.setOnClickListener {
            viewModel.onCreationUIAction(
                CreationUIAction.OnSaveClick(
                    binding.title.text.toString(),
                    binding.text.text.toString(),
                    getString(com.devflowteam.presentation.R.string.something_went_wrong)
                )
            )
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collectLatest { event ->
                        when (event) {
                            is CreationViewModel.Events.Back -> {
                                findNavController().popBackStack()
                            }
                            is CreationViewModel.Events.OpenCalendar -> {
                                val constraintsBuilder =
                                    CalendarConstraints.Builder()
                                        .setValidator(DateValidatorPointForward.now())

                                val datePicker =
                                    MaterialDatePicker.Builder.datePicker()
                                        .setTitleText(getString(com.devflowteam.presentation.R.string.select_date))
                                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds() + 24 * 60 * 60 * 1000)
                                        .setCalendarConstraints(constraintsBuilder.build())
                                        .setTheme(com.devflowteam.presentation.R.style.ThemeOverlay_App_DatePicker)
                                        .build()

                                datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
                                    viewModel.onCreationUIAction(
                                        CreationUIAction.OnPickerSaveClick(
                                            TimeFormatManager.transformFromMillis(selectedDateInMillis)
                                        )
                                    )
                                }

                                datePicker.show(requireFragmentManager(), null)
                            }
                            is CreationViewModel.Events.ShowToast -> {
                                Toast.makeText(
                                    requireContext(),
                                    event.text,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }
    }
}