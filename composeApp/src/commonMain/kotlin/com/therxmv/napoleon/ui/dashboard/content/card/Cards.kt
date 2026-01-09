package com.therxmv.napoleon.ui.dashboard.content.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.list.LeonDividerType
import com.therxmv.leonui.list.LeonHorizontalDivider
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.dashboard.component.DashboardUiData

@Composable
fun CardDivider(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LeonTheme.paddings.vertical),
        contentAlignment = Alignment.Center,
    ) {
        LeonHorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.5f),
            type = LeonDividerType.Full,
        )
    }
}

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    data: DashboardUiData.Card.Default,
    background: Color,
) {
    CompositionLocalProvider(
        LocalCardConfig provides when {
            data.ratio == 1f || data.gridSpan > 1 -> bigCard()
            else -> smallCard()
        }
    ) {
        when {
            data.gridSpan == 1 && data.ratio == 1f -> VerticalCard(modifier, data, background)

            else -> HorizontalCard(modifier, data, background)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerticalCard(
    modifier: Modifier = Modifier,
    data: DashboardUiData.Card.Default,
    background: Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(data.ratio)
            .clip(LeonTheme.shapes.allRounded)
            .background(background)
            .clickable(onClick = data.onClick)
            .padding(LeonTheme.paddings.defaultValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            icon = data.icon,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))
        Title(
            text = data.title,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
    }
}

@Composable
private fun HorizontalCard(
    modifier: Modifier = Modifier,
    data: DashboardUiData.Card.Default,
    background: Color,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(data.ratio)
            .clip(RoundedCornerShape(LeonTheme.shapes.cornerRadius.times(2)))
            .background(background)
            .clickable(onClick = data.onClick)
            .padding(LeonTheme.paddings.defaultValues),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon = data.icon,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))
        Title(
            text = data.title,
            color = MaterialTheme.colorScheme.contentColorFor(background),
        )
    }
}

@Composable
private fun Icon(icon: ImageVector, color: Color) {
    Icon(
        modifier = Modifier.size(LocalCardConfig.current.iconSize),
        tint = color,
        imageVector = icon,
        contentDescription = "Icon",
    )
}

@Composable
private fun Title(text: String, color: Color) {
    Text(
        text = text,
        style = LocalCardConfig.current.textStyle,
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = FontWeight.Bold,
    )
}

private val LocalCardConfig = compositionLocalOf {
    CardConfig(24.dp, TextStyle())
}

private data class CardConfig(val iconSize: Dp, val textStyle: TextStyle)

@Composable
private fun smallCard(): CardConfig =
    CardConfig(24.dp, MaterialTheme.typography.titleMedium)

@Composable
private fun bigCard(): CardConfig =
    CardConfig(36.dp, MaterialTheme.typography.headlineSmall)