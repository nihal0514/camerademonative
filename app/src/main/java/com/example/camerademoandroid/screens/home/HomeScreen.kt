package com.example.camerademoandroid.screens.home

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.camerademoandroid.db.ImageEntity
import com.example.camerademoandroid.viewmodel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController,viewModel: MainViewModel){
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(key1 = true, block = {
        cameraPermissionState.launchPermissionRequest()

    })

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate("camera")
            }) {
                Icon(Icons.Default.Add,"")
                Text("Add Image")
            }
        }
    ) {
        it
        if(viewModel.imagesList.size==0){

            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "You dont have any images",
                    textAlign = TextAlign.Center
                )
            }
        }
        else{
            LazyColumn(
                content ={
                    items(count = viewModel.imagesList.size, itemContent = {

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Image ${it +1}")
                            LoadImageFromPath(viewModel.imagesList.get(it).imagePath)
                        }
                    })

                }
            )
        }


    }
}
@Composable
fun LoadImageFromPath(imagePath: String) {
    val uri = imagePath

    Image(
        painter = rememberImagePainter(
            data  = Uri.parse(uri)  // or ht
        )
        ,
        contentDescription = "123",
        modifier = Modifier
            .width(200.dp)
            .height(80.dp),
    )
}