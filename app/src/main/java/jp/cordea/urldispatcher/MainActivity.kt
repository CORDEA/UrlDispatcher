package jp.cordea.urldispatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import jp.cordea.urldispatcher.ui.navigation.UrlDispatcherNavHost
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UrlDispatcherTheme {
                UrlDispatcherNavHost()
            }
        }
    }
}
