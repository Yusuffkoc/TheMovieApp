package com.example.themovieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovieapp.models.MovieListResponse

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieListResponse): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<MovieListResponse>

    @Query("DELETE FROM movies")
    suspend fun delete()

}