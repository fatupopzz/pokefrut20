package com.example.pokefrut20.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.pokefrut20.R

/**
 * Simple y limpio, solo agrega tu imagen en drawable
 */

@Composable
fun FrutigerAeroBackground(
    modifier: Modifier = Modifier,
    backgroundAlpha: Float = 1.0f,
    overlayAlpha: Float = 0.3f,
    overlayColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.White,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {


        Image(
            painter = painterResource(id = R.drawable.frutiger_background),
            contentDescription = "Frutiger Aero Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(backgroundAlpha),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(overlayAlpha)
        )


        content()
    }
}