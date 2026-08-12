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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
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
            onSearchActiveChange = viewModel::setSearchActive,
            onQueryChange = viewModel::onQueryChange,
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
        onSearchActiveChange: (Boolean) -> Unit,
        onQueryChange: (String) -> Unit,
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
                    isSearchActive = state.isSearchActive,
                    query = state.query,
                    onOpenSearch = { onSearchActiveChange(true) },
                    onCloseSearch = { onSearchActiveChange(false) },
                    onQueryChange = onQueryChange,
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
            if (state.items.isEmpty() && !state.isLoading && state.isFiltered) {
                EmptyResults(modifier = Modifier.padding(top = 32.dp))
            } else {
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
        isSearchActive: Boolean,
        query: String,
        onOpenSearch: () -> Unit,
        onCloseSearch: () -> Unit,
        onQueryChange: (String) -> Unit,
        onOverflowClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    if (isSearchActive) {
        SearchHeader(
                query = query,
                onQueryChange = onQueryChange,
                onClose = onCloseSearch,
                modifier = modifier
        )
    } else {
        IdleHeader(
                onSearchClick = onOpenSearch,
                onOverflowClick = onOverflowClick,
                modifier = modifier
        )
    }
}

@Composable
private fun IdleHeader(
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
private fun SearchHeader(
        query: String,
        onQueryChange: (String) -> Unit,
        onClose: () -> Unit,
        modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.home_action_search_close),
                onClick = onClose
        )
        val placeholder = stringResource(R.string.home_search_placeholder)
        BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { keyboard?.hide() }),
                modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                        .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                                text = placeholder,
                                style = MaterialTheme.typography.titleMedium,
                                color = AppTheme.extended.stoneMid
                        )
                    }
                    innerTextField()
                }
        )
    }
}

@Composable
private fun EmptyResults(modifier: Modifier = Modifier) {
    Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = stringResource(R.string.home_empty_no_matches),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CircleIconButton(
        icon: ImageVector,
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
                onSearchActiveChange = {},
                onQueryChange = {},
                onItemClick = {},
                onItemEdit = {},
                onItemDelete = {},
                onNewLinkClick = {},
                onOpenSettings = {}
        )
    }
}
