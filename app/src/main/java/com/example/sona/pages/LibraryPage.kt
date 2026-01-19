package com.example.sona.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R

@Composable
fun LibraryPage(modifier: Modifier = Modifier) {
    var gridView by remember { mutableStateOf(false) }
    var sortAZ by remember { mutableStateOf(false) }
    var libraryMode by remember { mutableStateOf("library") }

    Column(modifier = Modifier
        .fillMaxSize()

    ) {
        LibraryToolbar(
            gridView = gridView,
            onGridView = {gridView = !gridView},
            sortAZ = sortAZ,
            onSortMode = {sortAZ = !sortAZ},
            libraryMode = libraryMode,
            onChangeMode = {libraryMode = it},
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
            //.padding(24.dp)

        ) {
            item() {
                PlaylistElement(
                    image = R.drawable.artcover8,
                    name = "Title",
                    details = "Album · 12 songs"
                )
            }
            items(4) {
                PlaylistElement(
                    image = R.drawable.albumcover,
                    name = "Title",
                    details = "Playlist · 20 songs"
                )
            }
            item() {
                PlaylistElement(
                    image = R.drawable.artcover3,
                    name = "Title",
                    details = "Album · 4 songs"
                )
            }
            items(8) {
                PlaylistElement(
                    image = R.drawable.albumcover,
                    name = "Hehe",
                    details = "Playlist · 15 songs"
                )
            }
            items(8) {
                PlaylistElement(
                    image = R.drawable.artcover3,
                    name = "Title",
                    details = "Album · 4 songs"
                )
            }
        }
    }
}

@Composable
fun PlaylistElement(
    image : Int,
    name: String,
    details: String
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(2.dp)),
                alpha = 1f,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.dp, horizontal = 6.dp)
            ) {
                Text(
                    text = name,
                    fontFamily = IBMPlexSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 2.sp,

                )
                Text(
                    text = details,
                    fontFamily = IBMPlexSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    lineHeight = 2.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.aic_dotmenu),
            contentDescription = null,
            modifier = Modifier
                .padding(all = 12.dp)
                .size(20.dp),
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun LibraryToolbar(
    gridView: Boolean,
    onGridView: () -> Unit,
    sortAZ: Boolean,
    onSortMode: () -> Unit,
    libraryMode: String,
    onChangeMode: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()

            //.background(Color.Gray)
            .padding(horizontal = 14.dp)
            .padding(bottom = 10.dp),
            //.padding(bottom = 6.dp),
        //horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Row(
            modifier = Modifier
                //.padding(bottom = 10.dp)
                .background(Color(0xff121212), RoundedCornerShape(4.dp))
                //.border(1.dp, Color(0xff454545), RoundedCornerShape(4.dp))
                //.padding(vertical = 8.dp, horizontal = 7.dp)
        ){*/
        Image(
            painter = painterResource(R.drawable.aic_library),
            contentDescription = null,
            modifier = Modifier
                .then(
                    if (libraryMode == "library"){
                        Modifier
                        .background(Color(0xff212121), RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xff404040), RoundedCornerShape(4.dp))
                    }
                    else {
                        Modifier
                    }
                )
                .padding(8.dp)
                .size(23.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onChangeMode("library")
                },
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color(0xffffffff))
        )
        Spacer(Modifier.width(6.dp))
        Image(
            painter = painterResource(R.drawable.aic_download),
            contentDescription = null,
            modifier = Modifier
                .then(
                    if (libraryMode == "downloads"){
                        Modifier
                            .background(Color(0xff212121), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xff404040), RoundedCornerShape(4.dp))
                    }
                    else {
                        Modifier
                    }
                )
                .padding(8.dp)
                .size(23.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onChangeMode("downloads")
                },
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(Modifier.width(6.dp))
        Image(
            painter = painterResource(R.drawable.aic_audiofile),
            contentDescription = null,
            modifier = Modifier
                .then(
                    if (libraryMode == "files"){
                        Modifier
                            .background(Color(0xff212121), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xff404040), RoundedCornerShape(4.dp))
                    }
                    else {
                        Modifier
                    }
                )
                .padding(8.dp)
                .size(23.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onChangeMode("files")
                },
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
        //}

        Spacer(Modifier.width(6.dp).weight(1f))
        Image(
            painter = painterResource(
                if (sortAZ) R.drawable.aic_sortrecent else R.drawable.aic_sortaz
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(23.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onSortMode()
                },
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(Modifier.width(6.dp))
        Image(
            painter = painterResource(
                if (gridView) R.drawable.aic_list else R.drawable.aic_grid
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
                .size(23.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onGridView()
                },
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}