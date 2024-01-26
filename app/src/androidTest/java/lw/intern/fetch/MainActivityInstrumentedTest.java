package lw.intern.fetch;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void recyclerView_showsItemsAfterFetch() {
        // Click the "Get Data" button to trigger data loading
        onView(withId(R.id.btn_getData)).perform(click());
        // Wait for the RecyclerView to load data
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if the RecyclerView contains specific items
        onView(allOf(withId(R.id.textViewTitle), withText("Item 0"), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewTitle), withText("Item 4"), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewTitle), withText("Item 6"), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewTitle), withText("Item 19"), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewTitle), withText("Item 24"), isDisplayed()))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewTitle), withText("Item 28"), isDisplayed()))
                .check(matches(isDisplayed()));

    }
}
