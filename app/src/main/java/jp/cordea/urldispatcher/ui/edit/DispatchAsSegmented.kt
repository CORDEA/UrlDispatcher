package jp.cordea.urldispatcher.ui.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.AppTheme

@Composable
fun DispatchAsSegmented(
        selected: DispatchType,
        onSelect: (DispatchType) -> Unit,
        modifier: Modifier = Modifier
) {
    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DispatchType.entries.forEach { type ->
            Segment(
                    labelRes = type.labelRes(),
                    selected = type == selected,
                    onClick = { onSelect(type) },
                    modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun Segment(
        @StringRes labelRes: Int,
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    val bg = if (selected) MaterialTheme.colorScheme.inverseSurface else Color.Transparent
    val fg = if (selected) MaterialTheme.colorScheme.inverseOnSurface else AppTheme.extended.inkMuted
    Box(
            modifier = modifier
                    .background(bg, shape)
                    .then(
                            if (selected) Modifier
                            else Modifier.border(1.dp, MaterialTheme.colorScheme.outline, shape)
                    )
                    .clickable(onClick = onClick)
                    .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = stringResource(labelRes),
                style = MaterialTheme.typography.labelMedium,
                color = fg
        )
    }
}

@StringRes
private fun DispatchType.labelRes(): Int = when (this) {
    DispatchType.DEFAULT -> R.string.edit_dispatch_default
    DispatchType.BROWSER -> R.string.edit_dispatch_browser
    DispatchType.CHOOSER -> R.string.edit_dispatch_chooser
}
