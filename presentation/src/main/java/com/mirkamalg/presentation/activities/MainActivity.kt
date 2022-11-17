package com.mirkamalg.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mirkamalg.presentation.R
import com.mirkamalg.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerMain) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleNotificationPermission()
        configureNavigation()
    }

    private fun configureNavigation() {
        binding.toolbar.setupWithNavController(
            navHostFragment.navController,
            AppBarConfiguration(topLevelDestinationIds = setOf(R.id.homeFragment))
        )
    }

    private fun handleNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return  //No need for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    if (it.not()) Toast.makeText(
                        this,
                        "Permission must be granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            Handler(mainLooper).postDelayed({
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }, 2000)
        }
    }
}