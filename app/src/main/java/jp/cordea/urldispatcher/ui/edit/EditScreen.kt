package jp.cordea.urldispatcher.ui.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.ButtonShape
import jp.cordea.urldispatcher.ui.theme.MonoBody
import jp.cordea.urldispatcher.ui.theme.MonoLabelMedium
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme

@Composable
fun EditScreen(
        viewModel: EditViewModel,
        onFinished: () -> Unit,
        modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                EditEvent.Saved -> onFinished()
                is EditEvent.Error -> {
                    val messageRes = when (event.kind) {
                        EditError.EMPTY_URL -> R.string.url_empty_error_title
                        EditError.SAVE_FAILED -> R.string.failed_to_save_url_error_title
                    }
                    Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    EditScaffold(
            state = state,
            onBack = onFinished,
            onUrlChange = viewModel::onUrlChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onDispatchTypeChange = viewModel::onDispatchTypeChange,
            onSave = viewModel::save,
            modifier = modifier
    )
}

@Composable
internal fun EditScaffold(
        state: EditUiState,
        onBack: () -> Unit,
        onUrlChange: (String) -> Unit,
        onDescriptionChange: (String) -> Unit,
        onDispatchTypeChange: (DispatchType) -> Unit,
        onSave: () -> Unit,
        modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        EditTopBar(isEditMode = state.isEditMode, onBack = onBack)
        Column(
                modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(top = 18.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            LabeledTextField(
                    label = stringResource(R.string.edit_label_url),
                    value = state.url,
                    onValueChange = onUrlChange,
                    textStyle = MonoBody,
                    keyboardType = KeyboardType.Uri
            )
            LabeledTextField(
                    label = stringResource(R.string.edit_label_description),
                    value = state.description,
                    onValueChange = onDescriptionChange,
                    placeholder = stringResource(R.string.edit_placeholder_description)
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                        text = stringResource(R.string.edit_label_dispatch_as),
                        style = MonoLabelMedium,
                        color = AppTheme.extended.stoneMid
                )
                DispatchAsSegmented(
                        selected = state.dispatchType,
                        onSelect = onDispatchTypeChange
                )
            }
        }
        SaveLinkButton(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 14.dp)
        )
    }
}

@Composable
private fun EditTopBar(isEditMode: Boolean, onBack: () -> Unit) {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp),
                colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = AppTheme.extended.inkMuted
                )
        ) {
            Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.edit_action_back)
            )
        }
        Text(
                text = stringResource(
                        if (isEditMode) R.string.edit_title_edit else R.string.edit_title_new
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 14.dp)
        )
    }
}

@Composable
private fun SaveLinkButton(
        onClick: () -> Unit,
        enabled: Boolean,
        modifier: Modifier = Modifier
) {
    val bg = MaterialTheme.colorScheme.primary
    val fg = MaterialTheme.colorScheme.onPrimary
    Box(
            modifier = modifier
                    .height(56.dp)
                    .background(bg, ButtonShape)
                    .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = stringResource(R.string.edit_save),
                style = MaterialTheme.typography.labelLarge,
                color = fg
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5L, heightDp = 780)
@Composable
private fun EditScreenPreview() {
    UrlDispatcherTheme {
        EditScaffold(
                state = EditUiState(
                        isEditMode = false,
                        url = "myapp://product/1204",
                        description = "",
                        dispatchType = DispatchType.DEFAULT
                ),
                onBack = {},
                onUrlChange = {},
                onDescriptionChange = {},
                onDispatchTypeChange = {},
                onSave = {}
        )
    }
}
