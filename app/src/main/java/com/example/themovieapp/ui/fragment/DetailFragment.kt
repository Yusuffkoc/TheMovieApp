package com.example.themovieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.themovieapp.databinding.FragmentDetailBinding
import com.example.themovieapp.util.Constants

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initData()
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.backLayoutId.backButtonId.setOnClickListener {
            Navigation.findNavController(this.requireView()).popBackStack()
        }
    }

    private fun initData() {
        var imageKey = arguments?.getString("ImageKey")
        var title = arguments?.getString("MovieName")
        title?.let {
            binding.movieNameId.text = it
        }
        imageKey?.let {
            Glide.with(binding.root).load(Constants.BASE_IMAGE_URL + it)
                .into(binding.movieImage)
        }
    }

}