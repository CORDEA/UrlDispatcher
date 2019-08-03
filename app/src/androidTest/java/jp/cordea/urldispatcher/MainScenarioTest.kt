package jp.cordea.urldispatcher

import android.content.Intent
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xwray.groupie.ViewHolder
import org.hamcrest.CoreMatchers.allOf
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

        onView(withParent(withParent(withId(R.id.url))))
                .perform(typeText(url))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(typeText(description))

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

        onView(withParent(withParent(withId(R.id.url))))
                .perform(typeText("$url/3"))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(typeText("$description/3"))

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
                .check(matches(isSize(2)))

        // Edit item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        onView(withId(R.id.edit)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_add)))
                .check(matches(isDisplayed()))

        onView(withParent(withParent(withId(R.id.url))))
                .check(matches(withText(url)))
        onView(withParent(withParent(withId(R.id.description))))
                .check(matches(withText(description)))

        onView(withParent(withParent(withId(R.id.url))))
                .perform(clearText())
                .perform(typeText("$url/2"))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(clearText())
                .perform(typeText("$description/2"))

        onView(withId(R.id.fab)).perform(click())

        // Add item
        onView(withId(R.id.fab)).perform(click())

        onView(withParent(withParent(withId(R.id.url))))
                .perform(typeText("$url/4"))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(typeText("$description/4"))

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
                .check(matches(isSize(3)))

        // Delete item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(2, longClick()))

        onView(withId(R.id.delete)).perform(click())

        onView(withId(R.id.recycler_view))
                .check(matches(isSize(2)))

        // Add same url item
        onView(withId(R.id.fab)).perform(click())

        onView(withParent(withParent(withId(R.id.url))))
                .perform(typeText("$url/3"))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(typeText("$description/5"))

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.recycler_view))
                .check(matches(isSize(2)))

        onView(withId(R.id.recycler_view))
                .check(matches(withText(1, R.id.title, "$url/3")))
                .check(matches(withText(1, R.id.description, "$description/5")))

        // Open url
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("$url/2".toUri())))
    }
}
