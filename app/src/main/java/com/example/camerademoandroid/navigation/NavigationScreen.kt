package com.example.camerademoandroid.navigation

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.camerademoandroid.screens.home.HomeScreen
import com.example.camerademoandroid.viewmodel.MainViewModel
import com.example.camerajetpackcomposevideo.CameraView
import java.io.File
import java.util.concurrent.Executor


@Composable
fun NavigationScreen(){
    val navController= rememberNavController();
    val viewModel= MainViewModel(LocalContext.current,navController)
    NavHost(navController = navController, startDestination = "home" ){
        composable("home") { HomeScreen(navController,viewModel) }
        composable("camera") {
            CameraView(
                navController,
                outputDirectory = viewModel.getOutputDirectory(),
                executor = viewModel.cameraExecutor,
                onImageCaptured = viewModel::handleImageCapture,
                onError = {
                    Log.e("kilo", "View error:", it)
                },
            )}
    }
}