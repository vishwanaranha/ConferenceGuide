package com.va.conferenceguide.data.networking

abstract class BaseRepository {
    val genericError = "Something Went Wrong"
    val noInternetError = "Check your Internet Connection"

    fun api(): ConferenceApi? {
        return RetrofitClient.api()
    }
}