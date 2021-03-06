package com.hill.variety;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.AdapterView;
import android.widget.ListView;

import com.variety.R;
import com.hill.variety.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main class communicating with Food2Fork API
 */
public class SearchRecipesActivity extends ListActivity implements View.OnClickListener {

    private static final String TAG_RECIPES = "recipes";
    private static final String TAG_TITLE = "title";
    private static final String TAG_SOCIAL_RANK = "social_rank";
    private static final String TAG_RECIPE_ID = "recipe_id";
    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String TAG_RECIPE = "recipe";
    private static final String TAG_RECIPE_IMG_URL = "image_url";
    private static final String API_URL_SEARCH = "http://food2fork.com/api/search";
    private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";
    private static final String API_URL_GET = "http://food2fork.com/api/get";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_api_button);
        searchButton.setOnClickListener(this);

        ListView listView = getListView();

        // Listview on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Recipe recipe = (Recipe) parent.getAdapter().getItem(position);

                // Starting single recipe activity
                Intent intent = new Intent(getApplicationContext(),
                        SingleRecipeActivity.class);

                intent.putExtra(TAG_TITLE, recipe.getRecipe_title());
                intent.putExtra(TAG_SOCIAL_RANK, recipe.getSocial_rank());
                intent.putExtra(TAG_RECIPE_ID, recipe.getRecipe_id());
                intent.putExtra(TAG_RECIPE_IMG_URL, recipe.getRecipeImgUrl());
                intent.putStringArrayListExtra(TAG_INGREDIENTS, recipe.getIngredients());

                startActivity(intent);
            }
        });

        // Get the query from the intent
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        ArrayList<Recipe> recipes = intent.getParcelableArrayListExtra(MainActivity.EXTRA_RECIPE_LIST);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        class SearchRecipesTask extends AsyncTask<String, Void, String> {

            private static final int MAX_RECIPES = 5;

            private final ProgressBar progressBar;
            private ArrayList<Recipe> recipes;
            private JSONArray recipesArray;

            public SearchRecipesTask(ProgressBar progressBar, ArrayList<Recipe> recipes) {
                this.progressBar = progressBar;
                this.recipes = recipes;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recipes = new ArrayList<>();
            }

            @Override
            protected String doInBackground(String... params) {
                String query = params[0];

                return makeSearchCall(query);
            }

            private String makeSearchCall(String query) {

                try {
                    URL url = new URL(API_URL_SEARCH + "?key=" + API_KEY + "&q=" + query);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        String response = stringBuilder.toString();

                        parseResponseFromSearchCall(response);

                        return response;
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    // TODO: Proper error handling.
                    e.printStackTrace();
                    return null;
                }
            }

            private void parseResponseFromSearchCall(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        // Getting JSON Array node
                        recipesArray = jsonObj.getJSONArray(TAG_RECIPES);

                        // looping through all recipes
                        for (int i = 0; i < MAX_RECIPES; i++) {
                            JSONObject c = recipesArray.getJSONObject(i);

                            String title = c.getString(TAG_TITLE);
                            String socialRank = c.getString(TAG_SOCIAL_RANK);
                            String recipeId = c.getString(TAG_RECIPE_ID);
                            String recipeImgUrl = c.getString(TAG_RECIPE_IMG_URL);

                            Recipe newRecipe = new Recipe();

                            newRecipe.setRecipe_id(recipeId);
                            newRecipe.setRecipe_title(title);
                            newRecipe.setSocial_rank(socialRank);
                            newRecipe.setRecipeImgUrl(recipeImgUrl);

                            // Get recipe ingredients
                            String responseOfGetCall = makeGetCall(recipeId);
                            newRecipe.setIngredients(parseResponseFromGetCall(responseOfGetCall));


                            recipes.add(newRecipe);
                        }
                    } catch (JSONException e) {
                        // TODO: Proper error handling.
                        e.printStackTrace();
                    }
                }
            }

            private String makeGetCall(String recipeId) {

                try {
                    URL url = new URL(API_URL_GET + "?key=" + API_KEY + "&rId=" + recipeId);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        String response = stringBuilder.toString();

                        return response;
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    // TODO: Proper error handling.
                    e.printStackTrace();
                }
                return recipeId;
            }

            private ArrayList<String> parseResponseFromGetCall(String response) {

                if (response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        // Getting JSON Array node
                        JSONObject recipe = jsonObj.getJSONObject(TAG_RECIPE);

                        JSONArray ingredientsJSON = recipe.getJSONArray(TAG_INGREDIENTS);

                        ArrayList<String> ingredients = new ArrayList<>();

                        // looping through all recipes
                        for (int i = 0; i < ingredientsJSON.length(); i++) {
                            String ingredient = String.valueOf(ingredientsJSON.get(i));

                            ingredients.add(ingredient);
                        }

                        return ingredients;
                    } catch (JSONException e) {
                        // TODO: Proper error handling.
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                // Updating parsed JSON data into ListView
                RecipeArrayAdapter adapter = new RecipeArrayAdapter(SearchRecipesActivity.this, recipes);
                setListAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            }
        }

        SearchRecipesTask searchRecipesTask = new SearchRecipesTask(progressBar, recipes);
        searchRecipesTask.execute(query);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SearchRecipesActivity.class);
        EditText editText = (EditText) findViewById(R.id.search_text);
        String query = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_QUERY, query);
        startActivity(intent);
    }
}
