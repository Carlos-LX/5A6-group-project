package com.example.spotifyclone

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Store
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a destination in the application
 */


interface ReadifyDestination {
    val icon: ImageVector
    val route: String
}
object BookCollection : ReadifyDestination {
    override val icon = Icons.Rounded.Book
    override val route = "bookCollection"
}
object Settings : ReadifyDestination {
    override val icon = Icons.Rounded.Settings
    override val route = "settings"
}
object Library : ReadifyDestination {
    override val icon =  Icons.Rounded.Store
    override val route = "library"
}
val ReadifyScreens = listOf(Settings, BookCollection, Library)