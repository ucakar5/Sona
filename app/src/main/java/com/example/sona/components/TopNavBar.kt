import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sona.IBMPlexSans
import com.example.sona.R
import com.example.sona.SettingsPage
import com.example.sona.pages.HomePage
import kotlinx.coroutines.delay

@Composable
fun TopNavBar(selectedTab: String, onTabSelected: (String) -> Unit, statusPadding: Dp) {
    val interactionSource = remember { MutableInteractionSource() }
    Column() {
        Row(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(top = statusPadding)
                //.windowInsetsPadding(WindowInsets.statusBars)
                .height(50.dp)
                .fillMaxWidth()
                //.padding(all = 10.dp)
                //.padding(top = 4.dp)

        ) {
            when (selectedTab) {
                "Home" -> {
                    Row(modifier = Modifier.padding(top = 8.dp)){
                        Image(
                            painter = painterResource(R.drawable.sona_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 14.dp, top = 2.dp, end = 6.dp)
                                .size(28.dp),
                            alpha = 1f,
                            colorFilter = ColorFilter.tint(Color.White),
                        )
                        Text(
                            text = "Sona",
                            fontSize = 23.sp,
                            fontFamily = IBMPlexSans,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                }
                "Search" -> {
                    Row(){
                        val focusRequester = remember { FocusRequester() }
                        //val keyboardController = LocalSoftwareKeyboardController.current

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                            //keyboardController?.show()
                        }

                        Image(
                            painter = painterResource(R.drawable.aic_arrowback),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 14.dp, end = 14.dp, top = 12.dp)
                                .size(24.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ){
                                    onTabSelected("Home")
                                },
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        SearchBar(modifier = Modifier.weight(1f), focusRequester = focusRequester)
                        Image(
                            painter = painterResource(R.drawable.aic_search),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 14.dp, end = 14.dp, top = 13.dp)
                                .size(22.dp),
                            colorFilter = ColorFilter.tint(Color.White),

                        )
                    }
                }
                "Library" -> {
                    Row(modifier = Modifier.padding(top = 8.dp)){
                        Text(
                            text = "Library",
                            fontSize = 23.sp,
                            fontFamily = IBMPlexSans,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(start = 14.dp)
                                .weight(1f)

                        )
                        Image(
                            painter = painterResource(R.drawable.aic_recentlyviewed),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 14.dp, top = 5.dp)
                                .size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White),
                        )
                    }

                }
                "Playlist" -> {
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        Text(
                            text = "Profile",
                            fontSize = 23.sp,
                            fontFamily = IBMPlexSans,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(start = 14.dp)
                        )
                    }
                }
                "Profile" -> {
                    Row() {
                        Image(
                            painter = painterResource(R.drawable.aic_arrowback),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 14.dp, end = 14.dp, top = 12.dp)
                                .size(24.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    onTabSelected("Library")
                                },
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopNavBarDummy() {
    Row(
        modifier = Modifier
            .background(Color(0xFF121212))
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
    ){}
}

@Composable
fun SearchBar(
    modifier : Modifier,
    focusRequester : FocusRequester
) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text,
        onValueChange = {text = it},
        singleLine = true,
        enabled = true,
        modifier = modifier
            .fillMaxHeight()
            .focusRequester(focusRequester),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.Medium
        ),
        cursorBrush = SolidColor(Color(0x55ffffff)),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .background(Color(0xff181818), RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0xff505050), RoundedCornerShape(4.dp))
                    .padding(top = 7.dp, start = 10.dp, end = 6.dp)
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = "Search songs...",
                        color = Color(0x55ffffff),
                        fontSize = 18.sp,
                        fontFamily = IBMPlexSans,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    if (!text.isEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.aic_x),
                            contentDescription = null,
                            modifier = Modifier
                                .size(22.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ){
                                    text = ""
                                },
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
        }
    )
}