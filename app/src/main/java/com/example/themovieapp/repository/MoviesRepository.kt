package com.example.themovieapp.repository

import com.example.themovieapp.api.RetrofitInstance
import com.example.themovieapp.db.MovieDao
import com.example.themovieapp.models.MovieListResponse

class MoviesRepository(
    val db: MovieDao
) {
    suspend fun getMovies(pageNumber: Int) = RetrofitInstance.api.getMovies(page = pageNumber)

    suspend fun upsert(movieList: MovieListResponse) = db.upsert(movieList)

    fun getSavedMovies()= db.getAllMovies()

    suspend fun deleteAllData()=db.delete()

}