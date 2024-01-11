package com.example.themovieapp.db

import androidx.room.TypeConverter
import com.example.themovieapp.models.Movie2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieListResponseConverter {
    @TypeConverter
    fun convertInvoiceListToJSONString(invoiceList: MutableList<Movie2>): String =
        Gson().toJson(invoiceList)

    @TypeConverter
    fun convertJSONStringToInvoiceList(jsonString: String): MutableList<Movie2> =
        Gson().fromJson(jsonString, object : TypeToken<MutableList<Movie2?>?>() {}.type)

}