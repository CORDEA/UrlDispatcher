package jp.cordea.urldispatcher

import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isFocusable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xwray.groupie.GroupieViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScenarioTest {
    @Before
    fun setUp() = Intents.init()

    @After
    fun tearDown() = Intents.release()

    private fun isUrlTextInput(): Matcher<View> =
        allOf(isDescendantOfA(withId(R.id.url)), isDisplayed(), isFocusable())

    private fun isDescriptionTextInput(): Matcher<View> =
        allOf(isDescendantOfA(withId(R.id.description)), isDisplayed(), isFocusable())

    @Test
    fun save() {
        val url = "http://example.com"
        val description = "description"

        ActivityScenario.launch(MainActivity::class.java)

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_main)))
            .check(matches(isDisplayed()))

        // Add item
        onView(withId(R.id.fab)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_add)))
            .check(matches(isDisplayed()))

        onView(isUrlTextInput())
            .perform(typeText(url))
        onView(isDescriptionTextInput())
            .perform(typeText(description))
        closeSoftKeyboard()

        onView(withId(R.id.fab)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_main)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
            .check(matches(withText(0, R.id.title, url)))
            .check(matches(withText(0, R.id.description, description)))

        onView(withId(R.id.recycler_view))
            .check(matches(isSize(1)))

        // Add item
        onView(withId(R.id.fab)).perform(click())

        onView(isUrlTextInput())
            .perform(typeText("$url/3"))
        onView(isDescriptionTextInput())
            .perform(typeText("$description/3"))
        closeSoftKeyboard()

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
            .check(matches(isSize(2)))

        // Edit item
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<GroupieViewHolder>(0, longClick()))

        onView(withId(R.id.edit)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_add)))
            .check(matches(isDisplayed()))

        onView(isUrlTextInput())
            .check(matches(withText(url)))
        onView(isDescriptionTextInput())
            .check(matches(withText(description)))

        onView(isUrlTextInput())
            .perform(clearText())
            .perform(typeText("$url/2"))
        onView(isDescriptionTextInput())
            .perform(clearText())
            .perform(typeText("$description/2"))
        closeSoftKeyboard()

        onView(withId(R.id.fab)).perform(click())

        // Add item
        onView(withId(R.id.fab)).perform(click())

        onView(isUrlTextInput())
            .perform(typeText("$url/4"))
        onView(isDescriptionTextInput())
            .perform(typeText("$description/4"))
        closeSoftKeyboard()

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
            .check(matches(isSize(3)))

        // Delete item
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<GroupieViewHolder>(2, longClick()))

        onView(withId(R.id.delete)).perform(click())

        onView(withId(R.id.recycler_view))
            .check(matches(isSize(2)))

        // Add same url item
        onView(withId(R.id.fab)).perform(click())

        onView(isUrlTextInput())
            .perform(typeText("$url/3"))
        onView(isDescriptionTextInput())
            .perform(typeText("$description/5"))
        closeSoftKeyboard()

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
            .check(matches(isSize(2)))

        onView(withId(R.id.recycler_view))
            .check(matches(withText(1, R.id.title, "$url/3")))
            .check(matches(withText(1, R.id.description, "$description/5")))

        // Open url
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<GroupieViewHolder>(0, click()))

        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("$url/2".toUri())))
    }
}
