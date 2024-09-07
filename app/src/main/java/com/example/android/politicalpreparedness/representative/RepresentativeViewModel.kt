package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val SAVED_DATA_KEY = "saved_data"
    }

    private val _address = MutableLiveData<Address?>()
    val address: LiveData<Address?>
        get() = _address

    private val _showToast = MutableLiveData<String?>()
    val showToast: LiveData<String?>
        get() = _showToast

    val addressLine1 = MutableLiveData<String?>()
    val addressLine2 = MutableLiveData<String?>()
    val city = MutableLiveData<String?>()
    val state = MutableLiveData<String?>()
    val zip = MutableLiveData<String?>()

    private val _isLoading = MutableLiveData<Boolean?>(null)
    val isLoading: LiveData<Boolean?>
        get() = _isLoading

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    init {
        _representatives.value = savedStateHandle.get(SAVED_DATA_KEY) ?: emptyList()
    }

    fun updateAddress(address: Address) {
        _address.value = address
        addressLine1.value = address.line1
        addressLine2.value = address.line2
        city.value = address.city
        state.value = address.state
        zip.value = address.zip
    }

    fun updateLoadingState(state: Boolean) {
        _isLoading.value = state
    }

    fun showToastCompleted() {
        _showToast.value = null
    }

    fun findRepresentatives() {
        _isLoading.value = true
        viewModelScope.launch {
            _address.value?.let { address ->
                var representativeList: List<Representative> = emptyList()
                try {
                    val (offices, officials) = CivicsApi.retrofitService
                        .getRepresentativesByAddress(address.toFormattedString())
                    representativeList =
                        offices.flatMap { office -> office.getRepresentatives(officials) }
                } catch (e: Exception) {
                    _showToast.value = "Representatives not found"
                }
                _representatives.value = representativeList
                savedStateHandle.set(SAVED_DATA_KEY, representativeList)
            }
            _isLoading.value = false
        }
    }

    fun onRepresentativeSelected(representative: Representative) {
        TODO("Not yet implemented")
    }
}
