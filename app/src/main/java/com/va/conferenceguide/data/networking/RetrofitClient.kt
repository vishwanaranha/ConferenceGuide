package com.va.conferenceguide.data.networking

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var client: ConferenceApi? = null
    fun api(): ConferenceApi? {
        return client ?: initializeClient()
    }

    private fun initializeClient(): ConferenceApi? {
        val gson = GsonBuilder()
        val gsonConverterFactory = GsonConverterFactory.create(gson.create())
        client = Retrofit.Builder().baseUrl("https://guidebook.com/service/v2/")
            .client(HttpClient.client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ConferenceApi::class.java)
        return client
    }
}