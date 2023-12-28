package com.example.myapplication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.theme.UiState

class MediaViewModel(private val myMediaRecorder: MyMediaRecorder) : ViewModel() {
    private val _uiState = mutableStateOf(
        UiState(
            recordList = emptyList(),
            isRecording = false
        )
    )
    val uiState: State<UiState> = _uiState
    fun onClickRecordingOrStopButton() {
        val recording = _uiState.value.isRecording
        when (recording) {
            true -> myMediaRecorder.stopRecording()
            false -> myMediaRecorder.startRecording()
        }
        _uiState.value = _uiState.value.copy(
            isRecording = !recording
        )
    }

    fun onClickPlayingButton() {
        myMediaRecorder.startPlaying()
    }
}