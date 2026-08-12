package jp.cordea.urldispatcher.ui.home

import android.content.Context
import android.net.Uri
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private val sample = HomeUiState(
            isLoading = false,
            items = listOf(
                    HomeLinkItem(
                            id = 1L,
                            scheme = "https",
                            url = "https://example.com/a",
                            description = "Universal link",
                            date = "Aug 4, 21:14",
                            uri = Uri.parse("https://example.com/a"),
                            dispatchType = DispatchType.DEFAULT
                    ),
                    HomeLinkItem(
                            id = 2L,
                            scheme = "myapp",
                            url = "myapp://x",
                            description = "Custom scheme",
                            date = "Aug 4, 21:13",
                            uri = Uri.parse("myapp://x"),
                            dispatchType = DispatchType.DEFAULT
                    )
            ),
            schemes = listOf("https", "myapp"),
            selectedScheme = null,
            totalCount = 2
    )

    @Test
    fun rendersUrlsAndAllChipWithCount() {
        composeRule.setContent {
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

        composeRule.onNodeWithText(context.getString(R.string.home_title)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.home_filter_all, 2)).assertIsDisplayed()
        composeRule.onNodeWithText("https://example.com/a").assertIsDisplayed()
        composeRule.onNodeWithText("myapp://x").assertIsDisplayed()
    }

    @Test
    fun fabClickInvokesCallback() {
        var clicked = false
        composeRule.setContent {
            UrlDispatcherTheme {
                HomeScaffold(
                        state = sample,
                        onSelectScheme = {},
                        onItemClick = {},
                        onItemEdit = {},
                        onItemDelete = {},
                        onNewLinkClick = { clicked = true },
                        onOpenSettings = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.home_new_link)).performClick()

        assertThat(clicked).isTrue()
    }

    @Test
    fun overflowClickOpensSettings() {
        var opened = false
        composeRule.setContent {
            UrlDispatcherTheme {
                HomeScaffold(
                        state = sample,
                        onSelectScheme = {},
                        onItemClick = {},
                        onItemEdit = {},
                        onItemDelete = {},
                        onNewLinkClick = {},
                        onOpenSettings = { opened = true }
                )
            }
        }

        composeRule.onNodeWithContentDescription(
                context.getString(R.string.home_action_settings)
        ).performClick()

        assertThat(opened).isTrue()
    }

    @Test
    fun schemeChipClickFiltersItems() {
        var lastSelection: String? = "unset"
        composeRule.setContent {
            UrlDispatcherTheme {
                HomeScaffold(
                        state = sample,
                        onSelectScheme = { lastSelection = it },
                        onItemClick = {},
                        onItemEdit = {},
                        onItemDelete = {},
                        onNewLinkClick = {},
                        onOpenSettings = {}
                )
            }
        }

        composeRule.onNodeWithText("myapp").performClick()

        assertThat(lastSelection).isEqualTo("myapp")
    }
}
