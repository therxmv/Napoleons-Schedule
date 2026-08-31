package com.therxmv.napoleon.navigation.fullscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.ui.editprofile.EditProfileScreen
import com.therxmv.napoleon.ui.exam.ExamsScreen
import com.therxmv.napoleon.ui.rating.RatingScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun FullScreenContent(
    paddingValues: PaddingValues,
    child: Child.Full,
) {
    when (child) {
        is Child.Full.EditProfile -> {
            EditProfileScreen(
                modifier = Modifier.padding(paddingValues),
                component = child.component,
            )
        }

        is Child.Full.Exams -> {
            ExamsScreen(
                modifier = Modifier.padding(paddingValues),
                component = child.component,
            )
        }

        is Child.Full.Rating -> {
            RatingScreen(
                paddingValues = paddingValues,
                component = child.component,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCenterAppBar(
    data: FullScreenComponent.Data,
    windowInsets: WindowInsets,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        navigationIcon = {
            if (data.canGoBack()) {
                LeonIconButton(
                    icon = FeatherIcons.ArrowLeft,
                    onClick = data.goBack,
                )
            }
        },
        windowInsets = windowInsets,
    )
}

@Preview
@Composable
private fun TopCenterAppBarPreview() {
    LeonPreview {
        TopCenterAppBar(
            data = FullScreenComponent.Data(
                title = "TopCenterAppBar",
                canGoBack = { true },
                goBack = {},
            ),
            windowInsets = WindowInsets.safeDrawing,
        )
    }
}