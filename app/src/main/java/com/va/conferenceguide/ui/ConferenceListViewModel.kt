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

    private var _conferenceListResult: MutableState<Resource<ConferenceListResult>> =
        mutableStateOf(Resource.Loading)
    val conferenceListResult: State<Resource<ConferenceListResult>>
        get() = _conferenceListResult

    private fun setConferenceListResponse(response: Resource<ConferenceListResult>) {
        _conferenceListResult.value = response
    }

    // APIs
    fun getConferenceList() {
        viewModelScope.launch {
            // set loading until data is fetched
            setConferenceListResponse(Resource.Loading)
            setConferenceListResponse(repository.getConferenceListResults())
        }
    }
}