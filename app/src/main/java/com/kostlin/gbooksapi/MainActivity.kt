package com.kostlin.gbooksapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kostlin.gbooksapi.databinding.ActivityMainBinding
import com.kostlin.gbooksapi.ui.main.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_home)
        supportFragmentManager.beginTransaction().replace(R.id.idLLsearch, HomeFragment()).commit()
    }
}