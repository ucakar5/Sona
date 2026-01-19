package com.example.sona.pages

import SearchBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R

@Composable
fun SearchPage(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            //.padding(24.dp)

    ) {
        items(3){
            SearchElement("recent")
        }
        items(15){
            SearchElement("search")
        }
    }
}

@Composable
fun SearchElement(elementType : String) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(48.dp)
            //.background(Color.Gray)
            //.border(BorderStroke(1.dp, Color.White))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(
                if (elementType == "recent") R.drawable.aic_recentlyviewed else R.drawable.aic_search
            ),
            contentDescription = null,
            modifier = Modifier
                .size(22.dp),
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Text(
            text = "text",
            fontSize = 17.sp,
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.Medium,
            lineHeight = 15.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 20.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.aic_arrowupleft),
            contentDescription = null,
            modifier = Modifier
                .size(22.dp),
            colorFilter = ColorFilter.tint(Color.Gray),

            )
    }
}