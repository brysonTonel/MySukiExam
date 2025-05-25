package com.bryson.mysukiexam.domain.repository

import com.bryson.mysukiexam.domain.model.LoginState
import com.bryson.mysukiexam.domain.model.RegisterState

interface UserRepository {
    suspend fun register(username: String, password: String): RegisterState
    suspend fun login(username: String, password: String): LoginState
}