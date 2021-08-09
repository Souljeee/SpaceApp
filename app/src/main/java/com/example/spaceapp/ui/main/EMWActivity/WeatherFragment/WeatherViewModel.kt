package com.example.spaceapp.ui.main.EMWActivity.WeatherFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.ui.main.EMWActivity.EarthFragment.EarthDataState
import com.example.spaceapp.ui.main.EMWActivity.MarsFragment.MarsDataState
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EMWAPIImpl
import com.example.spaceapp.ui.main.EMWActivity.retrofit.MarsDTOFiles.MarsDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.WeatherDTOFiles.WeatherDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(
    private val liveDataForViewToObserve: MutableLiveData<WeatherDataState> = MutableLiveData(),
    private val retrofitImpl: EMWAPIImpl = EMWAPIImpl()
) : ViewModel() {
    fun getData(): LiveData<WeatherDataState> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = WeatherDataState.Loading(null)
        val apiKey = "WOyi2ZK2i4wJqKmOtb2E46UmeQeYptvYdSL0bxAU"
        if (apiKey.isBlank()) {
            WeatherDataState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getWeather("yyyy-MM-dd","yyyy-MM-dd",apiKey).enqueue(object : Callback<List<WeatherDTO>> {
                override fun onResponse(call: Call<List<WeatherDTO>>, response: Response<List<WeatherDTO>>) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = WeatherDataState.Success(response.body()!![0])
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                WeatherDataState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                WeatherDataState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<WeatherDTO>>, t: Throwable) {
                    liveDataForViewToObserve.value = WeatherDataState.Error(t)
                }
            })
        }
    }
}