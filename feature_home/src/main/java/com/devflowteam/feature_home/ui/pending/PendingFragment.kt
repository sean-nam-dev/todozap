package com.devflowteam.feature_home.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devflowteam.domain.model.Status
import com.devflowteam.domain.model.ToDo
import com.devflowteam.feature_home.databinding.FragmentPendingBinding
import com.devflowteam.feature_home.ui.home.HomeUIAction
import com.devflowteam.feature_home.ui.home.HomeViewModel
import com.devflowteam.feature_home.utils.StaggeredAdapter
import com.devflowteam.feature_home.utils.StaggeredGridSpacingItemDecoration
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PendingFragment : Fragment() {

    private lateinit var binding: FragmentPendingBinding
    private val viewModel by activityViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { list ->
                    binding.recyclerView.apply {
                        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        adapter = StaggeredAdapter(
                            items = list.filter { it.status == Status.PENDING },
                            onItemClickAction = { currentId ->
                                viewModel.onHomeUIAction(HomeUIAction.PassID(currentId, true))
                            }
                        )
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
        }
    }
}