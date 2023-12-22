package com.example.bookcraftapplication.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.bookcraftapplication.Theme

/**
 * Screen that allows the user to change the theme.
 */
@Composable
fun Settings(
    selectedTheme: Theme,
    onThemeChange: (Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Text(text = "   Theme")
            SelectTheme(selectedTheme, onThemeChange)
        }
    }
}

@Composable
fun SelectTheme(
    selectedTheme: Theme,
    onThemeChange: (Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val radioOptions = Theme.entries.toTypedArray()
    Column {
        radioOptions.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (theme == selectedTheme),
                            onClick = {
                                onThemeChange(theme)
                            },
                            role = Role.RadioButton,
                        )
                        .padding(horizontal = 16.dp)
                        .semantics(mergeDescendants = true) { onClick(label = "Change to $theme Theme", action = null) },
            ) {
                RadioButton(
                    selected = (theme == selectedTheme),
                    onClick = { onThemeChange(theme) },
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = theme.name, modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}
