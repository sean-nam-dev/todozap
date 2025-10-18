package com.devflowteam.feature_home.ui.page

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
import com.devflowteam.feature_home.databinding.FragmentPageBinding
import com.devflowteam.feature_home.ui.home.HomeUIAction
import com.devflowteam.feature_home.ui.home.HomeViewModel
import com.devflowteam.feature_home.utils.StaggeredAdapter
import com.devflowteam.feature_home.utils.StaggeredGridSpacingItemDecoration
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PageFragment : Fragment() {

    private lateinit var binding: FragmentPageBinding
    private val viewModel by activityViewModel<HomeViewModel>()

    private lateinit var status: Status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        status = arguments?.getSerializable(ARG_STATUS) as? Status
            ?: throw IllegalStateException("Status is required")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
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
                            items = list.filter { it.status == status },
                            onItemClickAction = { currentId ->
                                viewModel.onHomeUIAction(
                                    HomeUIAction.PassID(
                                        currentId,
                                        Status.PENDING == status
                                    )
                                )
                            }
                        )
                        setPadding(20, 20, 20, 60)
                    }
                }
            }
        }
    }

    companion object {
        private const val ARG_STATUS = "arg_status"

        fun newInstance(status: Status): PageFragment {
            val fragment = PageFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_STATUS, status)
            fragment.arguments = bundle
            return fragment
        }
    }
}