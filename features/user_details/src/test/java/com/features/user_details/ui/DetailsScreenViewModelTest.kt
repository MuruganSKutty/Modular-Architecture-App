package com.features.user_details.ui

import com.core.data.UserRepository
import com.core.network.model.User
import com.core.network.utils.ScreenState
import com.features.user_details.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailsScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: UserRepository
    private lateinit var viewModel: DetailsScreenViewModel
    private lateinit var dispatcherProvider: TestDispatcherProvider

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        dispatcherProvider = TestDispatcherProvider(testDispatcher)
        viewModel = DetailsScreenViewModel(repository, dispatcherProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchUserDetails is successful, uiState should be Success`() = runTest {
        val id = "1"
        val user = User(id, "John")
        coEvery { repository.getUserDetails(id) } returns user

        viewModel.fetchUserDetails(id)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ScreenState.Success)
        assertEquals(user, (state as ScreenState.Success).data)
    }

    @Test
    fun `when fetchUserDetails fails, uiState should be Error`() = runTest {
        val id = "1"
        val errorMessage = "An error occurred, please try again"
        coEvery { repository.getUserDetails(id) } throws Exception(errorMessage)

        viewModel.fetchUserDetails(id)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ScreenState.Error)
        assertEquals(errorMessage, (state as ScreenState.Error).message)
    }

    @Test
    fun `retry should call fetchUserDetails again`() = runTest {
        val id = "1"
        val errorMessage = "An error occurred, please try again"
        coEvery { repository.getUserDetails(id) } throws Exception(errorMessage)

        viewModel.fetchUserDetails(id)
        advanceUntilIdle()

        var state = viewModel.uiState.value
        assert(state is ScreenState.Error)

        val user = User("1", "Alex")
        coEvery { repository.getUserDetails("1") } returns user

        // calling fetchUserDetails again to simulate the retry click
        viewModel.fetchUserDetails(id)
        advanceUntilIdle()

        state = viewModel.uiState.value
        assert(state is ScreenState.Success)
        assertEquals(user, (state as ScreenState.Success).data)
    }
}