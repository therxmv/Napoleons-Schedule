package com.therxmv.leonui.input

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.list.LeonDividerType
import com.therxmv.leonui.list.LeonHorizontalDivider
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.leonui.theme.values.LeonSizes.Corner.toCornerRadius
import com.therxmv.leonui.theme.values.RoundedCornerShape

@Immutable
data class LeonDropdownInputData(
    val placeholder: String,
    val value: String? = null,
    val items: List<String> = emptyList(),
    val onClick: (String) -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeonDropdownInput(
    modifier: Modifier = Modifier,
    data: LeonDropdownInputData,
    style: LeonDropdownInputStyle = LeonDropdownInputStyle.Primary,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    val isEnabled = remember(data) { data.items.isNotEmpty() }
    val isExpanded by derivedStateOf { isFocused && isEnabled }

    val bottomRadius by animateDpAsState(targetValue = isExpanded.toCornerRadius())

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (isEnabled) isFocused = it.isFocused
            },
        expanded = isExpanded,
        onExpandedChange = {},
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            value = data.value.orEmpty(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = data.placeholder)
            },
            colors = style.colors,
            shape = RoundedCornerShape(
                top = LeonTheme.sizes.corner.defaultRadius,
                bottom = bottomRadius,
            ),
            enabled = data.items.isNotEmpty(),
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
        )

        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = isExpanded && data.items.isNotEmpty(),
            onDismissRequest = { focusManager.clearFocus() },
            shape = LeonTheme.shapes.onlyBottomRounded(),
            offset = DpOffset(
                0.dp,
                (-8 + LeonTheme.sizes.divider.thin.value).dp
            ), // removes menu's extra padding
            containerColor = Color.Transparent,
            shadowElevation = 0.dp,
        ) {
            data.items.forEachIndexed { index, item ->
                val isLast = index == data.items.lastIndex
                val shape = LeonTheme.shapes.onlyBottomRounded()
                    .takeIf { isLast } ?: LeonTheme.shapes.noneRounded

                DropdownMenuItem(
                    modifier = Modifier
                        .clip(shape)
                        .background(LeonTheme.colors.tertiary),
                    text = {
                        Text(text = item, color = LeonTheme.colors.onTertiary)
                    },
                    onClick = {
                        focusManager.clearFocus()
                        data.onClick(item)
                    },
                )

                if (isLast.not()) {
                    LeonHorizontalDivider(
                        type = LeonDividerType.Full,
                        color = LeonTheme.colors.surface,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LeonDropdownInputPreview() {
    val focusRequester = remember { FocusRequester() }
    val data = listOf("item1", "item2", "item3")

    LeonComponentPreview(
        modifier = Modifier.height(400.dp),
    ) {
        LeonDropdownInput(
            data = LeonDropdownInputData(
                placeholder = "Placeholder",
                items = data,
                onClick = {},
            )
        )

        LeonDropdownInput(
            modifier = Modifier.focusRequester(focusRequester),
            data = LeonDropdownInputData(
                placeholder = "Placeholder",
                value = "item1",
                items = data,
                onClick = {},
            )
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
private fun EmptyLeonDropdownInputPreview() {
    LeonComponentPreview {
        LeonDropdownInput(
            data = LeonDropdownInputData(
                placeholder = "Placeholder",
                onClick = {},
            )
        )
    }
}