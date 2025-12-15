package com.example.todo.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}