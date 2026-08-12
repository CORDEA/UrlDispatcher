package jp.cordea.urldispatcher.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
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
                    .clip(cardShape)
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
            Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Badge(
                        text = item.scheme.uppercase(Locale.ROOT),
                        background = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f, fill = false)
                )
                DispatchTypeBadge(dispatchType = item.dispatchType)
                Text(
                        text = item.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.extended.stoneMid,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                    text = "›",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.extended.chevron,
                    modifier = Modifier.padding(start = 8.dp)
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
private fun DispatchTypeBadge(dispatchType: DispatchType) {
    Badge(
            text = stringResource(dispatchType.badgeLabelRes()).uppercase(Locale.ROOT),
            background = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = AppTheme.extended.inkMuted
    )
}

@Composable
private fun Badge(
        text: String,
        background: Color,
        contentColor: Color,
        modifier: Modifier = Modifier
) {
    Text(
            text = text,
            style = MonoLabelSmall,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                    .background(background, RoundedCornerShape(7.dp))
                    .padding(horizontal = 9.dp, vertical = 4.dp)
    )
}

@StringRes
private fun DispatchType.badgeLabelRes(): Int = when (this) {
    DispatchType.DEFAULT -> R.string.dispatch_type_default
    DispatchType.CHOOSER -> R.string.dispatch_type_chooser
}
