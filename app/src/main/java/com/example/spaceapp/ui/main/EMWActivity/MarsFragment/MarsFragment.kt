package com.example.spaceapp.ui.main.EMWActivity.MarsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spaceapp.databinding.MarsFragmentBinding
import com.example.spaceapp.ui.main.EMWActivity.EarthFragment.EarthDataState
import com.squareup.picasso.Picasso

class MarsFragment : Fragment() {

    private lateinit var binding : MarsFragmentBinding
    private val viewModel: MarsViewModel by lazy {
        ViewModelProviders.of(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MarsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer <MarsDataState>{
            renderData(it)
        })
    }

    private fun renderData(data: MarsDataState) = with(binding){
        when (data) {
            is MarsDataState.Success -> {
                val serverResponseData = data.serverResponseData
                val url = toHTTPS(serverResponseData.img_src)
                Log.d("tag","url = "+url)
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    Picasso.get().load(url).into(marsPicture)
                    Log.d("tag","загрузил жес")
                }
            }
            is MarsDataState.Loading -> { }
            is MarsDataState.Error -> { }
        }
    }

    private fun toHTTPS(url: String): String {
        var shortUrl = url.drop(4)
        var newURL = "https$shortUrl"
        return newURL
    }


    companion object {
        fun newInstance() = MarsFragment()
    }
}