package com.example.themovieapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.themovieapp.db.MovieListResponseConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
class MovieListResponse(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    @field:TypeConverters(MovieListResponseConverter::class)
    var movies: MutableList<Movie2> = arrayListOf(),
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("movies"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(page)
        parcel.writeValue(totalPages)
        parcel.writeValue(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieListResponse> {
        override fun createFromParcel(parcel: Parcel): MovieListResponse {
            return MovieListResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieListResponse?> {
            return arrayOfNulls(size)
        }
    }
}
