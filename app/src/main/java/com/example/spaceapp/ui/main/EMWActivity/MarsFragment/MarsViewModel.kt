package com.example.spaceapp.ui.main.EMWActivity.MarsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.ui.main.EMWActivity.EarthFragment.EarthDataState
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EMWAPIImpl
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EarthDTOFiles.ImageDTO
import com.example.spaceapp.ui.main.EMWActivity.retrofit.MarsDTOFiles.MarsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsDataState> = MutableLiveData(),
    private val retrofitImpl: EMWAPIImpl = EMWAPIImpl()
) : ViewModel() {
    fun getData(): LiveData<MarsDataState> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsDataState.Loading(null)
        val apiKey = "WOyi2ZK2i4wJqKmOtb2E46UmeQeYptvYdSL0bxAU"
        if (apiKey.isBlank()) {
            EarthDataState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfMars(1000,apiKey).enqueue(object : Callback<MarsDTO> {
                override fun onResponse(call: Call<MarsDTO>, response: Response<MarsDTO>) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = MarsDataState.Success(response.body()!!.photos[0])
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                MarsDataState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                MarsDataState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsDTO>, t: Throwable) {
                    liveDataForViewToObserve.value = MarsDataState.Error(t)
                }
            })
        }
    }
}