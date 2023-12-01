package com.example.spotifyclone.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifyclone.Theme

@Composable
fun Settings(selectedTheme: Theme, onThemeChange: (Theme) -> Unit, modifier: Modifier = Modifier) {
    var sliderValue by remember { mutableStateOf(16f) } // Initial font size

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Text(text = "   Theme")
            selectTheme(selectedTheme, onThemeChange)
            Text(text = "   Font Size")
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                valueRange = 12f..36f, // Adjust the range based on your preference
                steps = 24 // Adjust the number of steps based on your preference
            )
            Text(
                text = "Font Size: ${sliderValue.toInt()} sp",
                fontSize = sliderValue.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun selectTheme(selectedTheme: Theme, onThemeChange: (Theme) -> Unit, modifier: Modifier = Modifier) {
    val radioOptions = listOf(Theme.Light, Theme.Dark)
    Column {
        radioOptions.forEach { theme ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (theme == selectedTheme),
                        onClick = {
                            onThemeChange(theme)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (theme == selectedTheme),
                    onClick = { onThemeChange(theme) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = theme.name, modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}