package com.features.user.ui

import com.core.network.model.User
import com.core.network.utils.ScreenState
import com.features.user.TestDispatcherProvider
import com.features.user.data.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserListViewModel
    private lateinit var dispatcherProvider: TestDispatcherProvider

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        dispatcherProvider = TestDispatcherProvider(testDispatcher)
        viewModel = UserListViewModel(repository, dispatcherProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchUsers called, uiState should be Loading first`() = runTest {
        coEvery { repository.getUsers() } coAnswers {
            delay(100)
            listOf(User("John"))
        }
        viewModel = UserListViewModel(repository, dispatcherProvider)

        viewModel.fetchUsers()

        assert(viewModel.uiState.value is ScreenState.Loading)

        advanceUntilIdle()
        assert(viewModel.uiState.value is ScreenState.Success)
    }

    @Test
    fun `when fetchUsers is successful, uiState should be Success`() = runTest {
        val users = listOf(User("1", "John"), User("2", "Alex"))
        coEvery { repository.getUsers() } returns users

        viewModel.fetchUsers()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ScreenState.Success)
        assertEquals(users, (state as ScreenState.Success).data)
    }

    @Test
    fun `when fetchUsers fails, uiState should be Error`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getUsers() } throws Exception(errorMessage)

        viewModel.fetchUsers()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ScreenState.Error)
        assertEquals(errorMessage, (state as ScreenState.Error).message)
    }

    @Test
    fun `retry should call fetchUsers again`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getUsers() } throws Exception(errorMessage)

        viewModel.fetchUsers()
        advanceUntilIdle()

        var state = viewModel.uiState.value
        assert(state is ScreenState.Error)

        val users = listOf(User("Alex"))
        coEvery { repository.getUsers() } returns users

        // calling fetchUsers again to simulate the retry click
        viewModel.fetchUsers()
        advanceUntilIdle()

        state = viewModel.uiState.value
        assert(state is ScreenState.Success)
        assertEquals(users, (state as ScreenState.Success).data)
    }

}