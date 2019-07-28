package jp.cordea.urldispatcher

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScenarioTest {

    @Test
    fun save() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_main)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.fab)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_add)))
                .check(matches(isDisplayed()))

        onView(withParent(withParent(withId(R.id.url))))
                .perform(typeText("http://example.com"))
        onView(withParent(withParent(withId(R.id.description))))
                .perform(typeText("description"))

        onView(withId(R.id.fab)).perform(click())

        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.title_main)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view))
                .check(matches(withText(0, R.id.title, "http://example.com")))
                .check(matches(withText(0, R.id.description, "description")))
    }
}
