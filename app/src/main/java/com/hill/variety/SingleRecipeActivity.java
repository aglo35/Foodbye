package com.hill.variety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hill.variety.tools.ImageLoadTask;
import com.variety.R;

import java.util.ArrayList;

public class SingleRecipeActivity extends AppCompatActivity {

    private static final String TAG_TITLE = "title";
    private static final String TAG_SOCIAL_RANK = "social_rank";
//    private static final String TAG_RECIPE_ID = "recipe_id";
    private static final String TAG_RECIPE_IMG_URL = "image_url";
    private static final String TAG_INGREDIENTS = "ingredients";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String title = in.getStringExtra(TAG_TITLE);
        String socialRank = in.getStringExtra(TAG_SOCIAL_RANK);
//        String recipeId = in.getStringExtra(TAG_RECIPE_ID);
        String imgUrl = in.getStringExtra(TAG_RECIPE_IMG_URL);
        ArrayList<String> ingredients = in.getStringArrayListExtra(TAG_INGREDIENTS);

        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.recipe_title);
        TextView lblSocialRank = (TextView) findViewById(R.id.social_rank);
//        TextView lblRecipeId = (TextView) findViewById(R.id.recipeId);
        ImageView recipeImg = (ImageView) findViewById(R.id.recipe_img);
        ListView lblIngredients = (ListView) findViewById(R.id.ingredients);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);

        if (!arrayAdapter.isEmpty()) {
            lblIngredients.setAdapter(arrayAdapter);
        }

        lblTitle.setText(title);
        lblSocialRank.setText(socialRank);
//        lblRecipeId.setText(recipeId);
        new ImageLoadTask(imgUrl, recipeImg).execute();

    }
}
