package com.example.themovieapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themovieapp.models.MovieListResponse

@Database(entities = [MovieListResponse::class], version = 1, exportSchema = true)
@TypeConverters(MovieListResponseConverter::class, GenreIdsConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}