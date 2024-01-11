package com.example.themovieapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.themovieapp.db.GenreIdsConverter
import com.google.gson.annotations.SerializedName

@Entity
data class Movie2(
    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String? = null,
    @SerializedName("genre_ids")
    @ColumnInfo(name = "genreIds")
    @field:TypeConverters(GenreIdsConverter::class)
    var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @SerializedName("original_language")
    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    @ColumnInfo(name = "originalTitle")
    var originalTitle: String? = null,
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview: String? = null,
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = null,
    @SerializedName("video")
    @ColumnInfo(name = "video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = null
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        TODO("genreIds"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        parcel.writeValue(id)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeValue(video)
        parcel.writeValue(voteAverage)
        parcel.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie2> {
        override fun createFromParcel(parcel: Parcel): Movie2 {
            return Movie2(parcel)
        }

        override fun newArray(size: Int): Array<Movie2?> {
            return arrayOfNulls(size)
        }
    }
}
