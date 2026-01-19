
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.sona.IBMPlexSans
import com.example.sona.data.repository.StatisticsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    statsViewModel: StatisticsViewModel = hiltViewModel()
) {
    val stats by statsViewModel.overallStats.collectAsState()
    val topTracks by statsViewModel.topTracks.collectAsState()
    val dailyStats by statsViewModel.dailyStats.collectAsState()

    val context = LocalContext.current
    val openSettings = remember {
        {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = openSettings, interactionSource = null, indication = null)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Text(
                text = "History",
                fontSize = 24.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Text(
                text = "Statistics",
                fontSize = 24.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
            )
        }
        HorizontalDivider(thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "TIME SPENT LISTENING", fontSize = 14.sp,
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray)
            stats?.let { stats ->
                //val minutes = formatDuration(stats.totalListenTime)
                val minutes = stats.totalListenTime/60000
                Text(text = "$minutes min"
                    , fontSize = 24.sp,
                    fontFamily = IBMPlexSans,
                    fontWeight = FontWeight.SemiBold,)
            }

        }


            Column {
                Text(text = "SONGS", fontSize = 14.sp,
                    fontFamily = IBMPlexSans,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.LightGray)
                Text(text = stats?.uniqueTracksCount.toString(), fontSize = 24.sp,
                    fontFamily = IBMPlexSans,
                    fontWeight = FontWeight.SemiBold,)
            }
        }

        HorizontalDivider(thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

        Text(text = "Top 5 songs", fontSize = 24.sp,
            fontFamily = IBMPlexSans,
            fontWeight = FontWeight.SemiBold,)


        topTracks.forEachIndexed{ index, track ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${index + 1}",
                    modifier = Modifier.width(30.dp),
                    fontSize = 18.sp,
                    fontFamily = IBMPlexSans,
                    fontWeight = FontWeight.SemiBold,
                )
                Column {
                    Text(text = track.title, fontSize = 16.sp,
                        fontFamily = IBMPlexSans,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1)
                    Text(text = "${track.artist} • ${track.playCount} plays", fontSize = 13.sp,
                        fontFamily = IBMPlexSans,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray)
                }
            }
        }

    }
}

fun formatDuration(millis: Long): String {
    val totalSeconds = (millis / 1000).toInt()
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        hours > 0 -> "%d:%02d:%02d".format(hours, minutes, seconds)
        else -> "%d:%02d".format(minutes, seconds)
    }
}