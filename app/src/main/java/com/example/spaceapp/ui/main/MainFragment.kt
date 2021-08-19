package com.example.spaceapp.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spaceapp.MainActivity
import com.example.spaceapp.R
import com.example.spaceapp.databinding.MainFragmentBinding
import com.example.spaceapp.ui.main.EMWActivity.EMWActivity
import com.example.spaceapp.ui.main.TODOActivity.TODOActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {
    private var isExpanded = false

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)

        inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            })
        }

        imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                cont, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = imageView.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            imageView.layoutParams = params
            imageView.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer <PictureOfTheDayData>{ renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) = with(binding) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val explanation = serverResponseData.title
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    val fade = Fade()
                    fade.duration = 1500
                    TransitionManager.beginDelayedTransition(cont,fade)
                    Picasso.get().load(url).into(imageView)
                    imageView.visibility = View.VISIBLE
                    //Log.d("tag","кайфарик")
                }
            }
            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {


            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_item ->{
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.container,SettingsFragment.newInstance())
                    ?.addToBackStack("")
                    ?.commit()
            }
            R.id.emw_item ->{
                activity?.let { startActivity(Intent(it,EMWActivity::class.java)) }
            }
            R.id.todo_item ->{
                activity?.let { startActivity(Intent(it, TODOActivity::class.java)) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }


    companion object {
        fun newInstance() = MainFragment()
    }

}