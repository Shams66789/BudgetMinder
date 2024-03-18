package io.github.shams66789.budgetminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import io.github.shams66789.budgetminder.databinding.ActivityMainBinding
import io.github.shams66789.budgetminder.ui.HomeFragment
import io.github.shams66789.budgetminder.ui.SettingsFragment
import io.github.shams66789.budgetminder.ui.StatisticsFragment
import io.github.shams66789.budgetminder.ui.TransactionFragment

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Check if HomeFragment is not the current fragment
            if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) !is HomeFragment) {
                // Replace with HomeFragment
                replaceFragment(HomeFragment())
                // Update the Bottom Navigation Bar to show Home as the selected item
                binding.bottomNavigationView.setSelectedItemId(R.id.home)
            } else {
                // If HomeFragment is already displayed, let the system handle the back press
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateTransaction::class.java))
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.transaction -> {
                    replaceFragment(TransactionFragment())
                    true
                }
                R.id.statistics -> {
                    replaceFragment(StatisticsFragment())
                    true
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}