package com.example.spaceapp.ui.main.EMWActivity.WeatherFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spaceapp.R
import com.example.spaceapp.databinding.WeatherFragmentBinding
import com.example.spaceapp.ui.main.EMWActivity.MarsFragment.MarsDataState
import com.example.spaceapp.ui.main.EMWActivity.MarsFragment.MarsViewModel
import com.squareup.picasso.Picasso

class WeatherFragment : Fragment() {

    private lateinit var binding:WeatherFragmentBinding
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer <WeatherDataState>{
            renderData(it)
        })
    }

    private fun renderData(data: WeatherDataState) = with(binding) {
        when (data) {
            is WeatherDataState.Success -> {
                val serverResponseData = data.serverResponseData
                weatherNote.text = serverResponseData.note
            }
            is WeatherDataState.Loading -> { }
            is WeatherDataState.Error -> { }
        }
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }

}