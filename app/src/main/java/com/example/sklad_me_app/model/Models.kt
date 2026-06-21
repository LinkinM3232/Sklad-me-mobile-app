package com.example.sklad_me_app.models

data class User(
    val id: String,
    val name: String
)

data class Trip(
    val id: String,
    val name: String,
    val participants: List<User>
)

data class Expense(
    val id: String,
    val description: String,
    val amount: Double,
    val paidBy: User
)

data class Debt(
    val debtor: User,     // Кто должен
    val creditor: User,   // Кому должен
    val amount: Double    // Сумма
)