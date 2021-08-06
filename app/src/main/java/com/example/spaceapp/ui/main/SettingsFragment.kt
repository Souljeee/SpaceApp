package com.example.spaceapp.ui.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spaceapp.R
import com.example.spaceapp.databinding.FragmentSetttingsBinding
import com.example.spaceapp.databinding.MainFragmentBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSetttingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetttingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitch()
    }

    private fun initSwitch() = with(binding) {
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activity
                    ?.getSharedPreferences(nameSharedPreferences, MODE_PRIVATE)
                    ?.edit()
                    ?.putInt(appTheme, nightThemeCode)
                    ?.apply()
                dayModeSwitch.isChecked = false
                activity?.recreate()
            }
            dayModeSwitch.isChecked = false
        }

        dayModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activity
                    ?.getSharedPreferences(nameSharedPreferences, MODE_PRIVATE)
                    ?.edit()
                    ?.putInt(appTheme, dayThemeCode)
                    ?.apply()
                nightModeSwitch.isChecked = false
                activity?.recreate()
            }
            nightModeSwitch.isChecked = false
        }
    }


    companion object {
        fun newInstance() = SettingsFragment()
        const val dayThemeCode = 0
        const val nightThemeCode = 1
        const val nameSharedPreferences = "name"
        const val appTheme = "APP_THEME"
    }
}