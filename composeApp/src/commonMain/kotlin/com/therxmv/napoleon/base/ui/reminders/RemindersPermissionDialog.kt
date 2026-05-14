package com.therxmv.napoleon.base.ui.reminders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Bell
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.reminders_dialog_deny
import napoleon.leonres.generated.resources.reminders_dialog_description
import napoleon.leonres.generated.resources.reminders_dialog_grant
import napoleon.leonres.generated.resources.reminders_dialog_title
import org.jetbrains.compose.resources.stringResource

// Outdated/Deprecated
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersPermissionDialog(
    isVisible: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
        ) {
            Surface(
                shape = MaterialTheme.shapes.large
            ) {
                RemindersPermissionContent(
                    onClick = onClick,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}

@Composable
private fun RemindersPermissionContent(
    onClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = FeatherIcons.Bell,
            contentDescription = "reminder",
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(Res.string.reminders_dialog_title),
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(Res.string.reminders_dialog_description))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.End),
        ) {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(stringResource(Res.string.reminders_dialog_deny))
            }

            Spacer(modifier = Modifier.width(8.dp))

            TextButton(
                onClick = {
                    onClick()
                    onDismiss()
                },
            ) {
                Text(stringResource(Res.string.reminders_dialog_grant))
            }
        }
    }
}