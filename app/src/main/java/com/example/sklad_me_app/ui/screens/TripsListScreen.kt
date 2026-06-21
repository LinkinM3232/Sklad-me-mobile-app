package com.example.sklad_me_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sklad_me_app.R
val DividerColor = Color(0xFF352B2D)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsListScreen(
    onCreateTripClick: () -> Unit,
    onTripClick: () -> Unit
) {
    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                IconButton(onClick = { /* Пока ничего, это главный экран */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = OnSurfaceVariant
                    )
                }
                Text(
                    text = "Sklad Me",
                    color = PrimaryColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Мои поездки",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Отслеживайте общие расходы легко",
                    color = OnSurfaceVariant,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onCreateTripClick, // Привязка перехода к созданию поездки
                containerColor = PrimaryColor,
                shape = CircleShape,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Add Trip",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Активная поездка
            item {
                TripCard(
                    title = "Алтай 2024",
                    dates = "12 Апр - 18 Апр",
                    amount = "₽ 45,200",
                    statusText = "Активно",
                    isActive = true,
                    onClick = onTripClick // Переход в детали поездки
                )
            }

            // Разделительная черная линия
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = DividerColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Завершенная поездка
            item {
                TripCard(
                    title = "Выходные в Питере",
                    dates = "01 Мар - 03 Мар",
                    amount = "₽ 12,500",
                    statusText = "Расчет завершен",
                    isActive = false,
                    onClick = onTripClick
                )
            }

            // Блок "Планируете поездку?"
            item {
                PlanningCard(onClick = onCreateTripClick)
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun TripCard(
    title: String,
    dates: String,
    amount: String,
    statusText: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Сделали карточку кликабельной
        shape = RoundedCornerShape(24.dp),
        color = SurfaceContainer
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(title, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(dates, color = OnSurfaceVariant, fontSize = 14.sp)
                }
                if (isActive) {
                    Surface(
                        color = PrimaryColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "🔄 $statusText",
                            color = PrimaryColor,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(if (isActive) "Общий расход" else "Вы заплатили", color = OnSurfaceVariant, fontSize = 14.sp)
            Text(amount, color = PrimaryColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)

            if (!isActive) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.check), //  иконка галочки
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(statusText, color = OnSurfaceVariant, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Имитация аватаров
                Row {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .border(2.dp, SurfaceContainer, CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.account_circle),
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                        Spacer(modifier = Modifier.width((-8).dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .border(2.dp, SurfaceContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+2", color = Color.White, fontSize = 10.sp)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Список участников", color = OnSurfaceVariant, fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = OnSurfaceVariant)
            }
        }
    }
}

@Composable
fun PlanningCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .background(SurfaceContainer.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send, // Встроенная иконка бумажного самолетика
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Планируете поездку?", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                "Создайте новую группу\nдля совместных расходов",
                color = OnSurfaceVariant,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = onClick,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, PrimaryColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Начать →", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF140C0E),
        tonalElevation = 8.dp
    ) {
        val items = listOf("Поездки", "Создать", "История", "Профиль")
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == 0,
                onClick = { },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(if (index == 0) 48.dp else 24.dp)
                            .background(
                                if (index == 0) PrimaryColor.copy(alpha = 0.2f) else Color.Transparent,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place, // Встроенная иконка пина на карте
                            contentDescription = item,
                            tint = if (index == 0) PrimaryColor else OnSurfaceVariant
                        )
                    }
                },
                label = { Text(item, color = if (index == 0) Color.White else OnSurfaceVariant) }
            )
        }
    }
}