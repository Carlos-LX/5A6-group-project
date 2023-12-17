// ReadifyDestination.kt
package com.example.bookcraftapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a destination in the application
 */

interface ReadifyScreen {
    val route: String
}
interface ReadifyBarDestination {
    val icon: ImageVector
    val route: String
}
object BookCollection : ReadifyBarDestination {
    override val icon = Icons.Rounded.Favorite
    override val route = "bookCollection"
}
object Settings : ReadifyBarDestination {
    override val icon = Icons.Rounded.Settings
    override val route = "settings"
}
object Library : ReadifyBarDestination {
    override val icon =  Icons.Rounded.LibraryBooks
    override val route = "library"
}
object Details : ReadifyScreen {
    override val route = "details"
}

object BookReading : ReadifyScreen {
    override val route = "bookReading"
}
object Login : ReadifyScreen {
    override val route = "login"
}

object SignUp : ReadifyBarDestination {
    override val icon = Icons.Rounded.Person
    override val route = "signUp"
}

val ReadifyScreens = listOf(SignUp, BookCollection, Library, Settings)