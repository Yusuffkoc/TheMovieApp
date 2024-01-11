package com.example.themovieapp.db

import androidx.room.TypeConverter
import com.example.themovieapp.models.Movie2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsConverter {
    @TypeConverter
    fun convertListToJSONString(invoiceList: ArrayList<Int>): String =
        Gson().toJson(invoiceList)

    @TypeConverter
    fun getIntList(list: String): ArrayList<Int> {
        return Gson().fromJson(
            list.toString(),
            object : TypeToken<List<Int?>?>() {}.type
        )
    }
}