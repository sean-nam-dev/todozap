package com.devflowteam.feature_server

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devflowteam.core.utils.Links
import com.devflowteam.feature_server.databinding.FragmentServerBinding
import com.devflowteam.presentation.utils.openWebsite
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServerFragment : Fragment(R.layout.fragment_server) {

    private var _binding: FragmentServerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ServerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
        observeState()
        applyListeners()
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
                    viewModel.onServerUIAction(ServerUIAction.OnApplyClick(textView.text.toString()))
                    true
                } else {
                    false
                }
            }
        }

        binding.buttonSeeInstructions.setOnClickListener {
            requireContext().openWebsite(Links.INSTRUCTIONS)
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collect { event ->
                        when (event) {
                            ServerViewModel.Events.ShowErrorToast -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(com.devflowteam.presentation.R.string.could_not_connect_to_server),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            ServerViewModel.Events.ShowSuccessfulToast -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(com.devflowteam.presentation.R.string.server_has_been_changed_successfully),
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
                    binding.progressBar.apply {
                        visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }
}