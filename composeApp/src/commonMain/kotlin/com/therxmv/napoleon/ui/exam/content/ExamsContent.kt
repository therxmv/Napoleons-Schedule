package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.therxmv.leonui.animation.leonLazyListAnimation
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.input.LeonTextInput
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
import com.therxmv.napoleon.ui.exam.component.ExamsUiEvent
import com.therxmv.napoleon.ui.exam.component.ExamsUiEvent.UpdateItem
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Trash2

@Composable
fun ExamsContent(
    modifier: Modifier = Modifier,
    data: ExamsUiData,
    fallbackReason: String?,
    onEvent: (ExamsUiEvent) -> Unit,
) {
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

        data.sections.forEachIndexed { index, section ->
            item(key = section.id) {
                val color =
                    if (index % 2 == 0) LeonTheme.colors.primary else LeonTheme.colors.tertiary

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

            itemsIndexed(
                items = section.items,
                key = { _, item -> item.id },
            ) { index, item ->
                SectionSubItem(
                    modifier = Modifier.leonLazyListAnimation(),
                    item = item,
                    section = section,
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

        if (section.isEditing) {
            LeonButton(
                label = "Save", // TODO add string res
                style = LeonButtonStyle.Text(contentColor),
                prefixIcon = FeatherIcons.Check,
                onClick = { onEvent(ExamsUiEvent.SaveSection(section.id)) },
            )
        } else {
            LeonIconButton(
                icon = FeatherIcons.Edit2,
                tint = contentColor,
                onClick = { onEvent(ExamsUiEvent.EditSection(section.id)) },
            )

            CompositionLocalProvider(LocalCopyIconColor provides contentColor) {
                CopyIconButton(textToCopy = section.toString())
            }
        }
    }
}

@Composable
private fun SectionSubItem(
    modifier: Modifier = Modifier,
    item: ExamsUiData.Item,
    section: ExamsUiData.Section,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    LeonExpandableSubItem(
        modifier = modifier,
        isLast = item == section.items.last(),
        onClick = {
            onEvent(ExamsUiEvent.AddNewItem(sectionId = section.id))
        }.takeIf { item is ExamsUiData.Item.AddNew },
    ) {
        when (item) {
            is ExamsUiData.Item.Exam -> ExamContent(
                modifier = Modifier.weight(1f),
                item = item,
                isEditing = section.isEditing,
                onNameChanged = { value ->
                    onEvent(
                        UpdateItem(
                            sectionId = section.id,
                            itemId = item.id,
                            newName = value
                        )
                    )
                },
                onTeacherChanged = { value ->
                    onEvent(
                        UpdateItem(
                            sectionId = section.id,
                            itemId = item.id,
                            newTeacher = value
                        )
                    )
                },
                onDelete = {
                    onEvent(ExamsUiEvent.DeleteItem(sectionId = section.id, itemId = item.id))
                },
            )

            is ExamsUiData.Item.Zalik -> ZalikContent(
                modifier = Modifier.weight(1f),
                item = item,
                isEditing = section.isEditing,
                onNameChanged = { value ->
                    onEvent(
                        UpdateItem(
                            sectionId = section.id,
                            itemId = item.id,
                            newName = value
                        )
                    )
                },
                onDelete = {
                    onEvent(ExamsUiEvent.DeleteItem(sectionId = section.id, itemId = item.id))
                },
            )

            is ExamsUiData.Item.AddNew -> AddNewContent(
                modifier = Modifier.weight(1f),
                item = item,
            )

            is ExamsUiData.Item.EmptyPlaceholder -> LeonText(
                modifier = Modifier.weight(1f),
                text = item.name,
                weight = LeonTextWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ExamContent(
    modifier: Modifier = Modifier,
    item: ExamsUiData.Item.Exam,
    isEditing: Boolean,
    onNameChanged: (String) -> Unit,
    onTeacherChanged: (String) -> Unit,
    onDelete: () -> Unit,
) {
    LeonText(
        // TODO edit date
        text = item.date,
        weight = LeonTextWeight.Bold,
        color = LeonTheme.colors.surfaceTint,
    )
    Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.skinny),
    ) {
        EditableText(
            modifier = Modifier.fillMaxWidth(),
            value = item.name,
            onValueChange = onNameChanged,
            isEditing = isEditing,
        )

        EditableText(
            modifier = Modifier.fillMaxWidth(),
            value = item.teacher,
            onValueChange = onTeacherChanged,
            isEditing = isEditing,
        )
    }

    if (isEditing) {
        LeonIconButton(
            icon = FeatherIcons.Trash2,
            tint = LeonTheme.colors.error,
            onClick = onDelete,
        )
    }
}

@Composable
private fun ZalikContent(
    modifier: Modifier = Modifier,
    item: ExamsUiData.Item.Zalik,
    isEditing: Boolean,
    onNameChanged: (String) -> Unit,
    onDelete: () -> Unit,
) {
    EditableText(
        modifier = modifier,
        value = item.name,
        onValueChange = onNameChanged,
        isEditing = isEditing,
    )

    if (isEditing) {
        LeonIconButton(
            icon = FeatherIcons.Trash2,
            tint = LeonTheme.colors.error,
            onClick = onDelete,
        )
    }
}

@Composable
private fun AddNewContent(
    modifier: Modifier = Modifier,
    item: ExamsUiData.Item.AddNew,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = FeatherIcons.Plus,
            tint = LeonTheme.colors.surfaceTint,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

        LeonText(
            text = item.name,
            color = LeonTheme.colors.surfaceTint,
            weight = LeonTextWeight.Bold,
        )
    }
}

@Composable
private fun EditableText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
) {
    if (isEditing) {
        LeonTextInput(
            // TODO long text is cut out
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
        )
    } else {
        LeonText(
            modifier = modifier,
            text = value,
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
