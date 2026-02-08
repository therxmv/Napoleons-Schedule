package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.therxmv.datetime.DateTimeConstants
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.button.LeonIconButtonStyle
import com.therxmv.leonui.input.LeonTextInput
import com.therxmv.leonui.list.LeonSwipeState
import com.therxmv.leonui.list.LeonSwipeableExpandableSubItem
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Item
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Section
import com.therxmv.napoleon.ui.exam.component.ExamsUiEvent
import com.therxmv.napoleon.ui.exam.component.ExamsUiEvent.UpdateItem
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.Trash2
import kotlinx.datetime.format

@Composable
fun RowScope.ExamItemContent(
    item: Item.Editable.Exam,
    sectionId: Section.Id,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    EditableDate(
        text = item.date.format(DateTimeConstants.Format.dayMonthFormat),
        isEditing = item.isEditing,
        onClick = { onEvent(ExamsUiEvent.ChangeItemDate(sectionId, item.id, item.date)) },
    )
    Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.skinny),
    ) {
        EditableText(
            modifier = Modifier.fillMaxWidth(),
            value = item.name,
            onValueChange = { onEvent(UpdateItem(sectionId, item.id, newName = it)) },
            isEditing = item.isEditing,
        )

        EditableText(
            modifier = Modifier.fillMaxWidth(),
            value = item.teacher,
            onValueChange = { onEvent(UpdateItem(sectionId, item.id, newTeacher = it)) },
            isEditing = item.isEditing,
        )
    }

    if (item.isEditing) {
        SaveAction(onClick = { onEvent(ExamsUiEvent.SaveItem(sectionId, item.id)) })
    }
}

@Composable
fun RowScope.ZalikItemContent(
    sectionId: Section.Id,
    item: Item.Editable.Zalik,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    EditableText(
        modifier = Modifier.weight(1f),
        value = item.name,
        onValueChange = { onEvent(UpdateItem(sectionId, item.id, newName = it)) },
        isEditing = item.isEditing,
    )

    if (item.isEditing) {
        SaveAction(onClick = { onEvent(ExamsUiEvent.SaveItem(sectionId, item.id)) })
    }
}

@Composable
fun RowScope.EmptyItemContent(
    item: Item.EmptyPlaceholder,
) {
    LeonText(
        modifier = Modifier.weight(1f),
        text = item.name,
        weight = LeonTextWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun SwipeableSubItem(
    modifier: Modifier = Modifier,
    item: Item.Editable,
    isLast: Boolean,
    swipedId: String?,
    onSwipe: (LeonSwipeState) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    LeonSwipeableExpandableSubItem(
        modifier = modifier,
        isLast = isLast,
        shouldResetState = swipedId != item.id,
        onStateChanged = onSwipe,
        actions = {
            SwipeableActions(
                isEditing = item.isEditing,
                onEdit = onEdit,
                onDelete = onDelete,
            )
        },
        content = content,
    )
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
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            maxLines = 3,
        )
    } else {
        LeonText(
            modifier = modifier,
            text = value,
        )
    }
}

@Composable
private fun EditableDate(
    text: String,
    isEditing: Boolean,
    onClick: () -> Unit,
) {
    if (isEditing) {
        // TODO maybe change button
        LeonText(
            modifier = Modifier
                .clip(LeonTheme.shapes.allRounded)
                .background(LeonTheme.colors.surface)
                .clickable(onClick = onClick)
                .padding(LeonTheme.paddings.vertical.base),
            text = text,
            weight = LeonTextWeight.Bold,
            color = LeonTheme.colors.onSurface,
        )
    } else {
        LeonText(
            text = text,
            weight = LeonTextWeight.Bold,
            color = LeonTheme.colors.surfaceTint,
        )
    }
}

@Composable
private fun SwipeableActions(
    isEditing: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    if (isEditing.not()) {
        LeonIconButton(
            icon = FeatherIcons.Edit2,
            style = LeonIconButtonStyle.Filled(
                containerColor = LeonTheme.colors.primaryContainer,
            ),
            onClick = onEdit,
        )
    }

    LeonIconButton(
        icon = FeatherIcons.Trash2,
        style = LeonIconButtonStyle.Filled(
            containerColor = LeonTheme.colors.errorContainer,
        ),
        onClick = onDelete,
    )
}

@Composable
private fun SaveAction(
    onClick: () -> Unit,
) {
    LeonIconButton(
        icon = FeatherIcons.Check,
        style = LeonIconButtonStyle.Default,
        tint = LeonTheme.colors.surfaceTint,
        onClick = onClick,
    )
}