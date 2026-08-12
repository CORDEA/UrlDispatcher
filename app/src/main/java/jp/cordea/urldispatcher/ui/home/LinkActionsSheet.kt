package jp.cordea.urldispatcher.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkActionsSheet(
        sheetState: SheetState = rememberModalBottomSheetState(),
        onEditClick: () -> Unit,
        onDeleteClick: () -> Unit,
        onDismiss: () -> Unit
) {
    ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
    ) {
        Column(Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
            SheetRow(
                    icon = Icons.Outlined.Edit,
                    label = stringResource(R.string.bottom_sheet_edit),
                    onClick = onEditClick
            )
            SheetRow(
                    icon = Icons.Outlined.Delete,
                    label = stringResource(R.string.bottom_sheet_delete),
                    onClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun SheetRow(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp)
        )
    }
}
