package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordAndPlayUI() {
    val viewModel: MediaViewModel = koinViewModel()
    val state by viewModel.uiState
    val context = LocalContext.current

    Row {
        Button(onClick = {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context, "需要錄音權限", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.onClickRecordingOrStopButton()
            }
        }) {
            Text(if (state.isRecording) "停止錄音" else "開始錄音")
        }

        Button(onClick = viewModel::onClickPlayingButton) {
            Text("播放錄音")
        }
    }
}
