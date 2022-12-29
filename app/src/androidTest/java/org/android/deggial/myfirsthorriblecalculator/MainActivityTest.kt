package org.android.deggial.myfirsthorriblecalculator


import android.graphics.ColorSpace.match
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var textViewLegs: ViewInteraction
    private lateinit var buttonDo: ViewInteraction
    private lateinit var textViewName: ViewInteraction
    private lateinit var buttonNext: ViewInteraction

    @Before
    fun setUp(){
        Thread.sleep(500)

        textViewLegs = onView(
            allOf(
                withId(R.id.textView),
                withParent(withParent(withId(android.R.id.content)))
            )
        )
        buttonDo = onView(
            allOf(
                withId(R.id.purple_rectangle),
                withParent(withParent(withId(android.R.id.content)))
            )
        )
        textViewName = onView(
            allOf(
                withId(R.id.textView2),
                withParent(withParent(withId(android.R.id.content)))
            )
        )
        buttonNext = onView(
            allOf(
                withId(R.id.button2),
                withParent(withParent(withId(android.R.id.content)))
            )
        )
    }

    @Test
    fun mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html


        textViewName.check(matches(withText(R.string.welcome_string)))
        textViewName.check(matches(isDisplayed()))

        textViewLegs.check(matches(withText(R.string.app_name)))
        textViewLegs.check(matches(isDisplayed()))

        buttonDo.check(matches(isDisplayed()))
        buttonNext.check(matches(not(isDisplayed())))


        while (checkNextBtnEnabled())
            if(checkNextBtnVisibility()){
                nextCatTest()
            } else {
                runOverCat()
            }
    }

    fun nextCatTest(){
        buttonNext.check(matches(isDisplayed()))
        buttonNext.perform(click())
        buttonNext.check(matches(not(isDisplayed())))

        textViewName.check(matches(withText(checkCurrentCatName())))
        textViewLegs.check(matches(withText(checkCurrentCatLegs())))

    }

    @Test
    fun textVisibility(){
        textViewName.check(matches(isDisplayed()))
    }

    private fun runOverCat(){
        // perform the first click and check if the values are correct
        buttonDo.perform(click())
        textViewLegs.check(matches(withText(checkCurrentCatLegs())))
        textViewName.check(matches(withText(checkCurrentCatName())))
    }

    private fun checkCurrentCatLegs(): String{
        lateinit var cat: MainActivity.Cats
        mActivityScenarioRule.scenario.onActivity {
            cat = it.cats.first()
        }
        return cat.legCount().toString()
    }

    private fun checkCurrentCatName(): String{
        lateinit var cat: MainActivity.Cats
        mActivityScenarioRule.scenario.onActivity {
            cat = it.cats.first()
        }
        return cat.catName()
    }

    private fun checkNextBtnVisibility(): Boolean{
        var isShown = false
        mActivityScenarioRule.scenario.onActivity {
            isShown = it.buttonNext.isVisible
        }
        return isShown
    }

    private fun checkNextBtnEnabled(): Boolean{
        var isEnabled = false
        mActivityScenarioRule.scenario.onActivity {
            isEnabled = it.buttonNext.isEnabled
        }
        return isEnabled
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
