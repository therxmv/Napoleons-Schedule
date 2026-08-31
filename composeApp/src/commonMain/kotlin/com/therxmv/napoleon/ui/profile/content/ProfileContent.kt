package com.therxmv.napoleon.ui.profile.content

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.list.LeonDividerType
import com.therxmv.leonui.list.LeonHorizontalDivider
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.profile.component.ProfileUiData
import com.therxmv.napoleon.ui.profile.component.ProfileUiEvent
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit2
import compose.icons.feathericons.User
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    data: ProfileUiData,
    onEvent: (ProfileUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(LeonTheme.paddings.baseValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            UserAvatar()
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
        }

        item {
            SpecialtyInfoCard(
                data = data,
                onEditClick = {
                    onEvent(ProfileUiEvent.EditProfile)
                },
            )
        }
    }
}

@Composable
private fun SpecialtyInfoCard(
    data: ProfileUiData,
    onEditClick: () -> Unit,
) {
    CardTitle(
        title = stringResource(data.infoTitleRes),
        editLabel = stringResource(data.editButtonLabelRes),
        onEditClick = onEditClick,
    )

    InfoCard(
        titleText = stringResource(data.facultyLabelRes),
        nameText = data.faculty,
        shape = LeonTheme.shapes.onlyTopRounded(),
    )

    LeonHorizontalDivider(
        type = LeonDividerType.Full,
        color = LeonTheme.colors.surface,
    )

    InfoCard(
        titleText = stringResource(data.specialtyLabelRes),
        nameText = data.specialty,
        shape = LeonTheme.shapes.onlyBottomRounded(),
    )
}

@Composable
private fun InfoCard(
    titleText: String,
    nameText: String,
    shape: Shape,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(LeonTheme.colors.primary)
            .padding(16.dp)
    ) {
        LeonText(
            modifier = Modifier.weight(1f),
            text = titleText,
            size = LeonTextSize.Title2,
            color = LeonTheme.colors.onPrimary,
        )

        LeonText(
            text = nameText,
            size = LeonTextSize.Title2,
            color = LeonTheme.colors.onPrimary,
            weight = LeonTextWeight.Bold
        )
    }
}

@Composable
private fun CardTitle(
    title: String,
    editLabel: String,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LeonText(
            text = title,
            size = LeonTextSize.Title2,
        )

        Spacer(modifier = Modifier.weight(1f))

        LeonButton(
            style = LeonButtonStyle.Text(),
            label = editLabel,
            onClick = onEditClick,
            suffixIcon = FeatherIcons.Edit2,
        )
    }
}

@Composable
private fun UserAvatar() {
    val colors = listOf(
        LeonTheme.colors.tertiaryContainer,
        LeonTheme.colors.primaryContainer,
    )
    val scale = remember { Animatable(0.2f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
        )
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .scale(scale.value)
            .drawWithCache {
                onDrawBehind {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = colors,
                        ),
                    )
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(0.5f),
            imageVector = FeatherIcons.User,
            tint = LeonTheme.colors.onPrimaryContainer,
            contentDescription = "Avatar",
        )
    }
}

@LeonPreview
@Composable
private fun ProfileContentPreview() {
    LeonPreview {
        ProfileContent(
            data = PreviewMockData.profileUiData,
            onEvent = {},
        )
    }
}