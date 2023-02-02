package com.udacity.shoestore.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        NavigationUI.setupActionBarWithNavController(this, navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            actionBar?.setDisplayHomeAsUpEnabled(nd.id == R.id.shoeDetailFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_logout -> {
                navController.navigate(R.id.signUpFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}