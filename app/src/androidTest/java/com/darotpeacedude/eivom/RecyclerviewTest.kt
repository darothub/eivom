package com.darotpeacedude.eivom

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darotpeacedude.eivom.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerviewTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testThatWhen_MainFragment_Is_Launched_RecyclerView_Is_Displayed() {
        Espresso.onView(withId(R.id.movie_rcv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testThatWhen_RecyclerView_Is_Clicked_DetailFragment_Is_Launched() {
        // Disable animation to test
        Espresso.onView(withId(R.id.movie_rcv))
            .perform(click())

        Espresso.onView(withId(R.id.blur_bg_iv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
