package com.therxmv.napoleon.navigation.fullscreen

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.ui.editprofile.EditProfileScreen
import com.therxmv.napoleon.ui.exam.ExamsScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun FullScreenContent(
    modifier: Modifier = Modifier,
    child: Child.Full,
) {
    when (child) {
        is Child.Full.EditProfile -> {
            EditProfileScreen(
                modifier = modifier,
                component = child.component,
            )
        }

        is Child.Full.Exams -> {
            ExamsScreen(
                modifier = modifier,
                component = child.component,
            )
        }

        is Child.Full.Rating -> {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCenterAppBar(
    data: FullScreenComponent.Data,
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
                IconButton(
                    onClick = data.goBack,
                ) {
                    Icon(
                        imageVector = FeatherIcons.ArrowLeft,
                        contentDescription = "back",
                    )
                }
            }
        },
    )
}