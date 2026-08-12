package jp.cordea.urldispatcher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.PillShape

@Composable
fun FilterChipsRow(
        schemes: List<String>,
        selected: String?,
        totalCount: Int,
        onSelect: (String?) -> Unit,
        modifier: Modifier = Modifier
) {
    LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        item(key = "__all__") {
            SchemeChip(
                    label = stringResource(R.string.home_filter_all, totalCount),
                    selected = selected == null,
                    onClick = { onSelect(null) }
            )
        }
        items(items = schemes, key = { it }) { scheme ->
            SchemeChip(
                    label = scheme,
                    selected = selected == scheme,
                    onClick = { onSelect(scheme) }
            )
        }
    }
}

@Composable
private fun SchemeChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) MaterialTheme.colorScheme.inverseSurface else Color.Transparent
    val fg = if (selected) MaterialTheme.colorScheme.inverseOnSurface else AppTheme.extended.inkMuted
    Row(
            modifier = Modifier
                    .background(bg, PillShape)
                    .then(
                            if (selected) Modifier
                            else Modifier.border(1.dp, MaterialTheme.colorScheme.outline, PillShape)
                    )
                    .clickable(onClick = onClick)
                    .padding(horizontal = 16.dp, vertical = 9.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = fg)
    }
}
