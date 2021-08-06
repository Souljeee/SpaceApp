package com.example.spaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceapp.ui.main.MainFragment
import com.example.spaceapp.ui.main.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme(R.style.Theme_SpaceApp_Day))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    private fun getAppTheme(codeStyle: Int): Int {
        return codeStyleToStyleId(getCodeStyle(codeStyle))
    }

    private fun codeStyleToStyleId(codeStyle: Int): Int {
        when(codeStyle){
            SettingsFragment.dayThemeCode -> return R.style.Theme_SpaceApp_Day
            SettingsFragment.nightThemeCode -> return R.style.Theme_SpaceApp_Night
            else -> return R.style.Theme_SpaceApp_Day
        }
    }

    private fun getCodeStyle(codeStyle: Int): Int {
        return getSharedPreferences(SettingsFragment.nameSharedPreferences, MODE_PRIVATE)
                    .getInt(SettingsFragment.appTheme,codeStyle)
    }
}