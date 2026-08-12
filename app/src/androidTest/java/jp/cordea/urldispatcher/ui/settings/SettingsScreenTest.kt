package jp.cordea.urldispatcher.ui.settings

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.ui.theme.UrlDispatcherTheme
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private val sample = SettingsUiState(
            licenseCount = 12,
            versionName = "2.0.0",
            versionCode = 140
    )

    @Test
    fun rendersTitleLicenseCountAndVersion() {
        composeRule.setContent {
            UrlDispatcherTheme {
                SettingsScaffold(state = sample, onBack = {}, onOpenLicenses = {})
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.settings_title)).assertIsDisplayed()
        composeRule.onNodeWithText(
                context.resources.getQuantityString(R.plurals.settings_licenses_subtitle, 12, 12)
        ).assertIsDisplayed()
        composeRule.onNodeWithText(
                context.getString(R.string.settings_version_value, "2.0.0", 140)
        ).assertIsDisplayed()
    }

    @Test
    fun licenseRowClickInvokesCallback() {
        var opened = false
        composeRule.setContent {
            UrlDispatcherTheme {
                SettingsScaffold(state = sample, onBack = {}, onOpenLicenses = { opened = true })
            }
        }

        composeRule.onNodeWithText(context.getString(R.string.settings_licenses_title))
                .performClick()

        assertThat(opened).isTrue()
    }

    @Test
    fun backClickInvokesCallback() {
        var backed = false
        composeRule.setContent {
            UrlDispatcherTheme {
                SettingsScaffold(state = sample, onBack = { backed = true }, onOpenLicenses = {})
            }
        }

        composeRule.onNodeWithContentDescription(context.getString(R.string.settings_action_back))
                .performClick()

        assertThat(backed).isTrue()
    }

    @Test
    fun singleLicense_showsSingularCopy() {
        composeRule.setContent {
            UrlDispatcherTheme {
                SettingsScaffold(
                        state = sample.copy(licenseCount = 1),
                        onBack = {},
                        onOpenLicenses = {}
                )
            }
        }

        composeRule.onNodeWithText(
                context.resources.getQuantityString(R.plurals.settings_licenses_subtitle, 1, 1)
        ).assertIsDisplayed()
    }
}
