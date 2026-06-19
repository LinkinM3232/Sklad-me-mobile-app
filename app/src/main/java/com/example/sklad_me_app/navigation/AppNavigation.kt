package com.example.sklad_me_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sklad_me_app.ui.screens.LoginScreen
import com.example.sklad_me_app.ui.screens.TripsListScreen
import com.example.sklad_me_app.ui.screens.CreateTripScreen
import com.example.sklad_me_app.ui.screens.TripDetailsScreen
import com.example.sklad_me_app.ui.screens.AddExpenseScreen
import com.example.sklad_me_app.ui.screens.DebtsResultScreen

// 1. Описывание всех экранов приложенияя
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object TripsList : Screen("trips_list")
    object CreateTrip : Screen("create_trip")
    object TripDetails : Screen("trip_details")
    object AddExpense : Screen("add_expense")
    object DebtsResult : Screen("debts_result")
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route // Приложение начнется с экрана Входа
    ) {
        // Экран 1-й Вход
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.TripsList.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            })
        }

        // Экран 2-й Список поездок
        composable(Screen.TripsList.route) {
            TripsListScreen(
                onCreateTripClick = { navController.navigate(Screen.CreateTrip.route) },
                onTripClick = { navController.navigate(Screen.TripDetails.route) }
            )
        }

        // Экран 3-й Создание поездки
        composable(Screen.CreateTrip.route) {
            CreateTripScreen(onBackClick = { navController.popBackStack() })
        }

        // Экран 4-й Детали поездки (чеки)
        composable(Screen.TripDetails.route) {
            TripDetailsScreen(
                onBackClick = { navController.popBackStack() },
                onAddExpenseClick = { navController.navigate(Screen.AddExpense.route) },
                onCalculateClick = { navController.navigate(Screen.DebtsResult.route) }
            )
        }

        // Экран 5-й Добавление расхода/чека
        composable(Screen.AddExpense.route) {
            AddExpenseScreen(onBackClick = { navController.popBackStack() })
        }

        // Экран 6-й Результаты расчета долгов
        composable(Screen.DebtsResult.route) {
            DebtsResultScreen(onBackClick = { navController.popBackStack() })
        }
    }
}