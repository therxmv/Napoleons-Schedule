package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.text.withLeonLink
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.rating.component.RatingUiData
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Trash2

@Composable
fun RatingInputs(
    modifier: Modifier = Modifier,
    data: RatingUiData,
    heightFraction: Float,
    onEvent: (RatingUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(heightFraction),
        contentPadding = LeonTheme.paddings.defaultValues,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical),
    ) {
        item {
            InfoCard(data.infoData)
        }

        item {
            LeonButton(
                modifier = Modifier.animateItem(),
                label = data.addInputLabel,
                onClick = { onEvent(RatingUiEvent.AddSubjectInput) },
                prefixIcon = FeatherIcons.Plus,
            )
        }

        if (data.subjectInputs.isNotEmpty()) {
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
            items = data.subjectInputs,
            key = { it.id },
        ) { input ->
            SubjectItem(
                modifier = Modifier.animateItem(),
                data = input,
                onEvent = onEvent,
            )

            if (input.error != null) {
                Spacer(modifier = Modifier.height(LeonTheme.paddings.halfVertical))
                ErrorText(
                    modifier = Modifier.animateItem(),
                    error = input.error,
                )
            }
        }
    }
}

@Composable
private fun SubjectItem(
    modifier: Modifier = Modifier,
    data: RatingUiData.SubjectInput,
    onEvent: (RatingUiEvent) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(LeonTheme.shapes.allRounded)
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = LeonTheme.paddings.halfVertical, horizontal = LeonTheme.paddings.halfHorizontal),
        horizontalArrangement = Arrangement.spacedBy(LeonTheme.paddings.halfHorizontal),
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
                    RatingUiEvent.UpdateSubjectInput(
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
                    RatingUiEvent.UpdateSubjectInput(
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
                    RatingUiEvent.UpdateSubjectInput(
                        id = data.id,
                        score = it,
                    )
                )
            }
        )

        LeonIconButton(
            modifier = Modifier.weight(0.5f),
            icon = FeatherIcons.Trash2,
            tint = MaterialTheme.colorScheme.onPrimary,
            onClick = {
                onEvent(RatingUiEvent.DeleteSubjectInput(data.id))
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
            .padding(horizontal = LeonTheme.paddings.halfHorizontal),
        horizontalArrangement = Arrangement.spacedBy(LeonTheme.paddings.halfHorizontal),
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
    LeonText(
        modifier = modifier,
        text = label,
        size = LeonTextSize.Body2,
    )
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    error: String,
) {
    LeonText(
        modifier = modifier,
        text = error,
        size = LeonTextSize.Body2,
        color = MaterialTheme.colorScheme.error,
        weight = LeonTextWeight.Bold,
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    colors: TextFieldColors = LeonTheme.colors.primaryOutlinedTextField,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = error != null,
        shape = LeonTheme.shapes.allRounded,
        textStyle = TextStyle(fontWeight = FontWeight.Bold),
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        colors = colors,
    )
}

@Composable
private fun InfoCard(
    data: RatingUiData.Info,
) {
    LeonCard(
        text = buildAnnotatedString {
            append(data.text)

            withLeonLink(
                url = data.link,
                size = LeonTextSize.Body1,
                weight = LeonTextWeight.Bold,
                color = MaterialTheme.colorScheme.surfaceTint,
            ) {
                append(data.linkText)
            }
        },
        type = LeonCardType.Info,
    )
}