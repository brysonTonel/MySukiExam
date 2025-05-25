package com.bryson.mysukiexam

import com.bryson.mysukiexam.domain.model.LoginState
import com.bryson.mysukiexam.domain.model.RegisterState
import com.bryson.mysukiexam.domain.repository.UserRepository
import com.bryson.mysukiexam.presentation.AuthViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private val repository: UserRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun `login success updates loginState to Success`() = runTest {
        coEvery { repository.login("user", "pass") } returns LoginState.Success

        viewModel.login("user", "pass")
        advanceUntilIdle()

        val result = viewModel.loginState.value
        assertTrue(result is LoginState.Success)
    }

    @Test
    fun `login failure updates loginState to Error`() = runTest {
        coEvery { repository.login("user", "wrong") } returns LoginState.Error("Invalid credentials")

        viewModel.login("user", "wrong")
        advanceUntilIdle()

        val result = viewModel.loginState.value
        assertTrue(result is LoginState.Error)
        assertEquals("Invalid credentials", (result as LoginState.Error).message)
    }

    @Test
    fun `register success updates registerState to Success`() = runTest {
        coEvery { repository.register("newuser", "password") } returns RegisterState.Success

        viewModel.register("newuser", "password")
        advanceUntilIdle()

        val result = viewModel.registerState.value
        assertTrue(result is RegisterState.Success)
    }

    @Test
    fun `register failure updates registerState to Error`() = runTest {
        coEvery { repository.register("newuser", "password") } returns RegisterState.Error("Username taken")

        viewModel.register("newuser", "password")
        advanceUntilIdle()

        val result = viewModel.registerState.value
        assertTrue(result is RegisterState.Error)
        assertEquals("Username taken", (result as RegisterState.Error).message)
    }

    @Test
    fun `logout resets loginState to null`() = runTest {
        viewModel.logout()
        val result = viewModel.loginState.value
        assertNull(result)
    }
}