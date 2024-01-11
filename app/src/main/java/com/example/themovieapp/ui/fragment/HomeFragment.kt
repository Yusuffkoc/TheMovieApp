package com.example.themovieapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.MovieApplication

import com.example.themovieapp.adapter.MoviesAdapter
import com.example.themovieapp.databinding.FragmentHomeBinding
import com.example.themovieapp.util.Constants
import com.example.themovieapp.util.Resource


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    val TAG = "MovieFragment"

    private lateinit var binding: FragmentHomeBinding
    lateinit var moviesAdapter: MoviesAdapter

    private val viewModel: HomeViewModel by viewModels {
        MovieViewModelProviderFactory(
            requireActivity().application,
            (requireActivity().application as MovieApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.addButton.setOnClickListener {
            Toast.makeText(requireContext(), "You clicked the custom button...", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initUI() {
        binding.pageNameId.backButtonId.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMovies()
        setupRecyclerView()
        viewModel.moviesResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { moviesResponse ->
                        moviesAdapter.differ.submitList(moviesResponse.movies.toList())
                        val totalPages = moviesResponse.totalPages!! / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.moviesPage == totalPages
                        if (isLastPage) {
                            //binding.movieRV.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "An Error Occured: $message",
                            Toast.LENGTH_SHORT
                        )
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                else -> {}
            }
        })



        moviesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("ImageKey", it.posterPath)
                putString("MovieName", it.title)
            }

            Navigation.findNavController(this.requireView())
                .navigate(
                    com.example.themovieapp.R.id.action_homeFragment_to_detailFragment,
                    bundle
                )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedMovies().observe(viewLifecycleOwner, Observer { moviesResponse ->
            if (!viewModel.hasInternetConnection() && moviesResponse != null && !moviesResponse.movies.isNullOrEmpty()) {
                hideProgressBar()
                moviesAdapter.differ.submitList(moviesResponse.movies.toList())
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.itemCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getMovies()
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.movieRV.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HomeFragment.scrollListener)
        }
    }

}