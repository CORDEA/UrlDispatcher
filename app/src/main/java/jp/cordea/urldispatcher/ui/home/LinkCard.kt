package jp.cordea.urldispatcher.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.MonoBody
import jp.cordea.urldispatcher.ui.theme.MonoLabelSmall
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinkCard(
        item: HomeLinkItem,
        onClick: () -> Unit,
        onLongClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(20.dp)
    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .shadow(elevation = 1.dp, shape = cardShape, clip = false)
                    .background(MaterialTheme.colorScheme.surfaceContainer, cardShape)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, cardShape)
                    .combinedClickable(
                            role = Role.Button,
                            onClick = onClick,
                            onLongClick = onLongClick
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SchemeBadge(scheme = item.scheme)
            Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.extended.stoneMid,
                    modifier = Modifier.padding(start = 8.dp)
            )
            Box(Modifier.weight(1f))
            Text(
                    text = "›",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.extended.chevron
            )
        }
        Text(
                text = item.url,
                style = MonoBody,
                color = MaterialTheme.colorScheme.onSurface
        )
        Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SchemeBadge(scheme: String) {
    Text(
            text = scheme.uppercase(Locale.ROOT),
            style = MonoLabelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(7.dp))
                    .padding(horizontal = 9.dp, vertical = 4.dp)
    )
}
