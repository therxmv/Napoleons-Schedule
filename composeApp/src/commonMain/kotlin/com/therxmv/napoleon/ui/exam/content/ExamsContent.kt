package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.list.LeonExpandableHeader
import com.therxmv.leonui.list.LeonExpandableSubItem
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.exam.component.ExamsUiData
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit2

@Composable
fun ExamsContent(
    modifier: Modifier = Modifier,
    data: ExamsUiData,
    fallbackReason: String?,
) {
    Column(
        modifier = modifier.padding(LeonTheme.paddings.baseValues),
    ) {
        if (fallbackReason != null) {
            LeonCard(
                text = fallbackReason,
                type = LeonCardType.Error,
            )
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
        }

        data.items.forEachIndexed { index, data ->
            val color = if (index % 2 == 0) LeonTheme.colors.primary else LeonTheme.colors.tertiary
            ItemContent(
                data = data,
                color = color,
            )

            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
        }
    }
}

@Composable
private fun ItemContent(
    data: ExamsUiData.ItemsData,
    color: Color,
) {
    val contentColor = LeonTheme.colors.contentColorFor(color)
    LeonExpandableHeader(
        color = color,
        isExpanded = true,
    ) {
        LeonText(
            modifier = Modifier.weight(1f),
            text = data.title,
            size = LeonTextSize.Title2,
            color = contentColor,
            weight = LeonTextWeight.Bold,
        )

        LeonIconButton(
            icon = FeatherIcons.Edit2,
            tint = contentColor,
            onClick = {},
        )

        CompositionLocalProvider(LocalCopyIconColor provides contentColor) {
            CopyIconButton(textToCopy = data.toString())
        }
    }

    data.items.forEachIndexed { index, item ->
        LeonExpandableSubItem(isLast = index == data.items.lastIndex) {
            when (item) {
                is ExamsUiData.Item.Exam -> ExamContent(item)

                is ExamsUiData.Item.Zalik -> LeonText(
                    text = item.name,
                )

                is ExamsUiData.Item.EmptyPlaceholder -> LeonText(
                    modifier = Modifier.weight(1f),
                    text = item.text,
                    weight = LeonTextWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ExamContent(
    data: ExamsUiData.Item.Exam,
) {
    LeonText(
        text = data.date,
        weight = LeonTextWeight.Bold,
        color = LeonTheme.colors.surfaceTint,
    )
    Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.base))

    Column(
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.skinny),
    ) {
        LeonText(
            text = data.name,
            weight = LeonTextWeight.Bold,
        )

        LeonText(
            text = data.teacher,
            size = LeonTextSize.Body2,
            color = LeonTheme.colors.onSurfaceVariant,
        )
    }
}

@LeonPreview
@Composable
private fun DashboardContentPreview() {
    LeonPreview {
        ExamsContent(
            data = PreviewMockData.examsUiData,
            fallbackReason = "Fallback reason"
        )
    }
}