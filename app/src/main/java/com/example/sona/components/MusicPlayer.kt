package com.example.sona.components

import TopNavBar
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.sona.IBMPlexSans
import com.example.sona.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayer(
    statusPadding: Dp,
    onSheetProgressChange: (Float) -> Unit = {},

) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    var sheetHeight by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    var progress by remember { mutableFloatStateOf(0f) }

    val navPadding = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()}

    LaunchedEffect(Unit) {
        snapshotFlow {
            try {
                scaffoldState.bottomSheetState.requireOffset()
            } catch (e: Exception) {
                sheetHeight
            }
        }.collect { offset ->
            Log.d("BottomSheet", "Current offset: $offset")

            if (sheetHeight > 0f) {
                val peekHeightPx = with(density) { (116.dp + navPadding).toPx() }
                val expandableHeight = sheetHeight - peekHeightPx
                progress = if (expandableHeight > 0f) {
                    (1f - (offset / expandableHeight)).coerceIn(0f, 1f)
                } else {
                    0f
                }

                onSheetProgressChange(progress)
                Log.d("BottomSheet", "Progress: $progress")
            }
        }
    }

    BottomSheetScaffold(

        topBar = { TopNavBar(statusPadding) },
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        sheetHeight = coordinates.size.height.toFloat()
                    }
                    //.padding(bottom = navPadding)
            ) {

                MiniPlayer(progress)
                FullPlayer(progress, statusPadding, navPadding)

                //Box(modifier = Modifier.height(5000.dp))
            }
        },
        sheetDragHandle = {},
        sheetShape = BottomSheetDefaults.HiddenShape,
        sheetPeekHeight = 116.dp + navPadding,
        //modifier = Modifier

            //.height(0.dp),
            //.fillMaxHeight(),

            //.windowInsetsPadding(WindowInsets.navigationBars),

        sheetContainerColor = Color(0xFF232323),
        sheetContentColor = Color.White,
    ) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPlayer(progress: Float, statusPadding: Dp, navPadding: Dp) {
    Column(
        modifier = Modifier
            .padding(top = 12.dp + statusPadding)
            .padding(horizontal = 24.dp)
            .padding(bottom = navPadding)
            .fillMaxSize()
            .graphicsLayer(
                alpha = progress
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(R.drawable.aic_pulldown),
                contentDescription = null,
                modifier = Modifier

                    .size(25.dp),
                //.weight(1f),
                alpha = 1f,
                colorFilter = ColorFilter.tint(Color.White)
            )
            Image(
                painter = painterResource(R.drawable.aic_dotmenu),
                contentDescription = null,
                modifier = Modifier

                    .size(25.dp),
                //.weight(1f),
                alpha = 1f,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }


        Column(
            modifier = Modifier
                //.padding(vertical = 30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.artcover),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(
                modifier = Modifier.height(28.dp)
            )
            Text(
                //modifier = Modifier.padding(horizontal = 8.dp),
                text = "Feel So Good",
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                color = Color.White,
            )

            Text(
                //modifier = Modifier.padding(horizontal = 8.dp),
                text = "Lucas & Steve",
                fontSize = 19.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
            )
        }
        Column(modifier = Modifier.padding(top = 14.dp))
        {
            ProgressBar(progress = 0.3f)
            PlayRow()
            //Spacer(modifier = Modifier.height(30.dp))

        }
        Row(){
            SecondRow()
        }

    }
}

@Composable
fun PlayRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        val iconList = listOf (
            R.drawable.aic_shuffle,
            R.drawable.aic_skipback,
            R.drawable.aic_play,
            R.drawable.aic_skipforward,
            R.drawable.aic_repeat
        )
        iconList.forEachIndexed { index, i ->
            if (index != 2) {
                Image(
                    painter = painterResource(i),
                    contentDescription = null,
                    modifier = Modifier
                        .size(21.dp),
                    alpha = 1f,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)

                ) {
                    Image(
                        painter = painterResource(i),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .size(28.dp),
                        //.weight(1f),
                        alpha = 1f,
                        colorFilter = ColorFilter.tint(Color(0xFF121212))
                    )
                }
            }

        }
    }
}

@Composable
fun SecondRow() {
    Row(
        modifier = Modifier
            .padding(bottom = 25.dp, top = 35.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        val iconList = listOf (
            R.drawable.aic_download,
            R.drawable.aic_share,
            R.drawable.aic_qrcode,
            R.drawable.aic_addplaylist,
            R.drawable.aic_queue
        )
        iconList.forEachIndexed { index, i ->
            Image(
                painter = painterResource(i),
                contentDescription = null,
                modifier = Modifier
                    .size(21.dp),
                alpha = 1f,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f // 0f..1f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(1.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(1.dp)
                )
        )
    }
    Row(modifier = Modifier
        .padding(vertical = 2.dp)
    ){
        Text(
            modifier = Modifier
                .weight(1f),
            text = "1:12",
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Color.Gray,
        )

        Text(
            //modifier = Modifier.padding(horizontal = 8.dp),
            text = "3:23",
            fontSize = 12.sp,
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
        )
    }
}

@Composable
fun MiniPlayer(progress: Float) {
    Row (modifier = Modifier
        .graphicsLayer(
            alpha = 1f - progress*6
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(all = 8.dp)

        ) {
            Image(
                painter = painterResource(R.drawable.artcover),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(2.dp)),
                alpha = 1f,

            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 14.dp, horizontal = 6.dp)
        ) {
            Text(
                text = "Feel So Good",
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                lineHeight = 2.sp,
            )
            Text(
                text = "Lucas & Steve",
                fontFamily = IBMPlexSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                lineHeight = 2.sp,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .size(34.dp),
                alpha = 1f,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}


/*
@Composable
fun TestFunc(modifier: Modifier = Modifier) {
    Column {
        var progress by remember {
            mutableStateOf(0f)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Slider(
            value = progress,
            onValueChange = {
                progress = it
            },
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}



@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = customProperties(id = "profile_pic")
        /*Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )*/
        Image(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier
                //.size(64.dp)
                .layoutId("profile_pic"),
            alpha = 1f,
            //colorFilter = ColorFilter.tint(Color.White),
        )
        Column(
            modifier = Modifier
                //.weight(1f)
                .layoutId("username")
                //.padding(vertical = 14.dp),

        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Take Me Away",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Andromedik",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray,
            )
        }
    }
}*/