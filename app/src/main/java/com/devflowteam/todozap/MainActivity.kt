package com.devflowteam.todozap

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.devflowteam.domain.usecase.GetFirstLaunchUseCase
import com.devflowteam.todozap.databinding.ActivityMainBinding
import com.devflowteam.todozap.utils.getThemeColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val firstLaunchUseCase: GetFirstLaunchUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { v, insets ->
            val innerPadding = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(
                innerPadding.left,
                innerPadding.top,
                innerPadding.right,
                innerPadding.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
                as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch {
            setupGraph()
        }

        binding.bottomAppBar.apply {
            menu.children.forEach { menuItem ->
                MenuItemCompat.setIconTintList(
                    menuItem,
                    ColorStateList.valueOf(
                        getThemeColor(com.google.android.material.R.attr.colorOnPrimary)
                    )
                )
            }

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.homeFragment -> {
                        smartNavigation(com.devflowteam.feature_home.R.id.home_graph)
                    }
                    com.devflowteam.feature_language.R.id.languageFragment -> {
                        smartNavigation(com.devflowteam.feature_language.R.id.language_graph)
                    }
                    com.devflowteam.feature_server.R.id.serverFragment -> {
                        smartNavigation(com.devflowteam.feature_server.R.id.server_graph)
                    }
                    else -> false
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.devflowteam.feature_start.R.id.startFragment -> {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.background))

                    binding.topAppBar.visibility = View.GONE
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
                com.devflowteam.feature_home.R.id.homeFragment,
                com.devflowteam.feature_server.R.id.serverFragment,
                com.devflowteam.feature_language.R.id.languageFragment ->  {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer))

                    binding.topAppBar.visibility = View.GONE
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }
//                R.id.detailFragment, R.id.creationFragment -> {
//                    binding.bottomAppBar.visibility = View.GONE
//                    binding.fab.visibility = View.GONE
//                }
            }
        }
    }

    private fun setupGraph() {
        lifecycleScope.launch {
            val startDestination = if (firstLaunchUseCase().first()) com.devflowteam.feature_start.R.id.start_graph
            else com.devflowteam.feature_home.R.id.home_graph

            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
            navGraph.setStartDestination(startDestination)
            navController.setGraph(navGraph, null)
        }
    }

    private fun smartNavigation(destination: Int): Boolean {
        if (navController.currentDestination?.id != destination) {
            navController.navigate(
                resId = destination,
                args = null,
                navOptions = navOptions {
                    popUpTo(destination) {
                        inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            )
        }
        return true
    }
}


