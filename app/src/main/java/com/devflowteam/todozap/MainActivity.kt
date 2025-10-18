package com.devflowteam.todozap

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.devflowteam.core.utils.Links
import com.devflowteam.data.local.copyToClipboard
import com.devflowteam.data.remote.ApiService
import com.devflowteam.domain.usecase.local.todo.UpsertToDoUseCase
import com.devflowteam.feature_home.utils.Temp
import com.devflowteam.presentation.main.MainUIAction
import com.devflowteam.presentation.main.MainViewModel
import com.devflowteam.presentation.utils.getThemeColor
import com.devflowteam.todozap.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        setupBottomAppBar()
        setupFab()
        setupNavControllerDestinationListener()

        observeEvents()
        observeStates()
    }

    private fun setupGraph(isFirstLaunch: Boolean) {
        val startDestination = if (isFirstLaunch) com.devflowteam.feature_start.R.id.start_graph
        else com.devflowteam.feature_home.R.id.home_graph

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(startDestination)
        navController.setGraph(navGraph, null)
    }

    private fun setupLanguage(isFirstLaunch: Boolean) {
        if (isFirstLaunch) {
            val currentLocale = resources.configuration.locale
            val languageCodeList = resources.getStringArray(com.devflowteam.presentation.R.array.language_codes)

            languageCodeList.forEach { language ->
                if (language != "en" && language == currentLocale.language) {
                    viewModel.onMainUIAction(MainUIAction.ChangeLanguage(language))
                }
            }
        }
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel.isFirstLaunch
                .filterNotNull()
                .take(1)
                .collect { value ->
                    setupLanguage(value)
                    setupGraph(value)
                }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneTimeEvents
                    .collect { event ->
                        when (event) {
                            is MainViewModel.Events.NavigateTo -> {
                                navigate(event.destination)
                            }
                            is MainViewModel.Events.ShowToast -> {
                                Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                            }
                            is MainViewModel.Events.SendEmail -> {
                                sendEmail()
                            }
                            is MainViewModel.Events.CopyToClipboard -> {
                                copyToClipboard(event.title, event.id)
                            }
                        }
                    }
            }
        }
    }

    private fun setupNavControllerDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.devflowteam.feature_start.R.id.startFragment,
                com.devflowteam.feature_home.R.id.detailFragment -> {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.background))

                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
                com.devflowteam.feature_server.R.id.serverFragment,
                com.devflowteam.feature_language.R.id.languageFragment -> {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.background))

                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }
                com.devflowteam.feature_home.R.id.homeFragment ->  {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer))

                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                }
                com.devflowteam.feature_create.R.id.creationFragment -> {
                    binding.main.setBackgroundColor(getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer))

                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }
            }
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            viewModel.onMainUIAction(MainUIAction.NavigateTo(com.devflowteam.feature_create.R.id.creation_graph))
        }
    }

    private fun setupBottomAppBar() {
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
                    R.id.copyId -> {
                        viewModel.onMainUIAction(
                            MainUIAction.CopyId(
                                title = resources.getString(com.devflowteam.presentation.R.string.user_id),
                                toastMessage = resources.getString(com.devflowteam.presentation.R.string.copied)
                            )
                        )
                        true
                    }
                    R.id.contactUs -> {
                        viewModel.onMainUIAction(MainUIAction.ContactUs(resources.getString(com.devflowteam.presentation.R.string.missing_mail_app)))
                        true
                    }
                    else -> {
                        viewModel.onMainUIAction(MainUIAction.NavigateTo(menuItem.itemId))
                        true
                    }
                }
            }
        }
    }

    private fun navigate(destination: Int) {
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
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Links.MAIL))
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(com.devflowteam.presentation.R.string.app_name))
        }

        startActivity(emailIntent)
    }
}


