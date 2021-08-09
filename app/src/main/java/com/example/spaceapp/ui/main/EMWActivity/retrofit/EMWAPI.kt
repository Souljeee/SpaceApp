package com.example.spaceapp.ui.main.EMWActivity.retrofit

import com.example.spaceapp.ui.main.EMWActivity.retrofit.EarthDTOFiles.ImageDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.MarsDTOFiles.MarsDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.WeatherDTOFiles.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface EMWAPI {
    @GET("EPIC/api/natural/images")
    fun getPictureOfEarth(
        @Query("api_key") apiKey : String
    ) : Call<List<ImageDTO>>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfMars(
        @Query("sol") sol : Int,
        @Query("api_key") apiKey : String
    ) : Call<MarsDTO>

    @GET("DONKI/CME")
    fun getWeather(
        @Query("startDate") startDate : String,
        @Query("endDate") endDate : String,
        @Query("api_key") apiKey: String
    ) : Call<List<WeatherDTO>>

}