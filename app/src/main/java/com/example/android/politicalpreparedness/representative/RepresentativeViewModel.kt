package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel() {

    //TODO: Establish live data for representatives and address
    private val _address = MutableLiveData<Address?>()
    val address: LiveData<Address?>
        get() = _address

    private val _isLoading = MutableLiveData<Boolean?>(null)
    val isLoading: LiveData<Boolean?>
        get() = _isLoading

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    fun updateAddress(address: Address) {
        _address.value = address
    }

    fun updateLoadingState(state: Boolean) {
        _isLoading.value = state
    }

    fun findRepresentatives() {
        _isLoading.value = true
        viewModelScope.launch {
            _address.value?.let { address ->
                val (offices, officials) = CivicsApi.retrofitService
                    .getRepresentativesByAddress(address.toFormattedString())
                _representatives.value = offices.flatMap { office ->
                    office.getRepresentatives(officials)
                }
            }
            _isLoading.value = false
        }
    }

    fun onRepresentativeSelected(representative: Representative) {
        TODO("Not yet implemented")
    }

    //TODO: Create function to get address from individual fields

}
