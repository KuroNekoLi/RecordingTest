package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.IOException

class MyMediaRecorderImpl(private val context: Context) : MyMediaRecorder {
    @RequiresApi(Build.VERSION_CODES.S)
    private val myMediaRecorder = MediaRecorder(context)
    private var fileDescriptor: ParcelFileDescriptor? = null
    private val mediaPlayer = MediaPlayer()
    private var uri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun startRecording() {
        fileDescriptor = context.contentResolver.openFileDescriptor(createAudioFile(context), "w")
        myMediaRecorder.apply {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(fileDescriptor?.fileDescriptor)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("LinLi", "startRecording error! ",e )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun stopRecording() {
        myMediaRecorder.apply {
            stop()
            reset()
        }
        fileDescriptor?.close()
        fileDescriptor = null
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun startPlaying() {
        try {
            mediaPlayer.apply {
                reset()
                uri?.let {
                    setDataSource(context, it)
                    prepare()
                    start()
                }
            }
        } catch (e: IOException) {
            Log.e("LinLi", "startPlaying error! ",e )
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun cancelRecording() {
        myMediaRecorder.reset()
        fileDescriptor?.close()
        fileDescriptor = null
    }

    private fun createAudioFile(context: Context): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "錄音_${System.currentTimeMillis()}.3gp")
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
        }
        uri = context.contentResolver.insert(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )!!
        return uri as Uri
    }
}