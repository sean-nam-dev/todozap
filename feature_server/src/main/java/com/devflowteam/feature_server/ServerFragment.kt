package com.devflowteam.feature_server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devflowteam.feature_server.databinding.FragmentServerBinding

class ServerFragment : Fragment() {

    private lateinit var binding: FragmentServerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServerBinding.inflate(inflater, container, false)

        return binding.root
    }
}