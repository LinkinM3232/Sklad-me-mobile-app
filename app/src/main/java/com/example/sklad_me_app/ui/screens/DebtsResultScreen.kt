package com.example.sklad_me_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsResultScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = Color(0xFF1A1113),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sklad Me",
                        color = Color(0xFFF7E9E8),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color(0xFFF7E9E8)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Итоги: Алтай 2024",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Оптимизированный расчет. Минимум переводов.",
                    color = Color(0xFFCCB6BB),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "ВАМ ВЕРНУТ",
                        amount = "1 200 ₽",
                        isIncome = true
                    )
                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "ВЫ ОТДАЕТЕ",
                        amount = "450 ₽",
                        isIncome = false
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
            }

            item {
                Text(
                    text = "Кто кому должен",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            items(sampleDebts) { debt ->
                DebtDetailCard(debt)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    isIncome: Boolean
) {
    Surface(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isIncome) Color(0xFFC27951) else Color(0xFF22191B)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = if (isIncome) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = if (isIncome) Color.Black.copy(alpha = 0.7f) else Color(0xFFCCB6BB)
            )
            Column {
                Text(
                    text = title,
                    color = if (isIncome) Color.Black.copy(alpha = 0.6f) else Color(0xFFCCB6BB),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = amount,
                    color = if (isIncome) Color.Black else Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DebtDetailCard(debt: DebtInfo) {
    val isReceiving = debt.amount > 0

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF22191B)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2C2224)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = debt.userName.take(1),
                            color = Color(0xFFCCB6BB),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = debt.userName,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isReceiving) "должен вам" else "вы должны",
                        color = Color(0xFFCCB6BB),
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = "${kotlin.math.abs(debt.amount)} ₽",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isReceiving) Color(0xFF2C2224) else Color(0xFFC27951).copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                border = if (!isReceiving) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC27951).copy(alpha = 0.5f)) else null
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!isReceiving) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = Color(0xFFC27951),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = if (isReceiving) "Напомнить человеку" else "Рассчитаться",
                        color = if (isReceiving) Color.White else Color(0xFFC27951),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class DebtInfo(
    val userName: String,
    val amount: Int
)

val sampleDebts = listOf(
    DebtInfo("Иван", 1200),
    DebtInfo("Анна", -450)
)