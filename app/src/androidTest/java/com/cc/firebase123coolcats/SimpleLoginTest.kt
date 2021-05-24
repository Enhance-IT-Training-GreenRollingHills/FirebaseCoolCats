package com.cc.firebase123coolcats

import android.R.id
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.cc.firebase123coolcats.view.MainActivity
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4::class)
@LargeTest
class SimpleLoginTest {

    @get:Rule
    var activityRule : ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)



    @Test
    fun test_loginButton () {

        onView(withId(R.id.editTextEmail))
            .perform(clearText())
            .perform(typeText("vaa2222@yahoo.com"))

        onView(withId(R.id.editTextPassword))
            .perform(clearText())
            .perform(typeText("123456"))

        onView(withId(R.id.loginButton))
            .perform(click())

        onView(withId(R.id.noteListRootLayout))
            .check(matches(isDisplayed()))
    }


}