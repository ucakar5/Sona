package com.example.sona.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val imgList = listOf (
        R.drawable.artcover,
        R.drawable.artcover2,
        R.drawable.artcover3,
        R.drawable.artcover4,
        R.drawable.artcover5,
        R.drawable.artcover6,
        R.drawable.artcover7,
        R.drawable.artcover8,
        R.drawable.artcover9,
    )
    val iconList = listOf (
        R.drawable.aic_favorite,
        R.drawable.aic_playlist,
        R.drawable.aic_shuffle,
        R.drawable.aic_last,
    )
    val iconNames = listOf (
        "Liked",
        "Playlists",
        "Shuffle",
        "Recent"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        content = {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(6.dp))
            }

            itemsIndexed(items = iconList,
                span = { _, _ -> GridItemSpan(3)}) { index, imageRes ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xff505050), RoundedCornerShape(4.dp))
                        .aspectRatio(3f)
                        .background(Color(0xff181818)),
                        //.padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 6.dp),
                        colorFilter = ColorFilter.tint(Color(0xffffffff)),
                    )
                    Text(
                        text = iconNames[index],
                        fontSize = 19.sp,
                        fontFamily = IBMPlexSans,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xffffffff)
                        //modifier = Modifier.weight(1f)
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(30.dp))
            }

            items(items = imgList,
                span = {GridItemSpan(2)}) { imageRes ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0x20ffffff), RoundedCornerShape(4.dp))
                        .aspectRatio(1f)
                        .background(Color.Black)
                ){
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    0.5f to Color.Transparent,
                                    0.85f to Color(0xaa000000)
                                )
                            )
                    )
                    Text(
                        text = "title",
                        fontSize = 13.sp,
                        fontFamily = IBMPlexSans,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xeeffffff),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 6.dp)
                    )
                }
            }
        }
    )
}