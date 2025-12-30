package com.example.team_26_project

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LabsActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(LabsActivity::class.java)


    @Test
    fun clickReadOnIntro() {
        onView(allOf(withId(R.id.btnReadLab), isDescendantOfA(withId(R.id.module1))))
            .perform(click())

        intended(hasComponent(intro_to_android_security::class.java.name))
    }


    @Test
    fun clickReadOnSqlInjection() {
        onView(allOf(withId(R.id.btnReadLab), isDescendantOfA(withId(R.id.module_SQL))))
            .perform(click())

        intended(hasComponent(sql::class.java.name))
    }


    @Test
    fun clickReadOnIntentActivity() {
        onView(allOf(withId(R.id.btnReadLab), isDescendantOfA(withId(R.id.module2))))
            .perform(click())

        intended(hasComponent(intent_explanation::class.java.name))
    }

    @Test
    fun clickReadOnIntentRedirection() {
        onView(allOf(withId(R.id.btnReadLab), isDescendantOfA(withId(R.id.module3))))
            .perform(click())

        intended(hasComponent(intent_redirection_explanation::class.java.name))
    }

    @Test
    fun clickReadOnDeepLink() {
        onView(allOf(withId(R.id.btnReadLab), isDescendantOfA(withId(R.id.module4))))
            .perform(click())

        intended(hasComponent(deep_link::class.java.name))
    }


}