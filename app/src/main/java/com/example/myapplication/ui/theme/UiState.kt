package com.example.myapplication.ui.theme

import android.net.Uri

data class UiState(
    val recordList: List<Uri>,
    val isRecording: Boolean
)