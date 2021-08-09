package com.example.spaceapp.ui.main.EMWActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceapp.R
import com.example.spaceapp.databinding.ActivityEmwBinding

class EMWActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }

    private fun initViewPager() = with(binding){
        emwViewPager.adapter = EMWViewPagerAdapter(supportFragmentManager)
        emwTabLayout.setupWithViewPager(emwViewPager)
    }

}