package jp.cordea.urldispatcher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme

@Composable
fun HomeScreen(
        viewModel: HomeViewModel,
        onNewLinkClick: () -> Unit,
        onEditLink: (Long) -> Unit,
        onOpenSettings: () -> Unit,
        onLinkOpen: (HomeLinkItem) -> Unit,
        modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.OpenLink -> onLinkOpen(event.item)
            }
        }
    }
    HomeScaffold(
            state = state,
            onSelectScheme = viewModel::selectScheme,
            onItemClick = viewModel::onItemClick,
            onItemEdit = onEditLink,
            onItemDelete = viewModel::deleteLink,
            onNewLinkClick = onNewLinkClick,
            onOpenSettings = onOpenSettings,
            modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScaffold(
        state: HomeUiState,
        onSelectScheme: (String?) -> Unit,
        onItemClick: (HomeLinkItem) -> Unit,
        onItemEdit: (Long) -> Unit,
        onItemDelete: (Long) -> Unit,
        onNewLinkClick: () -> Unit,
        onOpenSettings: () -> Unit,
        modifier: Modifier = Modifier
) {
    var sheetTargetId by remember { mutableStateOf<Long?>(null) }

    Box(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(Modifier.fillMaxSize()) {
            HomeHeader(
                    onSearchClick = { /* out of scope in v1 */ },
                    onOverflowClick = onOpenSettings,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 26.dp)
            )
            FilterChipsRow(
                    schemes = state.schemes,
                    selected = state.selectedScheme,
                    totalCount = state.totalCount,
                    onSelect = onSelectScheme,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
            )
            LazyColumn(
                    modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 14.dp),
                    contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = state.items, key = { it.id }) { item ->
                    LinkCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            onLongClick = { sheetTargetId = item.id }
                    )
                }
            }
        }
        NewLinkFab(
                onClick = onNewLinkClick,
                modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 22.dp, bottom = 24.dp)
        )
    }

    sheetTargetId?.let { targetId ->
        LinkActionsSheet(
                onEditClick = {
                    val id = targetId
                    sheetTargetId = null
                    onItemEdit(id)
                },
                onDeleteClick = {
                    val id = targetId
                    sheetTargetId = null
                    onItemDelete(id)
                },
                onDismiss = { sheetTargetId = null }
        )
    }
}

@Composable
private fun HomeHeader(
        onSearchClick: () -> Unit,
        onOverflowClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
        )
        Box(Modifier.weight(1f))
        CircleIconButton(
                icon = Icons.Filled.Search,
                contentDescription = stringResource(R.string.home_action_search),
                onClick = onSearchClick
        )
        CircleIconButton(
                icon = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.home_action_settings),
                onClick = onOverflowClick,
                modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun CircleIconButton(
        icon: androidx.compose.ui.graphics.vector.ImageVector,
        contentDescription: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    IconButton(
            onClick = onClick,
            modifier = modifier
                    .size(40.dp)
                    .semantics { this.contentDescription = contentDescription },
            colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = AppTheme.extended.inkMuted
            )
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5L, heightDp = 780)
@Composable
private fun HomeScreenPreview() {
    val sample = HomeUiState(
            isLoading = false,
            items = listOf(
                    HomeLinkItem(1L, "https", "https://example.com/products/1204?ref=push",
                            "Universal link — product detail", "Aug 4, 21:14",
                            android.net.Uri.parse("https://example.com"), DispatchType.DEFAULT),
                    HomeLinkItem(2L, "myapp", "myapp://product/1204",
                            "Custom scheme fallback", "Aug 4, 21:13",
                            android.net.Uri.parse("myapp://product/1204"), DispatchType.DEFAULT),
                    HomeLinkItem(3L, "https", "https://example.com",
                            "Simple browse case", "Aug 4, 21:13",
                            android.net.Uri.parse("https://example.com"), DispatchType.DEFAULT),
                    HomeLinkItem(4L, "intent", "intent://scan/#Intent;scheme=zxing;end",
                            "Intent URI — scanner", "Aug 3, 18:02",
                            android.net.Uri.parse("intent://scan"), DispatchType.CHOOSER)
            ),
            schemes = listOf("https", "intent", "myapp"),
            selectedScheme = null,
            totalCount = 4
    )
    UrlDispatcherTheme {
        HomeScaffold(
                state = sample,
                onSelectScheme = {},
                onItemClick = {},
                onItemEdit = {},
                onItemDelete = {},
                onNewLinkClick = {},
                onOpenSettings = {}
        )
    }
}
