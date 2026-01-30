package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.button.LeonIconButtonStyle
import com.therxmv.leonui.input.LeonTextInput
import com.therxmv.leonui.list.LeonExpandableSubItem
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

@Composable
fun ExamItemContent(
    modifier: Modifier = Modifier,
    sectionId: Section.Id,
    item: Item.Editable.Exam,
    isLast: Boolean,
    swipedId: String?,
    onSwipe: (LeonSwipeState) -> Unit,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    SwipeableSubItem(
        modifier = modifier,
        isLast = isLast,
        item = item,
        swipedId = swipedId,
        onSwipe = onSwipe,
        onEdit = { onEvent(ExamsUiEvent.EditItem(sectionId, item.id)) },
        onDelete = { onEvent(ExamsUiEvent.DeleteItem(sectionId, item.id)) },
    ) {
        LeonText(
            // TODO edit date
            text = item.date,
            weight = LeonTextWeight.Bold,
            color = LeonTheme.colors.surfaceTint,
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
}

@Composable
fun ZalikItemContent(
    modifier: Modifier = Modifier,
    sectionId: Section.Id,
    item: Item.Editable.Zalik,
    isLast: Boolean,
    swipedId: String?,
    onSwipe: (LeonSwipeState) -> Unit,
    onEvent: (ExamsUiEvent) -> Unit,
) {
    SwipeableSubItem(
        modifier = modifier,
        isLast = isLast,
        item = item,
        swipedId = swipedId,
        onSwipe = onSwipe,
        onEdit = { onEvent(ExamsUiEvent.EditItem(sectionId, item.id)) },
        onDelete = { onEvent(ExamsUiEvent.DeleteItem(sectionId, item.id)) },
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
}

@Composable
fun EmptyItemContent(
    modifier: Modifier = Modifier,
    item: Item.EmptyPlaceholder,
    isLast: Boolean,
) {
    LeonExpandableSubItem(
        modifier = modifier,
        isLast = isLast,
    ) {
        LeonText(
            modifier = Modifier.weight(1f),
            text = item.name,
            weight = LeonTextWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SwipeableSubItem(
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
            tint = LeonTheme.colors.onPrimaryContainer,
            onClick = onEdit,
        )
    }

    LeonIconButton(
        icon = FeatherIcons.Trash2,
        style = LeonIconButtonStyle.Filled(
            containerColor = LeonTheme.colors.errorContainer,
        ),
        tint = LeonTheme.colors.onErrorContainer,
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