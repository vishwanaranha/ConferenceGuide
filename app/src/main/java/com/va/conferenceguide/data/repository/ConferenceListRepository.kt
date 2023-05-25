package com.va.conferenceguide.data.networking

import com.va.conferenceguide.data.models.ConferenceListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class ConferenceListRepository : BaseRepository() {

    suspend fun getConferenceListResults(): Resource<ConferenceListResult> =
        withContext(Dispatchers.IO) {
            try {
                val response = api()?.getConferenceList()
                response?.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(
                    response?.errorBody()?.string() ?: genericError
                )
            } catch (exception: IOException) {
                Resource.Error(noInternetError)
            } catch (exception: Exception) {
                Resource.Error(genericError)
            }
        }

}