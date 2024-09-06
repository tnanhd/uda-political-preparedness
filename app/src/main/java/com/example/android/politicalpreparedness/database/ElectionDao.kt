package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.database.models.DatabaseTable

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: DatabaseTable)

    @Query("SELECT * FROM election_table ORDER BY electionDay DESC")
    suspend fun getElections(): List<DatabaseTable>

    @Query("SELECT * FROM election_table WHERE id = :electionId ORDER BY electionDay DESC")
    suspend fun getElection(electionId: Int): DatabaseTable?

    @Query("DELETE FROM election_table")
    suspend fun clearElections()

    @Query("DELETE FROM election_table WHERE id = :electionId")
    suspend fun deleteElection(electionId: Int)

}