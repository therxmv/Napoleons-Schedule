package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.therxmv.napoleon.base.ui.LocalCopyIconColor
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import com.therxmv.napoleon.ui.rating.component.RatingUiState
import com.therxmv.napoleon.ui.theme.NapoleonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Trash2

@Composable
fun RatingInputs(
    modifier: Modifier = Modifier,
    data: RatingUiState,
    onEvent: (RatingUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = NapoleonTheme.paddings.defaultValues,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(NapoleonTheme.paddings.vertical),
    ) {
        item {
            AddInputButton(
                modifier = Modifier.animateItem(),
                label = data.addInputLabel,
                onClick = { onEvent(RatingUiEvent.AddInput) },
            )
        }

        if (data.subjects.isNotEmpty()) {
            item {
                InputLabels(
                    modifier = Modifier.animateItem(),
                    name = data.nameLabel,
                    credits = data.creditsLabel,
                    score = data.scoreLabel,
                )
            }
        }

        items(
            items = data.subjects,
            key = { it.id },
        ) { input ->
            SubjectItem(
                modifier = Modifier.animateItem(),
                data = input,
                onEvent = onEvent,
            )

            if (input.error != null) {
                Spacer(modifier = Modifier.height(NapoleonTheme.paddings.halfVertical))
                ErrorText(
                    modifier = Modifier.animateItem(),
                    error = input.error,
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}

@Composable
private fun SubjectItem(
    modifier: Modifier = Modifier,
    data: RatingUiState.Subject,
    onEvent: (RatingUiEvent) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(NapoleonTheme.shapes.allRounded)
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = NapoleonTheme.paddings.halfVertical, horizontal = NapoleonTheme.paddings.halfHorizontal),
        horizontalArrangement = Arrangement.spacedBy(NapoleonTheme.paddings.halfHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InputField(
            modifier = Modifier.weight(2f),
            value = data.name,
            error = data.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            onValueChange = {
                onEvent(
                    RatingUiEvent.UpdateInput(
                        id = data.id,
                        name = it,
                    )
                )
            }
        )

        InputField(
            modifier = Modifier.weight(1f),
            value = data.credits,
            error = data.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            onValueChange = {
                onEvent(
                    RatingUiEvent.UpdateInput(
                        id = data.id,
                        credits = it,
                    )
                )
            }
        )

        InputField(
            modifier = Modifier.weight(1f),
            value = data.score,
            error = data.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            onValueChange = {
                onEvent(
                    RatingUiEvent.UpdateInput(
                        id = data.id,
                        score = it,
                    )
                )
            }
        )

        DeleteIcon(
            modifier = Modifier.weight(0.5f),
            onClick = {
                onEvent(RatingUiEvent.DeleteInput(data.id))
            },
        )
    }
}

@Composable
private fun InputLabels(
    modifier: Modifier = Modifier,
    name: String,
    credits: String,
    score: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = NapoleonTheme.paddings.halfHorizontal),
        horizontalArrangement = Arrangement.spacedBy(NapoleonTheme.paddings.halfHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InputLabel(
            modifier = Modifier.weight(2f),
            label = name,
        )
        InputLabel(
            modifier = Modifier.weight(1f),
            label = credits,
        )
        InputLabel(
            modifier = Modifier.weight(1f),
            label = score,
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun InputLabel(
    modifier: Modifier = Modifier,
    label: String,
) {
    Text(
        modifier = modifier,
        text = label,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun ErrorText(
    modifier: Modifier = Modifier,
    error: String,
) {
    Text(
        modifier = modifier,
        text = error,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
private fun DeleteIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = FeatherIcons.Trash2,
            contentDescription = "Delete",
            tint = LocalCopyIconColor.current,
        )
    }
}

@Composable
private fun AddInputButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = NapoleonTheme.colors.button,
        shape = RoundedCornerShape(NapoleonTheme.shapes.cornerRadius.times(2)),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(NapoleonTheme.paddings.defaultValues),
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = error != null,
        shape = NapoleonTheme.shapes.allRounded,
        textStyle = TextStyle(fontWeight = FontWeight.Bold),
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        colors = NapoleonTheme.colors.outlinedTextField
    )
}