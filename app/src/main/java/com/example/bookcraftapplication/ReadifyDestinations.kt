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
    override val route = "Favorites"
}
object Settings : ReadifyBarDestination {
    override val icon = Icons.Rounded.Settings
    override val route = "Settings"
}
object Library : ReadifyBarDestination {
    override val icon =  Icons.Rounded.LibraryBooks
    override val route = "Library"
}
object Details : ReadifyScreen {
    override val route = "details"
}

object AboutUs : ReadifyScreen {
    override val route = "aboutUs"
}
object Account : ReadifyScreen {
    override val route = "account"
}
object Login : ReadifyScreen {
    override val route = "login"
}

object SignUp : ReadifyBarDestination {
    override val icon = Icons.Rounded.Person
    override val route = "Account"
}

val ReadifyScreens = listOf(SignUp, BookCollection, Library, Settings)
