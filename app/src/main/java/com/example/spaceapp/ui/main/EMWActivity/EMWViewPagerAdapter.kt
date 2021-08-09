package com.example.spaceapp.ui.main.EMWActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.spaceapp.ui.main.EMWActivity.EarthFragment.EarthFragment
import com.example.spaceapp.ui.main.EMWActivity.MarsFragment.MarsFragment
import com.example.spaceapp.ui.main.EMWActivity.WeatherFragment.WeatherFragment

class EMWViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> EarthFragment()
            1 -> MarsFragment()
            2 -> WeatherFragment()
            else -> EarthFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Earth"
            1 -> "Mars"
            2 -> "Weather"
            else -> "Earth"
        }
    }
}