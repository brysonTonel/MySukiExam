package com.bryson.mysukiexam

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryson.mysukiexam.presentation.AuthViewModel
import com.bryson.mysukiexam.presentation.DashboardScreen
import com.bryson.mysukiexam.presentation.LoginScreen
import com.bryson.mysukiexam.presentation.RegisterScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = koinViewModel()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel, onRegisterClick = {
                navController.navigate("register")
            }, onLoginSuccess = {
                navController.navigate("dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("register") {
            RegisterScreen(viewModel) {
                navController.popBackStack()
            }
        }
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}