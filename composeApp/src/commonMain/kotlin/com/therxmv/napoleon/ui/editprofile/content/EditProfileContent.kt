package com.therxmv.napoleon.ui.editprofile.content

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.input.LeonDropdownInput
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiData
import com.therxmv.napoleon.ui.editprofile.component.EditProfileUiEvent
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.fallback_offline
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    data: EditProfileUiData,
    fallbackReasonRes: StringResource?,
    onEvent: (EditProfileUiEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(LeonTheme.paddings.baseValues)
            .focusable(), // Didn't work without focusable on API 26,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (fallbackReasonRes != null) {
            LeonCard(
                textRes = fallbackReasonRes,
                type = LeonCardType.Error,
            )
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))
        }

        LeonDropdownInput(data = data.facultyDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))

        LeonDropdownInput(data = data.yearDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))

        LeonDropdownInput(data = data.specialtyDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.baggy))

        LeonButton(
            label = stringResource(data.saveLabelRes),
            isEnabled = data.isAllSelected,
            onClick = {
                onEvent(EditProfileUiEvent.SaveProfile)
            },
        )
    }
}

@LeonPreview
@Composable
private fun EditProfileContentPreview() {
    LeonPreview {
        EditProfileContent(
            data = PreviewMockData.editProfileUiData,
            fallbackReasonRes = Res.string.fallback_offline,
            onEvent = {},
        )
    }
}