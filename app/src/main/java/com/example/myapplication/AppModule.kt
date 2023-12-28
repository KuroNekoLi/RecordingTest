package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.S)
val appModule = module {
    single<MyMediaRecorder> {
        MyMediaRecorderImpl(
            context = androidApplication()
        )
    }
    viewModel {
        MediaViewModel(get())
    }
}