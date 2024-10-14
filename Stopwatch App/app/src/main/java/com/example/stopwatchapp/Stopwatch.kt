package com.example.stopwatchapp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatchapp.service.ServiceHelper
import com.example.stopwatchapp.service.StopwatchService
import com.example.stopwatchapp.service.StopwatchState
import com.example.stopwatchapp.service.TimeStamp
import com.example.stopwatchapp.ui.theme.Dark
import com.example.stopwatchapp.ui.theme.ExtraDark
import com.example.stopwatchapp.ui.theme.Light
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_CANCEL
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_START
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_STOP
import com.example.stopwatchapp.util.Constants.ACTION_SERVICE_TIMESTAMP
import com.example.stopwatchapp.util.formatTime
import com.example.stopwatchapp.util.pad

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Stopwatch(stopwatchService: StopwatchService, timeStamps: List<TimeStamp>) {
    val context = LocalContext.current
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds
    val milliseconds by stopwatchService.milliseconds
    val currentState by stopwatchService.currentState
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = timeStamps) {
        if (timeStamps.isNotEmpty()) {
            scrollState.animateScrollToItem((timeStamps.lastIndex))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ExtraDark)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(3.5f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedContent(
                    targetState = minutes,
                    transitionSpec = { addAnimation() },
                    label = minutes
                ) {
                    Text(
                        text = minutes,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (minutes == "00") Color.White else Light
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = ":", color = Color.White, fontSize = 64.sp)
                Spacer(modifier = Modifier.width(8.dp))
                AnimatedContent(
                    targetState = seconds,
                    transitionSpec = { addAnimation() },
                    label = seconds
                ) {
                    Text(
                        text = seconds,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (seconds == "00") Color.White else Light

                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier.weight(1.5f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = milliseconds,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Normal,
                    color = Light

                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(3f)
                .padding(20.dp),
            state = scrollState,
            reverseLayout = true,
            userScrollEnabled = true
        ) {
            itemsIndexed(timeStamps) { index, item ->
                Text(text = "")
                TimeStampItem(
                    index = index + 1,
                    timeStamp = item.timeStamp.toComponents { _, minutes, seconds, milliseconds ->
                        formatTime(
                            minutes.pad(),
                            seconds.pad(),
                            milliseconds.pad().take(2)
                        )
                    }
                )
            }
        }
        Row(modifier = Modifier.weight(1f)) {
            if (currentState == StopwatchState.Idle) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    onClick = {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = ACTION_SERVICE_START
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Light,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                        contentDescription = "Start"
                    )
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.8f),
                    onClick = {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = if (currentState == StopwatchState.Started) ACTION_SERVICE_STOP else ACTION_SERVICE_START
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentState == StopwatchState.Started) Dark else Light,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(if (currentState == StopwatchState.Started) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24),
                        contentDescription = "Resume/Stop"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.8f),
                    onClick = {
                        if (currentState == StopwatchState.Started) {
                            ServiceHelper.triggerForegroundService(
                                context = context, action = ACTION_SERVICE_TIMESTAMP
                            )
                        } else {
                            ServiceHelper.triggerForegroundService(
                                context = context, action = ACTION_SERVICE_CANCEL
                            )
                        }
                    },
                    enabled = milliseconds != "0" || milliseconds != "00",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Dark,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(if (currentState == StopwatchState.Started) R.drawable.baseline_flag_24 else R.drawable.baseline_square_24),
                        contentDescription = "Cancel/Mark"
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 100): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = 3 * duration)) { height -> height } togetherWith slideOutVertically(
        animationSpec = tween(durationMillis = 7 * duration)
    ) { height -> height }
}

@Composable
private fun TimeStampItem(
    index: Int,
    timeStamp: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "+$index",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.weight(0.4f),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = timeStamp,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.Start
        )
    }
}