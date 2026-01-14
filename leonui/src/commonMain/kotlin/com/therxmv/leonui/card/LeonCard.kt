package com.therxmv.leonui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme

@Composable
fun LeonCard(
    modifier: Modifier = Modifier,
    text: String,
    type: LeonCardType,
) {
    LeonCard(
        modifier = modifier,
        text = AnnotatedString(text),
        type = type,
    )
}

@Composable
fun LeonCard(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    type: LeonCardType,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(LeonTheme.shapes.allRounded)
            .background(type.containerColor)
            .applyIf(type.withBorder()) {
                border(LeonTheme.paddings.border, type.accent, LeonTheme.shapes.allRounded)
            }
            .padding(LeonTheme.paddings.defaultValues),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = type.icon,
            tint = type.accent,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal))

        LeonText(
            text = text,
            color = type.contentColor,
        )
    }
}

@Preview
@Composable
private fun LeonCardPreview(
    @PreviewParameter(LeonCardTypeProvider::class) type: LeonCardType,
) {
    LeonComponentPreview {
        LeonCard(
            text = "This is an example of\n$type LeonCard",
            type = type,
        )
    }
}

private class LeonCardTypeProvider : PreviewParameterProvider<LeonCardType> {
    override val values = sequenceOf(
        LeonCardType.Info,
        LeonCardType.Error,
    )
}