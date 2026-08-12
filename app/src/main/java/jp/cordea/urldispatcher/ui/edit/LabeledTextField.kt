package jp.cordea.urldispatcher.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jp.cordea.urldispatcher.ui.theme.AppTheme
import jp.cordea.urldispatcher.ui.theme.MonoLabelMedium

@Composable
fun LabeledTextField(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        placeholder: String? = null,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        keyboardType: KeyboardType = KeyboardType.Text,
        singleLine: Boolean = true
) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)
    val focusedBorderColor = MaterialTheme.colorScheme.primary
    val restingBorderColor = MaterialTheme.colorScheme.outline
    val labelColor = if (focused) focusedBorderColor else AppTheme.extended.stoneMid

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = label, style = MonoLabelMedium, color = labelColor)
        BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(focusedBorderColor),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer, shape)
                        .border(
                                width = if (focused) 2.dp else 1.dp,
                                color = if (focused) focusedBorderColor else restingBorderColor,
                                shape = shape
                        )
                        .onFocusChanged { focused = it.isFocused }
                        .padding(16.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                                text = placeholder,
                                style = textStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                    innerTextField()
                }
        )
    }
}
