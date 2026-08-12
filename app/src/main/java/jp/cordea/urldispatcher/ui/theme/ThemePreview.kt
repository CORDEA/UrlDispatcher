package jp.cordea.urldispatcher.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, backgroundColor = 0xFFECEAE6L)
@Composable
private fun ColorPalettePreview() {
    UrlDispatcherTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Swatch("Bone", Bone)
                Swatch("Sand", Sand)
                Swatch("Card", Card)
                Swatch("StoneLight", StoneLight)
                Swatch("Stone", Stone)
                Swatch("StoneMid", StoneMid)
                Swatch("InkSubtle", InkSubtle)
                Swatch("InkMuted", InkMuted)
                Swatch("Ink", Ink)
                Swatch("Indigo", Indigo)
                Swatch("IndigoSurface", IndigoSurface)
            }
        }
    }
}

@Composable
private fun Swatch(name: String, color: Color) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Box(
                Modifier
                        .size(32.dp)
                        .background(color, RoundedCornerShape(6.dp))
                        .border(1.dp, Stone, RoundedCornerShape(6.dp))
        )
        Text(text = name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 12.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFECEAE6L, heightDp = 640)
@Composable
private fun TypographyPreview() {
    UrlDispatcherTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("URL Dispatcher", style = MaterialTheme.typography.displayLarge)
                Text("Links", style = MaterialTheme.typography.displaySmall)
                Text("New link", style = MaterialTheme.typography.titleLarge)
                Text("Open-source licenses", style = MaterialTheme.typography.titleMedium)
                Text("Body large — description text.", style = MaterialTheme.typography.bodyLarge)
                Text("Body medium — secondary text.", style = MaterialTheme.typography.bodyMedium)
                Text("SAVE LINK", style = MaterialTheme.typography.labelLarge)
                Text("All · 4", style = MaterialTheme.typography.labelMedium)
                Text("Aug 4, 21:14", style = MaterialTheme.typography.labelSmall)
                Text("URL", style = MonoLabelMedium, color = MaterialTheme.colorScheme.primary)
                Text("HTTPS", style = MonoLabelSmall, color = MaterialTheme.colorScheme.primary)
                Text("myapp://product/1204", style = MonoBody)
                Text("2.0.0 (140)", style = MonoMeta, color = AppTheme.extended.stoneMid)
            }
        }
    }
}
