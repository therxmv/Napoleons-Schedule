package com.therxmv.leonui.list

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import com.therxmv.leonui.extensions.applyIf

@Composable
fun LeonHorizontalDivider(
    type: LeonDividerType,
    modifier: Modifier = Modifier,
    color: Color = DividerDefaults.color,
) {
    HorizontalDivider(
        modifier = Modifier
            .applyIf(type.width.isSpecified) { width(50.dp) }
            .clip(CircleShape)
            .then(modifier),
        thickness = type.thickness,
        color = color,
    )
}