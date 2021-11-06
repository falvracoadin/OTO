package com.example.oto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oto.adapter.MainPagerAdapter
import com.example.oto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = MainPagerAdapter(supportFragmentManager)
        binding.tab.setupWithViewPager(binding.pager)
    }
}