package ca.gabrielcastro.openotp.ui.list;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.gabrielcastro.openotp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OtpListActivityTest {

    @Rule
    public ActivityTestRule<OtpListActivity> mActivityTestRule = new ActivityTestRule<>(OtpListActivity.class);

    @Test
    public void otpListActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.otp_item_issuer), withText("Google Inc."), isDisplayed()));
        textView.check(matches(withText("Google Inc.")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.otp_item_account), withText("john@gmail.com"), isDisplayed()));
        textView2.check(matches(withText("john@gmail.com")));

    }
}
