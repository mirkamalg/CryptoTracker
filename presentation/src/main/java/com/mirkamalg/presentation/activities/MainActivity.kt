package com.mirkamalg.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mirkamalg.presentation.R
import com.mirkamalg.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerMain) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureNavigation()
    }

    private fun configureNavigation() {
        binding.toolbar.setupWithNavController(
            navHostFragment.navController,
            AppBarConfiguration(topLevelDestinationIds = setOf(R.id.homeFragment))
        )
        lifecycleScope.launchWhenStarted {
            navHostFragment.navController.currentBackStackEntryFlow.collectLatest(::onDestinationChanged)
        }
    }

    private fun onDestinationChanged(entry: NavBackStackEntry) {
        //TODO handle
    }
}