package com.example.todonote.model

import android.icu.text.CaseMap.Title
import kotlinx.serialization.Serializable
import java.sql.Timestamp

class TaskModel(
    val id: Int,
    val userEmail : String,
    val title: String,
    val task: String,
    val createdAt : String,
    val updatedAt : String
)



