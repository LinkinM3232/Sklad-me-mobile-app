package com.example.sklad_me_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val SurfaceDark = Color(0xFF1A1113)
val SurfaceContainer = Color(0xFF22191B)
val PrimaryColor = Color(0xFFC27951)
val OnSurfaceVariant = Color(0xFFCCB6BB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(onBackClick: () -> Unit) {
    var tripName by remember { mutableStateOf("") }
    var tripDates by remember { mutableStateOf("") }

    Scaffold(
        containerColor = SurfaceDark,
        // Блок верхней панели навигации (TopBar)
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Новая группа",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Блок ввода названия поездки
            Text(
                "Название поездки",
                color = OnSurfaceVariant,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = tripName,
                onValueChange = { tripName = it },
                placeholder = { Text("Например: Поездка в Сочи", color = OnSurfaceVariant.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = SurfaceContainer,
                    unfocusedContainerColor = SurfaceContainer,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                leadingIcon = {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = OnSurfaceVariant)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Блок выбора дат
            Text(
                "Даты",
                color = OnSurfaceVariant,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = tripDates,
                onValueChange = { tripDates = it },
                placeholder = { Text("Выберите даты поездки", color = OnSurfaceVariant.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = SurfaceContainer,
                    unfocusedContainerColor = SurfaceContainer,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = OnSurfaceVariant)
                },
                trailingIcon = {
                    Icon(Icons.Default.Add, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.padding(end = 8.dp))
                }
            )

            Spacer(modifier = Modifier.height(32.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Добавить участников",
                    color = OnSurfaceVariant,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = SurfaceContainer
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Имя, телефон или email", color = OnSurfaceVariant.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Блок сохранения (Кнопка "Создать")
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Создать",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}