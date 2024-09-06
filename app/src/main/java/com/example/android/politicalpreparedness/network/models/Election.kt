package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import com.example.android.politicalpreparedness.database.models.ElectionTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
@JsonClass(generateAdapter = true)
data class Election(
    val id: Int,
    val name: String,
    val electionDay: Date,
    @Json(name = "ocdDivisionId")
    val division: Division
) : Parcelable

fun Election.asDatabaseElection() = ElectionTable(
    id = id,
    name = name,
    electionDay = electionDay,
    division = division
)