package com.example.sona.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R

@Composable
fun BottomNavItem(iconVector: Int, title:String, isSelected:Boolean, onClick:()->Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .height(6.dp)
        )
        Image(
            painter = painterResource(iconVector),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            alpha = if(isSelected)1f else 0.5f,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text (
            text=title,
            fontSize=11.sp,
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.Medium,
            color = if(isSelected) Color.White else Color.Gray,
            //fontWeight = if(isSelected) FontWeight.Medium else FontWeight.Light
        )
    }
}

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    progress: Float
) {
    val interactionSource = remember { MutableInteractionSource() }

    BottomAppBar(
        containerColor = Color(0xFF121212),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .background(Color(0xFF121212))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .height(50.dp),
    ) {
        Column() {
            Spacer(
                modifier = Modifier
                    .graphicsLayer(
                        alpha = if (progress < 0.03f) 1f else 0f
                    )
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val pageNames = listOf("Home", "Search", "Library", "Profile")
                val imageList = listOf(
                    /*Icons.Default.Home,
                    Icons.Default.Search,
                    Icons.Default.Menu,
                    Icons.Default.Settings,*/
                    R.drawable.aic_home,
                    R.drawable.aic_search,
                    R.drawable.aic_library,
                    R.drawable.aic_profile,

                )
                pageNames.forEachIndexed { index, name ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onTabSelected(name)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        BottomNavItem(
                            iconVector = imageList[index],
                            title = name,
                            isSelected = selectedTab == name,
                            onClick = {
                                onTabSelected(name)
                            }
                        )
                    }
                }
            }
        }
    }
}