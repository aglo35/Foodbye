package com.hill.variety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.hill.variety.model.Recipe;
import com.hill.variety.my_ingredients.MyIngredientsActivity;
import com.parse.ParseUser;
import com.variety.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_QUERY = "com.variety.QUERY";
    public static final String EXTRA_RECIPE_LIST = "com.variety.RECIPE_LIST";
    private static final String GUEST_LOGIN = "com.variety.GUEST_LOGIN";

    private ArrayList<Recipe> recipes;

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
                setContentView(R.layout.activity_main);

                // Get Parse user token
                ParseUser currentUser = ParseUser.getCurrentUser();

                // Get Facebook user token
                boolean facebookLoggedIn = isFacebookLoggedIn();

                // Check if Parse or Facebook user token is present
                if (currentUser == null && !facebookLoggedIn) {
                    loadLoginView();
                }


            }
        }

        // Initialize new list when activity is made for the first time.
        recipes = new ArrayList<>();
    }

    private boolean isFacebookLoggedIn() {
        return (AccessToken.getCurrentAccessToken() != null);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
            if (isFacebookLoggedIn()) {
                LoginManager.getInstance().logOut();
            } else {
                ParseUser.logOut();
            }
            loadLoginView();
            return true;
        }

        if (id == R.id.my_ingredients) {
            goToMyIngredients();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMyIngredients() {
        Intent intent = new Intent(this, MyIngredientsActivity.class);
        startActivity(intent);
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
