package jp.cordea.urldispatcher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.FabShape

@Composable
fun NewLinkFab(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    val onColor = MaterialTheme.colorScheme.onPrimary
    Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                    .shadow(elevation = 12.dp, shape = FabShape, clip = false)
                    .background(color, FabShape)
                    .clickable(onClick = onClick)
                    .semantics { role = Role.Button }
                    .height(60.dp)
                    .padding(horizontal = 24.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides onColor) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            Text(
                    text = stringResource(R.string.home_new_link),
                    style = MaterialTheme.typography.labelLarge,
                    color = onColor,
                    modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
