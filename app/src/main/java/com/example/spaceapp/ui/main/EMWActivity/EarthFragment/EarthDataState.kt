package com.example.spaceapp.ui.main.EMWActivity.EarthFragment

import com.example.spaceapp.ui.main.EMWActivity.retrofit.EarthDTOFiles.ImageDTO

sealed class EarthDataState{
    data class Success(val serverResponseData: ImageDTO) : EarthDataState()
    data class Error(val error: Throwable) : EarthDataState()
    data class Loading(val progress: Int?) : EarthDataState()
}
