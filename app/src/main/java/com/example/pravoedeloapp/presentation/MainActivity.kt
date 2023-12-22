package com.example.pravoedeloapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.pravoedeloapp.R
import com.example.pravoedeloapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        val navController: NavController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        navController.currentBackStackEntry
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        binding.toolBar.setupWithNavController(navController,appBarConfiguration)
    }
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}