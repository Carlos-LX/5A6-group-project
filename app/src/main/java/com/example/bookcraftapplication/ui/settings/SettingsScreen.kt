package com.example.bookcraftapplication.ui.settings

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcraftapplication.Theme

@Composable
fun Settings(selectedTheme: Theme, onThemeChange: (Theme) -> Unit, userfontSize: Float, onFontChange: (Float) -> Unit, modifier: Modifier = Modifier) {
    var sliderValue by remember { mutableStateOf(userfontSize) } // Initial font size

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
                    onFontChange(sliderValue)
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
    val radioOptions = Theme.values()
    Column {
        radioOptions.forEach { theme ->
            Row(

                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (theme == selectedTheme),
                        onClick = {
                            onThemeChange(theme)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp)
                    .semantics(mergeDescendants = true) {onClick(label = "Change to $theme Theme", action = null)}

            ) {
                RadioButton(
                    selected = (theme == selectedTheme),
                    onClick = { onThemeChange(theme) }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = theme.name, modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}