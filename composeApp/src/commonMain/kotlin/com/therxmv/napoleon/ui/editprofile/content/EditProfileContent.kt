package com.therxmv.napoleon.ui.editprofile.content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.therxmv.napoleon.base.state.FallbackCard
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiData
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiEvent
import com.therxmv.napoleon.ui.theme.NapoleonTheme

@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    data: EditProfileUiData,
    fallbackReason: String?,
    onEvent: (EditProfileUiEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(NapoleonTheme.paddings.defaultValues)
            .focusable(), // Didn't work without focusable on API 26,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (fallbackReason != null) {
            FallbackCard(fallbackReason)
            Spacer(modifier = Modifier.height(NapoleonTheme.paddings.vertical))
        }

        Dropdown(
            data = data.facultyDropdown,
        )
        Spacer(modifier = Modifier.height(NapoleonTheme.paddings.vertical))

        Dropdown(
            data = data.yearDropdown,
        )
        Spacer(modifier = Modifier.height(NapoleonTheme.paddings.vertical))

        Dropdown(
            data = data.specialtyDropdown,
        )
        Spacer(modifier = Modifier.height(NapoleonTheme.paddings.vertical.times(2)))

        SaveButton(
            label = data.saveLabel,
            isEnabled = data.isAllSelected,
            onClick = {
                onEvent(EditProfileUiEvent.SaveProfile)
            },
        )
    }
}

@Composable
private fun SaveButton(
    label: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        colors = NapoleonTheme.colors.button,
    ) {
        Text(
            modifier = Modifier.padding(NapoleonTheme.paddings.defaultValues),
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Dropdown(
    modifier: Modifier = Modifier,
    data: EditProfileUiData.Dropdown,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    val isEnabled = remember(data) { data.items.isNotEmpty() }
    val isExpanded by derivedStateOf { isFocused && isEnabled }

    val bottomCornerRadius = NapoleonTheme.shapes.noneCornerRadius
        .takeIf { isExpanded } ?: NapoleonTheme.shapes.cornerRadius
    val bottomRadius by animateDpAsState(targetValue = bottomCornerRadius)

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
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = data.value.orEmpty(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = data.placeholder)
            },
            colors = NapoleonTheme.colors.dropDownTextField,
            shape = RoundedCornerShape(
                topStart = NapoleonTheme.shapes.cornerRadius,
                topEnd = NapoleonTheme.shapes.cornerRadius,
                bottomEnd = bottomRadius,
                bottomStart = bottomRadius,
            ),
            enabled = data.items.isNotEmpty(),
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
        )

        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = isExpanded && data.items.isNotEmpty(),
            onDismissRequest = { focusManager.clearFocus() },
            shape = NapoleonTheme.shapes.onlyBottomRounded,
            offset = DpOffset(0.dp, (-6).dp), // removes menu's extra padding
            containerColor = Color.Transparent,
            shadowElevation = 0.dp,
        ) {
            data.items.forEachIndexed { index, item ->
                val isLast = index == data.items.lastIndex
                val shape = NapoleonTheme.shapes.onlyBottomRounded
                    .takeIf { isLast } ?: NapoleonTheme.shapes.noneRounded

                DropdownMenuItem(
                    modifier = Modifier
                        .clip(shape)
                        .background(MaterialTheme.colorScheme.tertiary),
                    text = {
                        Text(text = item, color = MaterialTheme.colorScheme.onTertiary)
                    },
                    onClick = {
                        focusManager.clearFocus()
                        data.onClick(item)
                    },
                )

                if (isLast.not()) {
                    HorizontalDivider(
                        thickness = NapoleonTheme.paddings.divider,
                        color = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }
}