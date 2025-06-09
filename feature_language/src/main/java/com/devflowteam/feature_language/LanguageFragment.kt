package com.devflowteam.feature_language

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devflowteam.feature_language.databinding.FragmentLanguageBinding
import com.devflowteam.presentation.utils.getThemeColor
import com.devflowteam.presentation.utils.updateLocale
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LanguageFragment : Fragment(R.layout.fragment_language) {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LanguageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyColors()
        observeStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeStates() {
        val languageCodeList = resources.getStringArray(com.devflowteam.presentation.R.array.language_codes)
        val languageList = resources.getStringArray(com.devflowteam.presentation.R.array.languages)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.radioGroup.children
                        .filterIsInstance<RadioButton>()
                        .forEachIndexed { index, radioButton ->
                            radioButton.apply {
                                text = languageList[index]
                                isChecked = languageCodeList[index] == state.languageCode
                                setOnClickListener {
                                    requireContext().updateLocale(languageCodeList[index])
                                    viewModel.onLanguageUIAction(LanguageUIAction.RadioClickAction(languageCodeList[index]))
                                }
                            }
                        }
                }
            }
        }
    }

    private fun applyColors() {
        val color = ColorStateList.valueOf(requireContext().getThemeColor(com.google.android.material.R.attr.colorSecondary))

        binding.enRadioButton.buttonTintList = color
        binding.ruRadioButton.buttonTintList = color
    }
}