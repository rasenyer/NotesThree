package com.rasenyer.notesthree.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.rasenyer.notesthree.R
import com.rasenyer.notesthree.databinding.ActivityMainBinding
import com.rasenyer.notesthree.vm.NoteViewModel
import com.rasenyer.notesthree.vm.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mToolbar)

        val noteViewModelFactory = NoteViewModelFactory(application)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory)[NoteViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}