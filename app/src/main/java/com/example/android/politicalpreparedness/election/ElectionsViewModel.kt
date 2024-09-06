package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application): AndroidViewModel(application) {
    private val _elections = MutableLiveData<List<Election>>()
    val elections: LiveData<List<Election>>
        get() = _elections

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

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun populateElections() {
        _isLoading.value = true
        viewModelScope.launch {
            val electionResponse = CivicsApi.retrofitService.getElections()
            _elections.value = electionResponse.elections
            _isLoading.value = false
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}