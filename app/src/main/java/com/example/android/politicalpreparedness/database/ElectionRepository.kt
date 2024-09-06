package com.example.android.politicalpreparedness.database

import com.example.android.politicalpreparedness.database.models.ElectionTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(private val database: ElectionDatabase) {

    suspend fun insertElection(election: ElectionTable) = withContext(Dispatchers.IO) {
        database.electionDao.insertElection(election)
    }

    suspend fun getElectionById(electionId: Int) = withContext(Dispatchers.IO) {
        database.electionDao.getElection(electionId)
    }

    suspend fun deleteElection(electionId: Int) = withContext(Dispatchers.IO) {
        database.electionDao.deleteElection(electionId)
    }

    fun getElections() = database.electionDao.getElections()
}