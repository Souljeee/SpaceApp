package com.example.spaceapp.ui.main.EMWActivity.EarthFragment

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spaceapp.databinding.EarthFragmentBinding
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class EarthFragment : Fragment() {

    private lateinit var binding: EarthFragmentBinding
    private val viewModel: EarthViewModel by lazy {
        ViewModelProviders.of(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EarthFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer <EarthDataState>{
            renderData(it)
        })
    }

    private fun renderData(data: EarthDataState) = with(binding) {
        when (data) {
            is EarthDataState.Success -> {
                val serverResponseData = data.serverResponseData
                val url = toURL(serverResponseData.date,serverResponseData.image)
                Log.d("tag","url = "+url)
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    val fade = Fade()
                    fade.duration = 1500
                    TransitionManager.beginDelayedTransition(contEarth,fade)
                    Picasso.get().load(url).into(earthImage)
                    earthImage.visibility = View.VISIBLE
                }
            }
            is EarthDataState.Loading -> { }
            is EarthDataState.Error -> { }
        }
    }

    private fun toURL(date: String, image: String): String {
        val sb  = StringBuilder()
        sb.append("https://api.nasa.gov/EPIC/archive/natural/")
        var newDate = date.replace("-","/",true)
        newDate = newDate.dropLast(9)
        sb.append("$newDate/png/$image.png?api_key=WOyi2ZK2i4wJqKmOtb2E46UmeQeYptvYdSL0bxAU")
        return sb.toString()
    }

    companion object {
        fun newInstance() = EarthFragment()
    }

}