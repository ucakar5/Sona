package com.example.sona

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen2(modifier: Modifier = Modifier) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Notifications", Icons.Default.Notifications),
        NavItem("Settings", Icons.Default.Settings),
        NavItem("Cart", Icons.Default.ShoppingCart),
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            androidx.compose.material3.NavigationBar(
                modifier = Modifier
                    .padding(12.dp)
                    .height(72.dp),      // thicker like Spotify
                containerColor = Color(0xFF1A1A1A),
                tonalElevation = 12.dp // floating effect
            ) {

                navItemList.forEachIndexed { index, navItem ->
                    val selected = selectedIndex == index

                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedIndex = index },
                        icon = {
                            // place a pill behind the selected item
                            androidx.compose.material3.Surface(
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                                color = if (selected) Color(0xFF2E2E2E) else Color.Transparent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null,
                                    tint = if (selected) Color.White else Color(0xFFB3B3B3),
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            }
                        },
                        label = {
                            if (selected) {
                                Text(
                                    text = navItem.label,
                                    color = Color.White
                                )
                            }
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding))
    }
}


@Composable
fun ContentScreen2(modifier: Modifier = Modifier) {

}