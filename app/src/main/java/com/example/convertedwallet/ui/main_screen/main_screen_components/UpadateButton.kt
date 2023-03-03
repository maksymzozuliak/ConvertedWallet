package com.example.convertedwallet.ui.main_screen.main_screen_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertedwallet.model.BaseCurrency

@Composable
fun UpdateButton(
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
) {
    Button(
        onClick = onClick ,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 35.dp,
            pressedElevation = 0.dp,
            disabledElevation = 20.dp
        ),
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(4.dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.Sync,
            contentDescription = "Update",
            tint = Color.Black,

        )
    }
}

@Preview
@Composable
fun UpdateButtonPreview() {
    UpdateButton(
        onClick = {},
        modifier = Modifier.size(120.dp)
    )
}