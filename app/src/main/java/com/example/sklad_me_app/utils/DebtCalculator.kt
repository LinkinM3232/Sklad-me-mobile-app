package com.example.sklad_me_app.utils

import com.example.sklad_me_app.models.Debt
import com.example.sklad_me_app.models.Expense
import com.example.sklad_me_app.models.User
import kotlin.math.min

fun calculateDebts(expenses: List<Expense>, participants: List<User>): List<Debt> {
    if (participants.isEmpty() || expenses.isEmpty()) return emptyList()

    val balances = mutableMapOf<User, Double>()
    participants.forEach { balances[it] = 0.0 }

    var totalCost = 0.0
    expenses.forEach { expense ->
        totalCost += expense.amount
        balances[expense.paidBy] = (balances[expense.paidBy] ?: 0.0) + expense.amount
    }

    val fairShare = totalCost / participants.size

    val debtors = mutableListOf<Pair<User, Double>>()
    val creditors = mutableListOf<Pair<User, Double>>()

    balances.forEach { (user, paid) ->
        val balance = paid - fairShare
        if (balance < -0.01) debtors.add(user to -balance)
        else if (balance > 0.01) creditors.add(user to balance)
    }

    val resultDebts = mutableListOf<Debt>()
    var i = 0
    var j = 0

    while (i < debtors.size && j < creditors.size) {
        val debtor = debtors[i]
        val creditor = creditors[j]

        val amountToSettle = min(debtor.second, creditor.second)
        resultDebts.add(Debt(debtor.first, creditor.first, amountToSettle))

        debtors[i] = debtor.copy(second = debtor.second - amountToSettle)
        creditors[j] = creditor.copy(second = creditor.second - amountToSettle)

        if (debtors[i].second < 0.01) i++
        if (creditors[j].second < 0.01) j++
    }

    return resultDebts
}