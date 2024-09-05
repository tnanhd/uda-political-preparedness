package com.example.android.politicalpreparedness.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SimpleDateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @FromJson
    fun fromJson(json: String): Date? {
        return dateFormat.parse(json)
    }

    @ToJson
    fun toJson(value: Date): String {
        return dateFormat.format(value)
    }
}