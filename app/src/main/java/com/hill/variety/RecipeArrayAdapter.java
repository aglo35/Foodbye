package com.hill.variety;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hill.variety.tools.ImageLoadTask;
import com.variety.R;
import com.hill.variety.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {

    public RecipeArrayAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Recipe recipe = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_recipe, parent, false);
        }
        // Lookup view for data population
        //TextView recipe_id = (TextView) convertView.findViewById(R.id.recipe_id);
        TextView recipe_title = (TextView) convertView.findViewById(R.id.recipe_title);
        TextView social_rank = (TextView) convertView.findViewById(R.id.social_rank);
        TextView ingredients = (TextView) convertView.findViewById(R.id.ingredients);

        ImageView recipeImg = (ImageView) convertView.findViewById(R.id.recipe_img);
        // Populate the data into the template view using the data object
        //recipe_id.setText(recipe.getRecipe_id());
        recipe_title.setText(recipe.getRecipe_title());
        social_rank.setText(recipe.getSocial_rank());
        ingredients.setText(recipe.getIngredientsForDisplay());

        new ImageLoadTask(recipe.getRecipeImgUrl(), recipeImg).execute();
        // Return the completed view to render on screen
        return convertView;
    }

}
