package com.hill.variety;

import android.app.Application;

import com.hill.variety.model.Ingredient;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 *
 * Created by allar on 25.09.15.
 */
public class FoodByeApplication extends Application {

    private static final String APPLICATION_ID = "95bEvOEwBr6FzPYw2sx9ffxbqkihjhnQtFpWTwvO";
    private static final String CLIENT_KEY = "FfkLXIDk0yAuCPXK2KrLPzOC8FqnqfV2Jx2uK7xi";

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Ingredient.class);
        // Parse initialization
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    }
}
