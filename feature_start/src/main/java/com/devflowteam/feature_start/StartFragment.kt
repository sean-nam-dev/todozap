package com.devflowteam.feature_start

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        binding.textInputEditTextServerChange.apply {
            setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(this.windowToken, 0)
                    viewModel.onStartUIAction(
                        StartUIAction.DoneServerChangeClickListener(
                            newServer = textView.text.toString(),
                            successMessage = getString(com.devflowteam.presentation.R.string.server_has_been_changed_successfully),
                            errorMessage = getString(com.devflowteam.presentation.R.string.could_not_connect_to_server)
                        )
                    )
                    true
                } else {
                    false
                }
            }
        }

        binding.buttonFinish.setOnClickListener {
            viewModel.onStartUIAction(
                StartUIAction.FinishClickListener(
                    resources.getString(com.devflowteam.presentation.R.string.server_connection_error)
                )
            )
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.ownRadioButtonServer -> viewModel.onStartUIAction(StartUIAction.ChangeServerType(0))
                R.id.defaultRadioButtonServer -> viewModel.onStartUIAction(StartUIAction.ChangeServerType(1))
            }
        }

        binding.buttonRestoreAccount.setOnClickListener {
            viewModel.onStartUIAction(StartUIAction.RestoreAccountClickListener)
        }

        binding.buttonSeeInstructions.setOnClickListener {
            viewModel.onStartUIAction(StartUIAction.SeeInstructions(Links.INSTRUCTIONS))
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
            onPositiveButtonClickListener = {
                viewModel.onStartUIAction(
                    StartUIAction.ApplyClickListener(
                        id = editText.text.toString().trim(),
                        successMessage = resources.getString(com.devflowteam.presentation.R.string.successfully_set),
                        errorMessage = resources.getString(com.devflowteam.presentation.R.string.invalid_id)
                    )
                )
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
                            is StartViewModel.Events.NavigateToHome -> {
                                findNavController().navigate(
                                    directions = StartFragmentDirections.fromFeatureStartToFeatureHome(),
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(R.id.startFragment, true)
                                        .build()
                                )
                            }
                            is StartViewModel.Events.CloseDialog -> {
                                dialog.dismiss()
                            }
                            is StartViewModel.Events.OpenDialog -> {
                                dialog.show()
                            }
                            is StartViewModel.Events.OpenWebsite -> {
                                requireContext().openWebsite(event.link)
                            }
                            is StartViewModel.Events.ShowToast -> {
                                Toast.makeText(
                                    requireContext(),
                                    event.message,
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
                    binding.textInputLayoutTextServerChange.apply {
                        visibility = if (state.isDefaultServer) View.GONE else View.VISIBLE
                        isEnabled = !state.isDefaultServer
                    }
                    binding.buttonSeeInstructions.apply {
                        visibility = if (state.isDefaultServer) View.GONE else View.VISIBLE
                        isEnabled = !state.isDefaultServer
                    }
                    binding.progressBar.apply {
                        visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }
}