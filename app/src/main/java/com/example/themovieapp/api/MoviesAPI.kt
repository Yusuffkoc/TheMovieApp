package com.example.themovieapp.api

import com.example.themovieapp.models.MovieListResponse
import com.example.themovieapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviesAPI {
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,
        @Header("Authorization")
        key:String = API_KEY
    ): Response<MovieListResponse>
}