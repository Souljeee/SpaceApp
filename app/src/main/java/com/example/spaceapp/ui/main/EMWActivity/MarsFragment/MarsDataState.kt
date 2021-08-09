package com.example.spaceapp.ui.main.EMWActivity.MarsFragment

import com.example.spaceapp.ui.main.EMWActivity.EarthFragment.EarthDataState
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EarthDTOFiles.ImageDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.MarsDTOFiles.ImageInfoDTO

sealed class MarsDataState{
    data class Success(val serverResponseData: ImageInfoDTO) : MarsDataState()
    data class Error(val error: Throwable) : MarsDataState()
    data class Loading(val progress: Int?) : MarsDataState()
}
