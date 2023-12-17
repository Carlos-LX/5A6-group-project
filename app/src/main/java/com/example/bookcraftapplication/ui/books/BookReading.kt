package com.example.bookcraftapplication.ui.books

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.math.min

@Composable
fun BookReading() {
    val folderPath = "books/TKMFullText"  // Update with your actual path
    val pageSize = 20  // Number of pages to load at a time
    val bookReader = remember { BookReader(folderPath, pageSize) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = rememberLazyListState()
    ) {
        items(bookReader.currentPage) { pageIndex ->
            val imageBitmaps = bookReader.loadPage(pageIndex)
            imageBitmaps.forEach { imageBitmap ->
                BookPage(imageBitmap = imageBitmap)
            }
        }
    }
}

@Composable
fun BookPage(imageBitmap: ImageBitmap) {
    // Your book page UI code goes here
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
    }
}

class BookReader(private val folderPath: String, private val pageSize: Int) {
    private val imageBitmaps = mutableListOf<ImageBitmap>()
    var currentPage = 0

    fun loadPage(pageIndex: Int): List<ImageBitmap> {
        if (pageIndex == currentPage) {
            return imageBitmaps
        }

        val start = pageIndex * pageSize
        val end = min(start + pageSize, totalNumberOfPages)

        imageBitmaps.clear()
        for (i in start until end) {
            val imagePath = "$folderPath/page_$i.jpg"
            val bitmap = loadBitmapFromFilePath(imagePath)
            if (bitmap != null) {
                imageBitmaps.add(bitmap)
            }
        }

        currentPage = pageIndex
        return imageBitmaps
    }

    private fun loadBitmapFromFilePath(filePath: String): ImageBitmap? {
        return try {
            val inputStream = FileInputStream(filePath)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap.asImageBitmap()
        } catch (e: IOException) {
            Log.e("ImageLoading", "Error loading image from $filePath", e)
            null
        }
    }

    private val totalNumberOfPages: Int
        get() {
            val folder = File(folderPath)
            val files = folder.listFiles { _, name -> name.endsWith(".jpg") }
            return files?.size ?: 0
        }
}

