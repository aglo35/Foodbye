package com.foodbye;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.foodbye.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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

    private ArrayList<Recipe> recipes;

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
                // getting values from selected ListItem
                String title = ((TextView) view.findViewById(R.id.recipe_title))
                        .getText().toString();
                String social_rank = ((TextView) view.findViewById(R.id.social_rank))
                        .getText().toString();
                String recipe_id = ((TextView) view.findViewById(R.id.recipe_id))
                        .getText().toString();

                // Starting single recipe activity
                Intent intent = new Intent(getApplicationContext(),
                        SingleRecipeActivity.class);

                intent.putExtra(TAG_TITLE, title);
                intent.putExtra(TAG_SOCIAL_RANK, social_rank);
                intent.putExtra(TAG_RECIPE_ID, recipe_id);

                startActivity(intent);
            }
        });

        // Get the query from the intent
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        recipes = intent.getParcelableArrayListExtra(MainActivity.EXTRA_RECIPE_LIST);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        class SearchRecipesTask extends AsyncTask<String, Void, String> {

            private static final String API_URL_SEARCH = "http://food2fork.com/api/search";
            private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";
            private static final String API_URL_GET = "http://food2fork.com/api/get";

            private static final int MAX_RECIPES = 5;

            private final ProgressBar progressBar;
            private ArrayList<Recipe> recipes;
            private ArrayList<HashMap<String, String>> recipeList;
            private JSONArray recipesArray;

            public SearchRecipesTask(ProgressBar progressBar, ArrayList<Recipe> recipes) {
                this.progressBar = progressBar;
                this.recipes = recipes;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recipeList = new ArrayList<>();
                recipes = new ArrayList<>();
            }

            @Override
            protected String doInBackground(String... params) {
                String query = params[0];

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

                            // tmp hashmap for single recipe
                            HashMap<String, String> recipe = new HashMap<>();

                            // adding each child node to HashMap key => value
                            recipe.put(TAG_TITLE, title);
                            recipe.put(TAG_SOCIAL_RANK, socialRank);
                            recipe.put(TAG_RECIPE_ID, "Recipe Id (For testing): " + recipeId);

                            Recipe newRecipe = new Recipe();

                            newRecipe.setRecipe_id(recipeId);
                            newRecipe.setRecipe_title(title);
                            newRecipe.setSocial_rank(socialRank);

                            // Get recipe ingredients
                            getRecipeIngredients(recipeId, newRecipe);

                            recipes.add(newRecipe);

                            // adding recipe to recipe list
                            recipeList.add(recipe);
                        }
                    } catch (JSONException e) {
                        // TODO: Proper error handling.
                        e.printStackTrace();
                    }
                }
            }

            private void getRecipeIngredients(String recipeId, Recipe recipe) {

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

                        parseResponseFromGetCall(response, recipe.getIngredients());
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    // TODO: Proper error handling.
                    e.printStackTrace();
                }
            }

            private void parseResponseFromGetCall(String response, ArrayList<String> ingredients) {

                if (response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        // Getting JSON Array node
                        JSONObject recipe = jsonObj.getJSONObject(TAG_RECIPE);

                        JSONArray ingredientsJSON = recipe.getJSONArray(TAG_INGREDIENTS);

                        // looping through all recipes
                        for (int i = 0; i < ingredientsJSON.length(); i++) {
                            String ingredient = String.valueOf(ingredientsJSON.get(i));

                            ingredients.add(ingredient);
                        }
                    } catch (JSONException e) {
                        // TODO: Proper error handling.
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(String response) {
                progressBar.setVisibility(View.GONE);

                // Updating parsed JSON data into ListView
                SimpleAdapter adapter = new SimpleAdapter(
                        SearchRecipesActivity.this, recipeList,
                        R.layout.list_item, new String[] { TAG_TITLE, TAG_SOCIAL_RANK, TAG_RECIPE_ID },
                        new int[] { R.id.recipe_title,
                                R.id.social_rank,
                                R.id.recipe_id });

                setListAdapter(adapter);

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
