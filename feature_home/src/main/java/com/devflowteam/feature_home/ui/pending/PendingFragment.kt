package com.devflowteam.feature_home.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devflowteam.feature_home.databinding.FragmentPendingBinding
import com.devflowteam.feature_home.utils.StaggeredAdapter
import com.devflowteam.feature_home.utils.StaggeredGridSpacingItemDecoration
import com.devflowteam.feature_home.utils.Temp

class PendingFragment: Fragment() {

    private lateinit var binding: FragmentPendingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = StaggeredAdapter(Temp.getItems()) {
                TODO()
            }
            addItemDecoration(
                StaggeredGridSpacingItemDecoration(
                    spanCount = 2,
                    spacingDp = 15F,
                    includeEdge = true,
                    context = requireContext()
                )
            )
            setPadding(0, 0, 0, 40)
        }
    }
}