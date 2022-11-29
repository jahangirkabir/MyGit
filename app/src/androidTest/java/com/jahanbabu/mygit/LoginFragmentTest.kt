package com.jahanbabu.mygit

import androidx.activity.ComponentActivity
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jahanbabu.mygit.core.navigation.RouteActivity
import com.jahanbabu.mygit.features.login.view.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

//@HiltAndroidTest
//@AndroidEntryPoint
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

    @get : Rule
    var mActivityRule = ActivityScenarioRule(RouteActivity::class.java)

    private lateinit var scenario: FragmentScenario<LoginFragment>

    @Before
    fun setup() {
//        scenario = launchFragmentInContainer(themeResId = R.style.Theme_MyGit)
//        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testLoginButton() {
        onView(withId(R.id.btn_login)).check(matches((isDisplayed())))
    }

    @Test
    fun testLoginButtonClick() {
        onView(withId(R.id.btn_login)).check(matches((isDisplayed())))
    }
}