package com.example.sona

import TopNavBar
import TopNavBarDummy
import android.R
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetDefaults.ContainerColor
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sona.components.BottomNavBar
import com.example.sona.components.MusicPlayer
import com.example.sona.pages.HomePage
import com.example.sona.pages.LibraryPage
import com.example.sona.pages.PlaylistPage
import com.example.sona.pages.SearchPage
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {


}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = "home"){
        composable(route = "home"){
            MainAppContainer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainAppContainer(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var selectedTab by remember { mutableStateOf("Home") }

    val navPadding = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()}
    val statusPadding = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(this).toDp()}

    val bottomPadding = navPadding + 50.dp
    var sheetProgress by remember { mutableFloatStateOf(0f) }
    val navBarOffsetY = bottomPadding * sheetProgress

    //val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val backHandlerBool =
        scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
        || selectedTab != "Home"


    BackHandler(backHandlerBool) {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded){
            scope.launch {
                scaffoldState.bottomSheetState.partialExpand()
            }
        }
        else if (selectedTab != "Home"){
            selectedTab = "Home"
        }
    }

    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true )
    var sheetMode by remember { mutableStateOf("queue") }
    var isSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF121212),
        contentColor = Color.White,
        bottomBar = {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationY = navBarOffsetY.toPx()
                    }
            ) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { newTab -> selectedTab = newTab },
                    progress = sheetProgress
                )
            }
        },
        //topBar = { TopNavBarDummy() },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 50.dp, bottom = 66.dp)
        ) {
            when (selectedTab) {
                "Home" -> {HomePage()}
                "Search" -> {SearchPage()}
                "Library" -> {LibraryPage()}
                "Profile" -> {PlaylistPage()}
                "Playlist" -> {PlaylistPage()}
            }
        }
        Box(
            modifier = Modifier
                //.padding(top = paddingValues.calculateTopPadding(),)
                //.windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            MusicPlayer(
                scaffoldState = scaffoldState,
                selectedTab = selectedTab,
                onTabSelected = { newTab -> selectedTab = newTab },
                sheetMode = sheetMode,
                onSheetMode = { newMode -> sheetMode = newMode },
                statusPadding = statusPadding,
                //navPadding = navPadding,
                onSheetProgressChange = { progress ->
                    sheetProgress = progress
                }
            )

            if (isSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { isSheetVisible = false }, // REQUIRED
                    sheetState = sheetState
                ) {
                    //OptionsContent(onClose = { isSheetVisible = false })
                }
            }
        }
    }
}




@Composable
fun SettingsPage(modifier: Modifier = Modifier) {
    Column(){
        Text(text = "settings")
        Text(text = "settings")
        Text(text = "settings")
        Text(text = "settings")
        Text(text = "settings")
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {

}