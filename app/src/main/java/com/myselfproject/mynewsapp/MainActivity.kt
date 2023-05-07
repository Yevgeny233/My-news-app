package com.myselfproject.mynewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.myselfproject.mynewsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.startFragment
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController
        pickerBottomNavigation(navController)
    }

    private fun pickerBottomNavigation(navController: NavController) {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.startFragment, R.id.secondFragment
            )
        )
        binding.bottomNavigation.setupWithNavController(navController)
    }

}