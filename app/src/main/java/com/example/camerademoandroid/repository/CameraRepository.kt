package com.example.camerademoandroid.repository

import android.content.Context
import com.example.camerademoandroid.db.AppDatabase
import com.example.camerademoandroid.db.ImageEntity

class CameraDemoRepository(context: Context) {
    private val imageDao = AppDatabase.getDatabase(context).imageDao()

    suspend fun insertImage(imagePath: String) {
        val imageEntity = ImageEntity(imagePath = imagePath)
        imageDao.insertImage(imageEntity)
    }

    suspend fun getAllImages(): List<ImageEntity> {
        return imageDao.getAllImages()
    }
}
