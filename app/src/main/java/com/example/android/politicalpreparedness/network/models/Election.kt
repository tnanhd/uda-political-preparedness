package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Election(
    val id: Int,
    val name: String,
    val electionDay: Date,
    @Json(name = "ocdDivisionId")
    val division: Division
)