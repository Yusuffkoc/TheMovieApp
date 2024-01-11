package com.example.themovieapp.ui.fragment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.MovieApplication
import com.example.themovieapp.models.MovieListResponse
import com.example.themovieapp.repository.MoviesRepository
import com.example.themovieapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    app: Application,
    val repository: MoviesRepository
) : AndroidViewModel(app) {

    val moviesResponse: MutableLiveData<Resource<MovieListResponse>> = MutableLiveData()
    var moviesPage = 1
    var moviesListResponse: MovieListResponse? = null

    fun getMovies() = viewModelScope.launch {
        safeGetMoviesCall()
    }

    private suspend fun safeGetMoviesCall() {
        try {
            if (hasInternetConnection()) {
                val response = repository.getMovies(pageNumber = moviesPage)
                moviesResponse.postValue(handleMoviesResponse(response))
            } else {
                moviesResponse.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> moviesResponse.postValue(Resource.Error("Network Failure"))
                else -> moviesResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun saveMovies(movie: MovieListResponse) = viewModelScope.launch {
        repository.upsert(movie)
    }

    fun getSavedMovies() = repository.getSavedMovies()

    private fun handleMoviesResponse(response: Response<MovieListResponse>): Resource<MovieListResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                moviesPage++
                if (moviesListResponse == null) {
                    moviesListResponse = resultResponse
                } else {
                    val oldMovies = moviesListResponse?.movies
                    val newsMovies = resultResponse.movies
                    oldMovies?.addAll(newsMovies)
                }
                saveMovies(moviesListResponse!!)
                return Resource.Success(moviesListResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MovieApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}