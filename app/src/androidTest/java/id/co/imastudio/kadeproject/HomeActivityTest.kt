package id.co.imastudio.kadeproject

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.co.imastudio.kadeproject.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class HomeActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {

        Thread.sleep(3000)
        onView(withId(listEvent))
                .check(matches(isDisplayed()))
        onView(withId(listEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(listEvent)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

    }

    @Test
    fun testAppBehaviour() {
        Thread.sleep(3000)

        onView(withText("Man City"))
                .check(matches(isDisplayed()))
        onView(withText("Man City")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        pressBack()

        onView(withId(navigation_notifications))
                .check(matches(isDisplayed()))
        onView(withId(navigation_notifications)).perform(click())
    }

}
