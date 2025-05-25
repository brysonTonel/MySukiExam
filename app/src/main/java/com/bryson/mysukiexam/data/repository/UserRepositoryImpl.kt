package com.bryson.mysukiexam.data.repository

import com.bryson.mysukiexam.data.local.dao.UserDao
import com.bryson.mysukiexam.data.local.entities.UserEntity
import com.bryson.mysukiexam.domain.model.LoginState
import com.bryson.mysukiexam.domain.model.RegisterState
import com.bryson.mysukiexam.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun login(username: String, password: String): LoginState {
        val user = userDao.getUser(username)
        return if (user != null && user.passwordHash == password.sha256()) {
            LoginState.Success
        } else {
            LoginState.Error("Invalid credentials")
        }
    }

    override suspend fun register(username: String, password: String): RegisterState {
        return try {
            userDao.register(UserEntity(username, password.sha256()))
            RegisterState.Success
        } catch (e: Exception) {
            RegisterState.Error(e.message ?: "An unknown error occurred")
        }
    }

    private fun String.sha256(): String {
        return MessageDigest.getInstance("SHA-256").digest(toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}