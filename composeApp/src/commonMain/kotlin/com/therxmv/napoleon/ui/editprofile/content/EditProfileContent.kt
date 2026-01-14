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

@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    data: EditProfileUiData,
    fallbackReason: String?,
    onEvent: (EditProfileUiEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(LeonTheme.paddings.defaultValues)
            .focusable(), // Didn't work without focusable on API 26,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (fallbackReason != null) {
            LeonCard(
                text = fallbackReason,
                type = LeonCardType.Error,
            )
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))
        }

        LeonDropdownInput(data = data.facultyDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))

        LeonDropdownInput(data = data.yearDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical))

        LeonDropdownInput(data = data.specialtyDropdown)
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.times(2)))

        LeonButton(
            label = data.saveLabel,
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
            fallbackReason = "Fallback Reason",
            onEvent = {},
        )
    }
}