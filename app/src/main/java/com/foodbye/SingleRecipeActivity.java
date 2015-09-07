package com.foodbye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleRecipeActivity extends Activity {

    private static final String TAG_TITLE = "title";
    private static final String TAG_SOCIAL_RANK = "social_rank";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String title = in.getStringExtra(TAG_TITLE);
        String socialRank = in.getStringExtra(TAG_SOCIAL_RANK);

        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.recipe_title);
        TextView lblSocialRank = (TextView) findViewById(R.id.social_rank);

        lblTitle.setText(title);
        lblSocialRank.setText(socialRank);
    }

}
