package jp.cordea.urldispatcher.ui.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.MonoLabelMedium
import jp.cordea.urldispatcher.ui.theme.MonoMeta
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme

@Composable
fun SettingsScreen(
        viewModel: SettingsViewModel,
        onBack: () -> Unit,
        modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    SettingsScaffold(
            state = state,
            onBack = onBack,
            onOpenLicenses = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            },
            modifier = modifier
    )
}

@Composable
internal fun SettingsScaffold(
        state: SettingsUiState,
        onBack: () -> Unit,
        onOpenLicenses: () -> Unit,
        modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        SettingsTopBar(onBack = onBack)
        Column(
                modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                    text = stringResource(R.string.settings_section_about),
                    style = MonoLabelMedium,
                    color = AppTheme.extended.stoneMid
            )
            AboutCard(
                    versionName = state.versionName,
                    versionCode = state.versionCode,
                    onOpenLicenses = onOpenLicenses
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun SettingsTopBar(onBack: () -> Unit) {
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
                    contentDescription = stringResource(R.string.settings_action_back)
            )
        }
        Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 14.dp)
        )
    }
}

@Composable
private fun AboutCard(
        versionName: String,
        versionCode: Int,
        onOpenLicenses: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surfaceContainer, shape)
    ) {
        LicensesRow(onClick = onOpenLicenses)
        HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
        )
        VersionRow(
                versionName = versionName,
                versionCode = versionCode
        )
    }
}

@Composable
private fun LicensesRow(onClick: () -> Unit) {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = stringResource(R.string.settings_licenses_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
        )
        Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = AppTheme.extended.chevron
        )
    }
}

@Composable
private fun VersionRow(versionName: String, versionCode: Int) {
    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = stringResource(R.string.settings_version_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
        )
        Text(
                text = stringResource(R.string.settings_version_value, versionName, versionCode),
                style = MonoMeta,
                color = AppTheme.extended.stoneMid
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5L, heightDp = 780)
@Composable
private fun SettingsScreenPreview() {
    UrlDispatcherTheme {
        SettingsScaffold(
                state = SettingsUiState(
                        versionName = "2.0.0",
                        versionCode = 140
                ),
                onBack = {},
                onOpenLicenses = {}
        )
    }
}
