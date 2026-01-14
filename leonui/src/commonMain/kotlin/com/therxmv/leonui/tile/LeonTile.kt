package com.therxmv.leonui.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Headphones

@Composable
fun LeonTile(
    modifier: Modifier = Modifier,
    size: LeonTileSize,
    type: LeonTileType,
    icon: ImageVector,
    title: String,
    ratio: Float,
    background: Color,
    onClick: () -> Unit,
) {
    when (type) {
        LeonTileType.Horizontal -> HorizontalTile(
            modifier = modifier,
            icon = icon,
            title = title,
            ratio = ratio,
            background = background,
            size = size,
            onClick = onClick,
        )

        LeonTileType.Vertical -> VerticalTile(
            modifier = modifier,
            icon = icon,
            title = title,
            ratio = ratio,
            background = background,
            size = size,
            onClick = onClick,
        )
    }
}

@Composable
private fun VerticalTile(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    ratio: Float,
    background: Color,
    size: LeonTileSize,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .clip(LeonTheme.shapes.allRounded)
            .background(background)
            .clickable(onClick = onClick)
            .padding(LeonTheme.paddings.defaultValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            icon = icon,
            size = size.iconSize,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))
        Title(
            text = title,
            size = size.textSize,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
    }
}

@Composable
private fun HorizontalTile(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    ratio: Float,
    background: Color,
    size: LeonTileSize,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .clip(RoundedCornerShape(LeonTheme.shapes.cornerRadius.times(2)))
            .background(background)
            .clickable(onClick = onClick)
            .padding(LeonTheme.paddings.defaultValues),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon = icon,
            size = size.iconSize,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))
        Title(
            text = title,
            size = size.textSize,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
    }
}

@Composable
private fun Icon(icon: ImageVector, size: Dp, color: Color) {
    Icon(
        modifier = Modifier.size(size),
        tint = color,
        imageVector = icon,
        contentDescription = null,
    )
}

@Composable
private fun Title(text: String, size: LeonTextSize, color: Color) {
    LeonText(
        text = text,
        color = color,
        size = size,
        weight = LeonTextWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun LeonTilePreview(
    @PreviewParameter(LeonTileSizeProvider::class) size: LeonTileSize,
) {
    val text = "$size Tile"
    LeonComponentPreview {
        LeonTile(
            size = size,
            type = LeonTileType.Vertical,
            icon = FeatherIcons.Headphones,
            title = text,
            ratio = 1f,
            background = MaterialTheme.colorScheme.primary,
            onClick = {},
        )

        LeonTile(
            size = size,
            type = LeonTileType.Horizontal,
            icon = FeatherIcons.Headphones,
            title = text,
            ratio = 2f,
            background = MaterialTheme.colorScheme.primary,
            onClick = {},
        )
    }
}

private class LeonTileSizeProvider : PreviewParameterProvider<LeonTileSize> {
    override val values = sequenceOf(
        LeonTileSize.Big,
        LeonTileSize.Small,
    )
}