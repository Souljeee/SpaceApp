package com.example.spaceapp.ui.main.EMWActivity.WeatherFragment

import com.example.spaceapp.ui.main.EMWActivity.MarsFragment.MarsDataState
import com.example.spaceapp.ui.main.EMWActivity.retrofit.MarsDTOFiles.ImageInfoDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.WeatherDTOFiles.WeatherDTO

sealed class WeatherDataState{
    data class Success(val serverResponseData: WeatherDTO) : WeatherDataState()
    data class Error(val error: Throwable) : WeatherDataState()
    data class Loading(val progress: Int?) : WeatherDataState()
}
