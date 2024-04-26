package com.example.camerademoandroid.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.camera.core.impl.CameraRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.camerademoandroid.db.ImageEntity
import com.example.camerademoandroid.repository.CameraDemoRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainViewModel(context: Context,navController: NavController): ViewModel(){

    val repository= CameraDemoRepository(context)
    val navController= navController
     val context = context
     lateinit var photoUri: Uri
     var cameraExecutor: ExecutorService

    var imagesList: List<ImageEntity> by mutableStateOf(listOf())
     init {
         cameraExecutor = Executors.newSingleThreadExecutor()
         getAllImages()
     }
     fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")

         viewModelScope.launch {
             repository.insertImage(uri.path.toString())
             getAllImages()
             navController.popBackStack()

         }

        photoUri = uri


    }

     fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, "aaaaaaa").apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    fun getAllImages(){
        viewModelScope.launch {
             imagesList= repository.getAllImages()
        }
    }
    override fun onCleared() {
        super.onCleared()
        cameraExecutor.shutdown()
    }
}