package com.va.conferenceguide.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.va.conferenceguide.data.models.ConferenceListResult
import com.va.conferenceguide.data.networking.ConferenceListRepository
import com.va.conferenceguide.data.networking.Resource
import kotlinx.coroutines.launch

class ConferenceListViewModel(
    private val repository: ConferenceListRepository = ConferenceListRepository()
) : ViewModel() {

    private var _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean>
        get() = _isRefreshing

    private var _conferenceListResult: MutableState<Resource<ConferenceListResult>> =
        mutableStateOf(Resource.Empty)
    val conferenceListResult: State<Resource<ConferenceListResult>>
        get() = _conferenceListResult

    private fun setConferenceListResponse(response: Resource<ConferenceListResult>) {
        _conferenceListResult.value = response
    }

    private fun setRefreshing(refreshing: Boolean) {
        _isRefreshing.value = refreshing
    }

    fun loadData(isRefresh: Boolean) {
        // check if already a network request is in progress
        if (conferenceListResult.value != Resource.Loading) {
            if (isRefresh) {
                setRefreshing(true)
            }
            getConferenceList()
        }
    }

    // APIs
    private fun getConferenceList() {
        viewModelScope.launch {
            // set loading until data is fetched
            setConferenceListResponse(Resource.Loading)
            setConferenceListResponse(repository.getConferenceListResults())
            setRefreshing(false)
        }
    }

}