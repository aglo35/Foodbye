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

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.variety",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {

//        }

        Intent intent = getIntent();
        if (intent != null) {
            String guest_login = intent.getStringExtra(MainActivity.GUEST_LOGIN);
            if (GUEST_LOGIN.equals(guest_login)) {
                setContentView(R.layout.activity_main);
            } else {
                setContentView(R.layout.login);
                setFacebookLoginCallbackManager();

//                posts = new ArrayList<>();
//
//                ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this, R.layout.list_item, posts);
//
//                ListView myList = (ListView) findViewById(android.R.id.list);
//                myList.setAdapter(adapter);
//
//                refreshPostList();
            }
        }

        // Initialize new list when activity is made for the first time.
        recipes = new ArrayList<>();
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

    public void guestLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GUEST_LOGIN, GUEST_LOGIN);
        startActivity(intent);
    }
}
