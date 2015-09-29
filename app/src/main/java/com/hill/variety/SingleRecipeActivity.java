package com.hill.variety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.variety.R;


public class SingleRecipeActivity extends Activity {

    private static final String TAG_TITLE = "title";
    private static final String TAG_SOCIAL_RANK = "social_rank";
    private static final String TAG_RECIPE_ID = "recipe_id";
    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";
    private static final String API_URL_GET = "http://food2fork.com/api/get";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String title = in.getStringExtra(TAG_TITLE);
        String socialRank = in.getStringExtra(TAG_SOCIAL_RANK);
        String recipeId = in.getStringExtra(TAG_RECIPE_ID);

        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.recipe_title);
        TextView lblSocialRank = (TextView) findViewById(R.id.social_rank);
        TextView lblRecipeId = (TextView) findViewById(R.id.recipe_id);

        lblTitle.setText(title);
        lblSocialRank.setText(socialRank);
        lblRecipeId.setText(recipeId);
    }


}
