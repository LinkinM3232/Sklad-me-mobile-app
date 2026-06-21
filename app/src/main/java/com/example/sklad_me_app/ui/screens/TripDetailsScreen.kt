package com.example.sklad_me_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    onBackClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onCalculateClick: () -> Unit
) {
    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Алтай 2024",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = OnSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                containerColor = PrimaryColor,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense",
                    tint = Color.Black
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
            ) {
                item {
                    BalanceCard()
                }

                item {
                    Text(
                        text = "Расходы",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                }

                items(sampleExpenses) { expense ->
                    ExpenseItem(expense)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(24.dp),
                color = Color.Transparent
            ) {
                Button(
                    onClick = onCalculateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        "Рассчитать долги",
                        color = PrimaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceContainer
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("ВАМ ДОЛЖНЫ", color = OnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("4 500 ₽", color = PrimaryColor, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.ArrowUpward, contentDescription = null, tint = PrimaryColor)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("ВЫ ДОЛЖНЫ", color = OnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("1 200 ₽", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = OnSurfaceVariant)
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = SurfaceContainer.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = SurfaceContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(expense.icon, contentDescription = null, tint = OnSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(expense.title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Оплатил(а) ${expense.paidBy}", color = OnSurfaceVariant, fontSize = 13.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("${expense.amount} ₽", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Ваша доля: ${expense.yourShare} ₽", color = OnSurfaceVariant, fontSize = 12.sp)
            }
        }
    }
}

data class Expense(
    val title: String,
    val paidBy: String,
    val amount: Int,
    val yourShare: Int,
    val icon: ImageVector
)

val sampleExpenses = listOf(
    Expense("Ужин в горах", "Анна", 2500, 625, Icons.Default.Restaurant),
    Expense("Трансфер из аэропорта", "Вы", 3200, 2400, Icons.Default.DirectionsCar),
    Expense("Продукты в дорогу", "Иван", 4100, 1025, Icons.Default.ShoppingCart),
    Expense("Аренда домика", "Вы", 10000, 7500, Icons.Default.Home)
)