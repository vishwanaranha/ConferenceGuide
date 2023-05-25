package com.va.conferenceguide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.va.conferenceguide.data.models.ConferenceListResult
import com.va.conferenceguide.data.networking.ConferenceListRepository
import com.va.conferenceguide.data.networking.Resource
import com.va.conferenceguide.ui.ConferenceListViewModel
import com.va.conferenceguide.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class ConferenceListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var viewModel: ConferenceListViewModel
    private lateinit var mockedConferenceListRepository: ConferenceListRepository

    @Before
    fun setup() {
        mockedConferenceListRepository = mock(ConferenceListRepository::class.java)
        viewModel = ConferenceListViewModel(mockedConferenceListRepository)
    }

    @Test
    fun getConferenceListSuccess() = runBlocking {
        val expectedResponse = Resource.Success(
            ConferenceListResult(
                data = emptyList(), total = "0"
            )
        )

        // set mock success response
        `when`(mockedConferenceListRepository.getConferenceListResults())
            .thenReturn(
                Resource.Success(
                    ConferenceListResult(
                        data = emptyList(), total = "0"
                    )
                )
            )

        // fetch mocked data from repository
        viewModel.getConferenceList()

        // verify actual and expected response match
        assertEquals(expectedResponse, viewModel.conferenceListResult.value)
    }

    @Test
    fun getConferenceListError() = runBlocking {
        val expectedResponse = Resource.Error("Something went wrong")

        // set mock error response
        `when`(mockedConferenceListRepository.getConferenceListResults())
            .thenReturn(Resource.Error("Something went wrong"))

        // fetch mocked data from repository
        viewModel.getConferenceList()

        // verify actual and expected response match
        assertEquals(expectedResponse, viewModel.conferenceListResult.value)
    }
}