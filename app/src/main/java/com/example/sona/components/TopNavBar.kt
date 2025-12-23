import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopNavBar(statusPadding: Dp) {
    Row(
        modifier = Modifier
            .background(Color(0xFF121212))
            .padding(top = statusPadding)
            //.windowInsetsPadding(WindowInsets.statusBars)
            .height(50.dp)
            .fillMaxWidth()
            .padding(all = 10.dp)
            .padding(top = 4.dp)

    ){
        //Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "Sona",
            fontSize = 22.sp,
            modifier = Modifier.weight(1f)
        )

        Image(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            alpha = 1f,
            colorFilter = ColorFilter.tint(Color.White)
        )
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

