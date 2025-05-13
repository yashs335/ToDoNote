package com.example.todonote.model

import android.icu.text.CaseMap.Title
import kotlinx.serialization.Serializable

class TaskModel( val id: Int, val title: String, val task: String)