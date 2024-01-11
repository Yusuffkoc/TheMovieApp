package com.example.themovieapp

import android.app.Application
import com.example.themovieapp.db.MovieDatabase
import com.example.themovieapp.repository.MoviesRepository

class MovieApplication : Application() {

    private val database by lazy { MovieDatabase.getDatabase(this) }
    val repository by lazy { MoviesRepository(database.getMovieDao()) }
}