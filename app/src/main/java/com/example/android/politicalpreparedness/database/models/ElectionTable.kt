package com.example.android.politicalpreparedness.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import java.util.Date

@Entity(tableName = "election_table")
data class ElectionTable(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") val division: Division
)

fun ElectionTable.asElection() = Election(
    id = id,
    name = name,
    electionDay = electionDay,
    division = division
)