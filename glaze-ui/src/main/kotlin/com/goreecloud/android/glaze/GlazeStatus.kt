package com.goreecloud.android.glaze

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

enum class GlazeStatus {
    NORMAL,
    INFO,
    SUCCESS,
    WARNING,
    ERROR,
    DISABLED,
    UNKNOWN,
}

private data class GlazeStatusColors(
    val container: Color,
    val content: Color,
)

@Composable
private fun colorsFor(status: GlazeStatus): GlazeStatusColors = when (status) {
    GlazeStatus.NORMAL -> GlazeStatusColors(
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.onSurfaceVariant,
    )
    GlazeStatus.INFO -> GlazeStatusColors(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onPrimaryContainer,
    )
    GlazeStatus.SUCCESS -> GlazeStatusColors(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.onSecondaryContainer,
    )
    GlazeStatus.WARNING -> GlazeStatusColors(
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.onTertiaryContainer,
    )
    GlazeStatus.ERROR -> GlazeStatusColors(
        MaterialTheme.colorScheme.errorContainer,
        MaterialTheme.colorScheme.onErrorContainer,
    )
    GlazeStatus.DISABLED,
    GlazeStatus.UNKNOWN,
    -> GlazeStatusColors(
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun GlazeStatusChip(
    label: String,
    status: GlazeStatus,
    modifier: Modifier = Modifier,
) {
    val colors = colorsFor(status)
    Surface(
        modifier = modifier.semantics {
            stateDescription = status.name.lowercase()
        },
        color = colors.container,
        contentColor = colors.content,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
