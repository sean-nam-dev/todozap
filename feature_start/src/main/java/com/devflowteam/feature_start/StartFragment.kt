package com.devflowteam.feature_start

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devflowteam.feature_start.databinding.FragmentStartBinding
import com.devflowteam.core.utils.Links
import com.devflowteam.presentation.component.getCustomDialog
import com.devflowteam.presentation.utils.getThemeColor
import com.devflowteam.presentation.utils.openWebsite
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment(R.layout.fragment_start) {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StartViewModel by viewModel()

    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        initDialog()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyColors()
        applyListeners()
        observeEvents()
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyListeners() {
        binding.buttonFinish.setOnClickListener {
            viewModel.onStartUIAction(StartUIAction.FinishClickAction)
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.defaultRadioButtonServer -> viewModel.onStartUIAction(StartUIAction.ChangeServerTypeAction(0))
                R.id.ownRadioButtonServer -> viewModel.onStartUIAction(StartUIAction.ChangeServerTypeAction(1))
            }
        }

        binding.buttonRestoreAccount.setOnClickListener {
            dialog.show()
        }

        binding.buttonSeeInstructions.setOnClickListener {
            requireContext().openWebsite(Links.INSTRUCTIONS)
        }
    }

    private fun initDialog() {
        val editText = TextInputEditText(requireContext()).apply {
            maxLines = 1
            setTextColor(requireContext().getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
        }

        dialog = getCustomDialog(
            context = requireContext(),
            innerView = editText,
            titleText = resources.getString(com.devflowteam.presentation.R.string.enter_your_previous_id),
            positiveButtonText = resources.getString(com.devflowteam.presentation.R.string.apply),
            negativeButtonText = resources.getString(com.devflowteam.presentation.R.string.cancel),
            extractValue = { editText.text.toString().trim() },
            onPositiveButtonClickListener = { id: String ->
                viewModel.onStartUIAction(StartUIAction.ApplyIDClickAction(id))
            }
        )
    }

    private fun applyColors() {
        val color = ColorStateList.valueOf(requireContext().getThemeColor(com.google.android.material.R.attr.colorSecondary))

        binding.defaultRadioButtonServer.buttonTintList = color
        binding.ownRadioButtonServer.buttonTintList = color
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collect { event ->
                        when (event) {
                            StartViewModel.Events.NavigateToHome -> {
                                findNavController().navigate(
                                    directions = StartFragmentDirections.fromFeatureStartToFeatureHome(),
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(R.id.startFragment, true)
                                        .build()
                                )
                            }
                            StartViewModel.Events.ShowToastInvalidIdError -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(com.devflowteam.presentation.R.string.invalid_id),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            StartViewModel.Events.ShowToastSuccessSet -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(com.devflowteam.presentation.R.string.successfully_set),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            StartViewModel.Events.ShowToastNoServerConnection -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(com.devflowteam.presentation.R.string.server_connection_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.textInputOwnServer.apply {
                        visibility = if (state.isLinkInputVisible) View.VISIBLE else View.GONE
                        isEnabled = state.isLinkInputVisible
                    }
                    binding.buttonSeeInstructions.apply {
                        visibility = if (state.isLinkInputVisible) View.VISIBLE else View.GONE
                        isEnabled = state.isLinkInputVisible
                    }
                    binding.progressBar.apply {
                        visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }
}