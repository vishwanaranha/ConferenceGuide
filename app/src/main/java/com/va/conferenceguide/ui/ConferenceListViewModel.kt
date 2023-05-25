package com.va.conferenceguide.ui

import androidx.lifecycle.ViewModel
import com.va.conferenceguide.data.networking.ConferenceListRepository


class ConferenceListViewModel(
    private val repository: ConferenceListRepository = ConferenceListRepository()
) : ViewModel() {

}