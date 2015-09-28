package com.hill.variety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hill.variety.model.Recipe;
import com.parse.ParseUser;
import com.variety.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_QUERY = "com.variety.QUERY";
    public static final String EXTRA_RECIPE_LIST = "com.variety.RECIPE_LIST";
    private static final String GUEST_LOGIN = "com.variety.GUEST_LOGIN";

    private ArrayList<Recipe> recipes;

    CallbackManager callbackManager;

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null) {
            String guest_login = intent.getStringExtra(MainActivity.GUEST_LOGIN);
            if (GUEST_LOGIN.equals(guest_login)) {
                setContentView(R.layout.activity_main);
            } else {
//                setFacebookLoginCallbackManager();
                setContentView(R.layout.activity_main);

                //        Check if user is logged in
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser == null) {
                    loadLoginView();
                }
            }
        }

        // Initialize new list when activity is made for the first time.
        recipes = new ArrayList<>();
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setFacebookLoginCallbackManager() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
//                        setContentView(R.layout.activity_main);
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            ParseUser.logOut();
            loadLoginView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Search button */
    public void searchRecipe(View view) {
        Intent intent = new Intent(this, SearchRecipesActivity.class);
        EditText editText = (EditText) findViewById(R.id.search_text);
        String query = editText.getText().toString();
        intent.putExtra(EXTRA_QUERY, query);
        intent.putParcelableArrayListExtra(EXTRA_RECIPE_LIST, recipes);
        startActivity(intent);
    }

}
