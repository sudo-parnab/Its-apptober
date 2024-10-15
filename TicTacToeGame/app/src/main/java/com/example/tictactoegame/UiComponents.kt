package com.example.tictactoegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoegame.ui.theme.Blue500
import com.example.tictactoegame.ui.theme.Pink500

@Composable
fun RowScope.ProfileItem(
    name: String,
    avatar: Int,
    symbol: String,
    score: Int
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(200.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(
                    start = 35.dp,
                    end = 35.dp,
                    bottom = 15.dp,
                    top = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$name: $score",
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold
            )
            Box(
                modifier = Modifier
                    .size(65.dp, 45.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (symbol == MoveList.O) Blue500 else Pink500),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = if (symbol == MoveList.O) R.drawable.baseline_circle_24 else R.drawable.baseline_cross_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .background(Color.White)
                    .border(3.dp, if (symbol == MoveList.O) Blue500 else Pink500, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = avatar),
                    colorFilter = ColorFilter.tint(if (symbol == MoveList.O) Blue500 else Pink500),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun GoButton(
    image1: Int,
    image2: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),

    ) {
        Image(painter = painterResource(id = image1), contentDescription = "Player 1", modifier = Modifier.size(60.dp))
        Text(text = "VS", fontSize = 32.sp)
        Image(painter = painterResource(id = image2), contentDescription = "Player 2", modifier = Modifier.size(60.dp))

    }
}
