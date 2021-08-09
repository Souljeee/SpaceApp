package com.example.spaceapp.ui.main.EMWActivity.EarthFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EMWAPIImpl
import com.example.spaceapp.ui.main.EMWActivity.retrofit.EarthDTOFiles.ImageDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EarthViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthDataState> = MutableLiveData(),
    private val retrofitImpl: EMWAPIImpl = EMWAPIImpl()
) : ViewModel() {

    fun getData(): LiveData<EarthDataState> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = EarthDataState.Loading(null)
        val apiKey = "WOyi2ZK2i4wJqKmOtb2E46UmeQeYptvYdSL0bxAU"
        if (apiKey.isBlank()) {
            EarthDataState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfEarth(apiKey).enqueue(object : Callback<List<ImageDTO>> {
                override fun onResponse(call: Call<List<ImageDTO>>, response: Response<List<ImageDTO>>) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = EarthDataState.Success(response.body()!![0])
                        Log.d("tag",Calendar.getInstance().toString())
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                EarthDataState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                EarthDataState.Error(Throwable(message))

                        }
                    }
                }

                override fun onFailure(call: Call<List<ImageDTO>>, t: Throwable) {
                    liveDataForViewToObserve.value = EarthDataState.Error(t)
                }
            })
        }
    }
}