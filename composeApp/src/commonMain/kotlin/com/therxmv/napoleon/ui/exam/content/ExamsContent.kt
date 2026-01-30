package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.animation.leonLazyListAnimation
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.list.LeonExpandableHeader
import com.therxmv.leonui.list.LeonSwipeState
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.base.ui.CopyIconButton
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.exam.component.ExamsUiData
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Item
import com.therxmv.napoleon.ui.exam.component.ExamsUiEvent
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus

@Composable
fun ExamsContent(
    modifier: Modifier = Modifier,
    data: ExamsUiData,
    fallbackReason: String?,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    var swipedItemId by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        modifier = modifier,
        contentPadding = LeonTheme.paddings.baseValues,
    ) {
        if (fallbackReason != null) {
            item {
                LeonCard(
                    text = fallbackReason,
                    type = LeonCardType.Error,
                )
                Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
            }
        }

        item {
            LeonCard(
                text = data.infoData.text,
                hyperlinkText = data.infoData.linkText,
                hyperlink = data.infoData.link,
                type = LeonCardType.Info,
            )
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
        }

        data.sections.forEachIndexed { index, section ->
            item(key = section.id) {
                val color = if (index % 2 == 0) LeonTheme.colors.primary else LeonTheme.colors.tertiary

                if (index != 0) {
                    Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
                }

                SectionHeader(
                    modifier = Modifier.leonLazyListAnimation(),
                    section = section,
                    color = color,
                    onEvent = onEvent,
                )
            }

            // TODO drag reorder
            itemsIndexed(
                items = section.items,
                key = { _, item -> item.id },
                contentType = { _, item -> item },
            ) { index, item ->
                SectionSubItem(
                    modifier = Modifier.leonLazyListAnimation(),
                    item = item,
                    section = section,
                    swipedId = swipedItemId,
                    onSwipe = { state ->
                        when (state) {
                            LeonSwipeState.Start -> {
                                if (swipedItemId == item.id) swipedItemId == null
                            }

                            LeonSwipeState.End -> {
                                swipedItemId = item.id
                            }
                        }
                    },
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    modifier: Modifier = Modifier,
    section: ExamsUiData.Section,
    color: Color,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    val contentColor = LeonTheme.colors.contentColorFor(color)

    LeonExpandableHeader(
        modifier = modifier,
        color = color,
        isExpanded = true,
    ) {
        LeonText(
            modifier = Modifier.weight(1f),
            text = section.title,
            size = LeonTextSize.Title2,
            color = contentColor,
            weight = LeonTextWeight.Bold,
        )

        LeonButton(
            prefixIcon = FeatherIcons.Plus,
            label = Res.string.exams_add_new,
            style = LeonButtonStyle.Text(contentColor),
            onClick = { onEvent(ExamsUiEvent.AddNewItem(section.id)) },
        )

        CompositionLocalProvider(LocalCopyIconColor provides contentColor) {
            CopyIconButton(textToCopy = section.toString())
        }
    }
}

@Composable
private fun SectionSubItem(
    modifier: Modifier = Modifier,
    item: Item,
    swipedId: String?,
    onSwipe: (LeonSwipeState) -> Unit,
    section: ExamsUiData.Section,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    val isLast = item == section.items.last()

    when (item) {
        is Item.Editable.Exam -> ExamItemContent(
            modifier = modifier,
            sectionId = section.id,
            item = item,
            isLast = isLast,
            swipedId = swipedId,
            onSwipe = onSwipe,
            onEvent = onEvent,
        )

        is Item.Editable.Zalik -> ZalikItemContent(
            modifier = modifier,
            sectionId = section.id,
            item = item,
            isLast = isLast,
            swipedId = swipedId,
            onSwipe = onSwipe,
            onEvent = onEvent,
        )

        is Item.EmptyPlaceholder -> EmptyItemContent(
            modifier = modifier,
            item = item,
            isLast = isLast,
        )
    }
}

@LeonPreview
@Composable
private fun DashboardContentPreview() {
    LeonPreview {
        ExamsContent(
            data = PreviewMockData.examsUiData,
            fallbackReason = "Fallback reason",
            onEvent = {},
        )
    }
}
