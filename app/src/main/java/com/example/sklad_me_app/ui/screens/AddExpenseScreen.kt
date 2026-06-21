package com.example.sklad_me_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val ErrorColor = Color(0xFFCF6679)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(onBackClick: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var payer by remember { mutableStateOf("Вы") }
    var date by remember { mutableStateOf("Сегодня") }

    val isFormValid = amount.isNotEmpty() && description.isNotEmpty()

    Scaffold(
        containerColor = SurfaceDark,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Новый расход",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = OnSurfaceVariant
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Сумма",
                    color = OnSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    BasicTextField(
                        value = amount,
                        onValueChange = { if (it.all { char -> char.isDigit() }) amount = it },
                        textStyle = TextStyle(
                            color = PrimaryColor,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(IntrinsicSize.Min),
                        cursorBrush = SolidColor(PrimaryColor),
                        decorationBox = { innerTextField ->
                            if (amount.isEmpty()) {
                                Text(
                                    text = "0",
                                    color = PrimaryColor.copy(alpha = 0.3f),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            innerTextField()
                        }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "₽",
                        color = PrimaryColor,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(1.dp)
                        .background(OnSurfaceVariant.copy(alpha = 0.2f))
                        .padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Что купили?",
                color = OnSurfaceVariant,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Surface(
                color = SurfaceContainer,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Например: Продукты, Такси", color = OnSurfaceVariant.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Кто оплатил", color = OnSurfaceVariant, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Surface(
                        color = SurfaceContainer,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(payer, color = Color.White)
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = OnSurfaceVariant)
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Дата", color = OnSurfaceVariant, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Surface(
                        color = SurfaceContainer,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                            Text(date, color = Color.White)
                        }
                    }
                }
            }

            if (!isFormValid && (amount.isNotEmpty() || description.isNotEmpty())) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Заполните сумму и название расхода",
                    color = ErrorColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBackClick,
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    disabledContainerColor = SurfaceContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Сохранить",
                    color = if (isFormValid) Color.Black else OnSurfaceVariant.copy(alpha = 0.5f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}