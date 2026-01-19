package com.example.sona.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R

@Composable
fun PlaylistPage() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item() {
            Image(
                painter = painterResource(R.drawable.artcover6),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 65.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))

            )
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                text = "After You",
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = Color.White,

            )
            Text(
                //modifier = Modifier.padding(horizontal = 8.dp),
                text = "Album · David Guetta · 2025",
                fontSize = 14.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Medium,
                lineHeight = 12.sp,
                color = Color.Gray,
            )
            Text(
                //modifier = Modifier.padding(horizontal = 8.dp),
                text = "11 songs · 54 mins",
                fontSize = 14.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Medium,
                lineHeight = 11.sp,
                color = Color.Gray,
            )
            PlaylistToolbar()
        }
        items(11) {
            PlaylistElement(
                image = R.drawable.artcover2,
                name = "Tear Me Down",
                details = "Joyner Lucas, Ava Max · 3:25"
            )
        }
    }
}

@Composable
fun PlaylistToolbar() {
    Row(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .padding(top = 18.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        val iconList = listOf (
            R.drawable.aic_download,
            R.drawable.aic_share,
            R.drawable.aic_play,
            R.drawable.aic_edit,
            R.drawable.aic_dotmenu
        )
        iconList.forEachIndexed { index, i ->
            if (index != 2) {
                Image(
                    painter = painterResource(i),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp),
                    alpha = 1f,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)

                ) {
                    Image(
                        painter = painterResource(i),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .size(25.dp),
                        //.weight(1f),
                        alpha = 1f,
                        colorFilter = ColorFilter.tint(Color(0xFF121212))
                    )
                }
            }
        }
    }
}