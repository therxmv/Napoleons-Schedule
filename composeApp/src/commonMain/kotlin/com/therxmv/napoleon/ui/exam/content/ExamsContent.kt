package com.therxmv.napoleon.ui.exam.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.therxmv.leonui.card.LeonCard
import com.therxmv.leonui.card.LeonCardType
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.ui.exam.component.ExamsUiData

@Composable
fun ExamsContent(
    modifier: Modifier = Modifier,
    data: ExamsUiData,
    fallbackReason: String?,
) {
    LazyColumn(
        modifier = modifier
            .padding(LeonTheme.paddings.baseValues),
        verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.base),
    ) {
        if (fallbackReason != null) {
            item {
                LeonCard(
                    text = fallbackReason,
                    type = LeonCardType.Error,
                )
            }
        }

        items(data.items) {
            when (it) {
                is ExamsUiData.Item.Title -> TitleItem(it.title)

                is ExamsUiData.Item.Exam -> ExamItem(it)

                is ExamsUiData.Item.Zalik -> ZalikItem(it)

                is ExamsUiData.Item.EmptyPlaceholder -> EmptyPlaceholderItem(it.text)
            }
        }
    }
}

@Composable
private fun EmptyPlaceholderItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = LeonTheme.colors.onSurface,
    )
}

@Composable
private fun TitleItem(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun ExamItem(
    data: ExamsUiData.Item.Exam,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = data.date,
            style = MaterialTheme.typography.headlineSmall,
            color = LeonTheme.colors.surfaceTint,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.base))

        Column {
            Text(
                text = data.lesson,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = data.teacher,
                style = MaterialTheme.typography.titleSmall,
                color = LeonTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ZalikItem(data: ExamsUiData.Item.Zalik) {
    Text(
        text = data.lesson,
        style = MaterialTheme.typography.titleMedium,
    )
}