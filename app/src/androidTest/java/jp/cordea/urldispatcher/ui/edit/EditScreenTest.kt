package jp.cordea.urldispatcher.ui.edit

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme
import org.junit.Rule
import org.junit.Test

class EditScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun createMode_showsNewTitleAndBlankFields() {
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(isEditMode = false),
                        onBack = {},
                        onUrlChange = {},
                        onDescriptionChange = {},
                        onDispatchTypeChange = {},
                        onSave = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.edit_title_new)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.edit_placeholder_description))
                .assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.edit_save)).assertIsDisplayed()
    }

    @Test
    fun editMode_showsEditTitleAndSeedValues() {
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(
                                isEditMode = true,
                                url = "https://example.com",
                                description = "seed",
                                dispatchType = DispatchType.BROWSER
                        ),
                        onBack = {},
                        onUrlChange = {},
                        onDescriptionChange = {},
                        onDispatchTypeChange = {},
                        onSave = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.edit_title_edit)).assertIsDisplayed()
        composeRule.onNodeWithText("https://example.com").assertIsDisplayed()
        composeRule.onNodeWithText("seed").assertIsDisplayed()
    }

    @Test
    fun urlInput_invokesCallback() {
        val captured = mutableListOf<String>()
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(),
                        onBack = {},
                        onUrlChange = { captured += it },
                        onDescriptionChange = {},
                        onDispatchTypeChange = {},
                        onSave = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.edit_label_url))
                .performClick()
        composeRule.onNodeWithText(context.getString(R.string.edit_label_url))
                .performTextInput("a")

        assertThat(captured).contains("a")
    }

    @Test
    fun segmentClick_selectsDispatchType() {
        var picked: DispatchType? = null
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(),
                        onBack = {},
                        onUrlChange = {},
                        onDescriptionChange = {},
                        onDispatchTypeChange = { picked = it },
                        onSave = {}
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.edit_dispatch_chooser))
                .performClick()

        assertThat(picked).isEqualTo(DispatchType.CHOOSER)
    }

    @Test
    fun saveClick_invokesCallback() {
        var saved = false
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(url = "https://example.com"),
                        onBack = {},
                        onUrlChange = {},
                        onDescriptionChange = {},
                        onDispatchTypeChange = {},
                        onSave = { saved = true }
                )
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.edit_save)).performClick()

        assertThat(saved).isTrue()
    }

    @Test
    fun backClick_invokesCallback() {
        var backed = false
        composeRule.setContent {
            UrlDispatcherTheme {
                EditScaffold(
                        state = EditUiState(),
                        onBack = { backed = true },
                        onUrlChange = {},
                        onDescriptionChange = {},
                        onDispatchTypeChange = {},
                        onSave = {}
                )
            }
        }

        composeRule.onNodeWithContentDescription(context.getString(R.string.edit_action_back))
                .performClick()

        assertThat(backed).isTrue()
    }
}
