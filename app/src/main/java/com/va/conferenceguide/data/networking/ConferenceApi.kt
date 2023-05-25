package com.va.conferenceguide.data.networking

import com.va.conferenceguide.data.models.ConferenceListResult
import retrofit2.Response
import retrofit2.http.GET

interface ConferenceApi {
    @GET("upcomingGuides/")
    suspend fun getConferenceList(): Response<ConferenceListResult>
}