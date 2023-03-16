package com.example.convertedwallet.ui.main_screen.main_screen_components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertedwallet.model.BaseCurrency
import com.example.convertedwallet.ui.main_screen.MainScreenViewModel

@Composable
fun UpdateButton(
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
    isLoading : Boolean,
    buttonColor : Color = MaterialTheme.colors.primaryVariant
) {

    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = currentRotation - 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2400, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                // Slow down rotation on pause
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        durationMillis = 1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }

    Button(
        onClick = onClick ,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 35.dp,
            pressedElevation = 0.dp,
            disabledElevation = 20.dp
        ),
        colors = ButtonDefaults.buttonColors(buttonColor),
        contentPadding = PaddingValues(4.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize().rotate(rotation.value),
            imageVector = Icons.Default.Sync,
            contentDescription = "Update",
            tint = Color.Black,

        )
    }

}



