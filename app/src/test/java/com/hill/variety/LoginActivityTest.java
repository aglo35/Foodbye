package com.hill.variety;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.variety.BuildConfig;
import com.variety.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;


/**
 *
 * Created by Allar on 7.11.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);

        usernameEditText = (EditText) activity.findViewById(R.id.emailField);
        passwordEditText = (EditText) activity.findViewById(R.id.passwordField);
        loginButton = (Button) activity.findViewById(R.id.loginButton);

        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        if (usernameEditText.requestFocus()) {
                            usernameEditText.setText("tester2");
                        }
                        if (passwordEditText.requestFocus()) {
                            passwordEditText.setText("tester2");
                        }
                        loginButton.performClick();
                    }
                }
        );

        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        assertEquals(shadowOf(activity).getNextStartedActivity(), expectedIntent);
    }


}