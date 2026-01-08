package com.therxmv.leonui.theme.values

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object LeonColors {

    val button = Button
    object Button {
        val default: ButtonColors
            @Composable get() = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            )

        val outlined: ButtonColors
            @Composable get() = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )

        val text: ButtonColors
            @Composable get() = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.surfaceTint,
            )

        val icon: IconButtonColors
            @Composable get() = IconButtonDefaults.iconButtonColors()

        val filledIcon: IconButtonColors
            @Composable get() = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val dropDownTextField: TextFieldColors
        @Composable get() = ExposedDropdownMenuDefaults.textFieldColors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,

            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,

            unfocusedPrefixColor = MaterialTheme.colorScheme.onPrimary,
            focusedPrefixColor = MaterialTheme.colorScheme.onPrimary,

            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
        )

    val primaryOutlinedTextField: TextFieldColors
        @Composable get() = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            errorContainerColor = MaterialTheme.colorScheme.surface,

            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,

            selectionColors = TextSelectionColors(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.tertiary)
        )

    val tertiaryOutlinedTextField: TextFieldColors
        @Composable get() = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            errorContainerColor = MaterialTheme.colorScheme.surface,

            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,

            selectionColors = TextSelectionColors(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)
        )

    val dialogTint: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceTint

    val navBarItem: NavigationBarItemColors
        @Composable get() = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )

    internal val LightScheme = lightColorScheme(
        primary = Color(0xFFDBE1FF),
        onPrimary = Color(0xFF001849),
        primaryContainer = Color(0xFFDBE1FF),
        onPrimaryContainer = Color(0xFF001849),
        secondary = Color(0xFFDDE1F9),
        onSecondary = Color(0xFF151B2C),
        secondaryContainer = Color(0xFFDDE1F9),
        onSecondaryContainer = Color(0xFF151B2C),
        tertiary = Color(0xFFE5DEFF),
        onTertiary = Color(0xFF1B0261),
        tertiaryContainer = Color(0xFFE5DEFF),
        onTertiaryContainer = Color(0xFF1B0261),
        error = Color(0xFFBA1A1A),
        errorContainer = Color(0xFFFFDAD6),
        onError = Color(0xFFFFFFFF),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFFEFBFF),
        onBackground = Color(0xFF1B1B1F),
        surface = Color(0xFFFEFBFF),
        onSurface = Color(0xFF1B1B1F),
        surfaceVariant = Color(0xFFE2E2EC),
        onSurfaceVariant = Color(0xFF45464F),
        outline = Color(0xFF757680),
        inverseOnSurface = Color(0xFFF2F0F4),
        inverseSurface = Color(0xFF303034),
        inversePrimary = Color(0xFFB3C5FF),
        surfaceTint = Color(0xFF3C5BA9),
        outlineVariant = Color(0xFFC5C6D0),
        scrim = Color(0xFF000000),
    )

    internal val DarkScheme = darkColorScheme(
        primary = Color(0xFFB3C5FF),
        onPrimary = Color(0xFF002B75),
        primaryContainer = Color(0xFFB3C5FF),
        onPrimaryContainer = Color(0xFF002B75),
        secondary = Color(0xFFC1C6DD),
        onSecondary = Color(0xFF2A3042),
        secondaryContainer = Color(0xFFC1C6DD),
        onSecondaryContainer = Color(0xFF2A3042),
        tertiary = Color(0xFFC9BFFF),
        onTertiary = Color(0xFF302175),
        tertiaryContainer = Color(0xFFC9BFFF),
        onTertiaryContainer = Color(0xFF302175),
        error = Color(0xFFFFB4AB),
        errorContainer = Color(0xFF93000A),
        onError = Color(0xFF690005),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF1B1B1F),
        onBackground = Color(0xFFE4E2E6),
        surface = Color(0xFF1B1B1F),
        onSurface = Color(0xFFE4E2E6),
        surfaceVariant = Color(0xFF45464F),
        onSurfaceVariant = Color(0xFFC5C6D0),
        outline = Color(0xFF8F909A),
        inverseOnSurface = Color(0xFF1B1B1F),
        inverseSurface = Color(0xFFE4E2E6),
        inversePrimary = Color(0xFF3C5BA9),
        surfaceTint = Color(0xFFB3C5FF),
        outlineVariant = Color(0xFF45464F),
        scrim = Color(0xFF000000),
    )
}