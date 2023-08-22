package com.example.yummyapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_SCREEN_ROUTE
import com.example.yummyapp.ui.navigation.Nav.SEARCH_IMAGES_SCREEN_ROUTE

object Nav {
    val SEARCH_TEXT_PARAM = "searchText"
    val IMAGE_DETAILS_ID_PARAM = "imageId"
    val SEARCH_IMAGES_SCREEN_ROUTE = "searchImages/"
    val IMAGE_DETAILS_SCREEN_ROUTE = "imageDetails/"
}


sealed class BottomNavItem(val route: String) {
    object Search : BottomNavItem(SEARCH_IMAGES_SCREEN_ROUTE)
    object Details : BottomNavItem(IMAGE_DETAILS_SCREEN_ROUTE)
    object Saved : BottomNavItem(SEARCH_IMAGES_SCREEN_ROUTE)

}
