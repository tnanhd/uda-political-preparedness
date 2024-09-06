package com.example.android.politicalpreparedness.election

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch

class VoterInfoViewModel(application: Application) : AndroidViewModel(application) {

    var showVotingUrl = View.VISIBLE
    var showBallotUrl = View.VISIBLE

    private val _voterInfo = MutableLiveData<VoterInfoResponse?>()
    val voterInfo: LiveData<VoterInfoResponse?>
        get() = _voterInfo

    private val _dataNotAvailable = MutableLiveData<Boolean?>()
    val dataNotAvailable: LiveData<Boolean?>
        get() = _dataNotAvailable

    private val _votingLocationUrl = MutableLiveData<String?>()
    val votingLocationUrl: LiveData<String?>
        get() = _votingLocationUrl

    private val _ballotInfoUrl = MutableLiveData<String?>()
    val ballotInfoUrl: LiveData<String?>
        get() = _ballotInfoUrl

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun populateVoterInfo(address: String, electionId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val infoResponse = CivicsApi.retrofitService.getVoterInfo(address, electionId)
                _voterInfo.value = infoResponse

                showVotingUrl =
                    if (infoResponse.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }

                showBallotUrl =
                    if (infoResponse.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
            } catch (e: Exception) {
                _voterInfo.value = null
                _dataNotAvailable.value = true
            }
            _isLoading.value = false
        }
    }

    fun finishFetching() {
        _dataNotAvailable.value = null
    }

    fun loadVotingLocationUrl() {
        _voterInfo.value?.let {
            _votingLocationUrl.value =
                it.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
        }
    }

    fun loadBallotInfoUrl() {
        _voterInfo.value?.let {
            _ballotInfoUrl.value =
                it.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
        }
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}