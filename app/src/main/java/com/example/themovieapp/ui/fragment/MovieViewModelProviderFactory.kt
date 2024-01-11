package com.example.themovieapp.ui.fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.repository.MoviesRepository

class MovieViewModelProviderFactory(
    val app: Application,
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(app, moviesRepository) as T
    }
}
