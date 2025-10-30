package com.features.user.data

import com.core.network.ApiInterface
import com.core.network.model.User
import com.features.user.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {
    private lateinit var apiService: ApiInterface
    private lateinit var userRepository: UserRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var dispatcherProvider: TestDispatcherProvider

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        apiService = mockk()
        dispatcherProvider = TestDispatcherProvider(testDispatcher)
        userRepository = UserRepositoryImpl(apiService, dispatcherProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers returns list of users on successful API call`() = runTest(testDispatcher) {
        val users = listOf(User(id = "1", name = "Test User"))

        coEvery { apiService.getUsers() } returns users

        val result = userRepository.getUsers()

        assertEquals(users, result)
        assertEquals(1, result.size)
        assertEquals("Test User", result.first().name)
    }

    @Test
    fun `getUsers throws exception when API call fails`() = runTest(testDispatcher) {
        val exception = RuntimeException("Network Error")

        coEvery { apiService.getUsers() } throws exception

        try {
            userRepository.getUsers()
            assert(false) { "Should have thrown an exception" }
        } catch (e: RuntimeException) {
            assertEquals("Network Error", e.message)
        }
    }
}