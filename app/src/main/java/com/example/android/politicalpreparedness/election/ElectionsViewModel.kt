package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionRepository
import com.example.android.politicalpreparedness.database.models.ElectionTable
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(application: Application): AndroidViewModel(application) {
    private val electionRepository = ElectionRepository(ElectionDatabase.getInstance(application))
    val savedElections: LiveData<List<ElectionTable>> = electionRepository.getElections()

    private val _fetchedElections = MutableLiveData<List<Election>>()
    val fetchedElections: LiveData<List<Election>>
        get() = _fetchedElections

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _navigateToVoterInfo = MutableLiveData<Election?>()
    val navigateToVoterInfo: LiveData<Election?>
        get() = _navigateToVoterInfo

    fun displayElectionDetail(it: Election) {
        _navigateToVoterInfo.value = it
    }

    fun displayElectionDetailCompleted() {
        _navigateToVoterInfo.value = null
    }

    init {
        populateElections()
    }

    fun populateElections() {
        _isLoading.value = true
        viewModelScope.launch {
            val electionResponse = CivicsApi.retrofitService.getElections()
            _fetchedElections.value = electionResponse.elections
            _isLoading.value = false
        }
    }
}