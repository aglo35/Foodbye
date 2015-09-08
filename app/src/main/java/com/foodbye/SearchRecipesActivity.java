package com.foodbye;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class SearchRecipesActivity extends ListActivity implements View.OnClickListener {

    private static final String TAG_RECIPES = "recipes";
    private static final String TAG_TITLE = "title";
    private static final String TAG_SOCIAL_RANK = "social_rank";

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

                // Starting single contact activity
                Intent intent = new Intent(getApplicationContext(),
                        SingleRecipeActivity.class);
                intent.putExtra(TAG_TITLE, title);
                intent.putExtra(TAG_SOCIAL_RANK, social_rank);
                startActivity(intent);
            }
        });

        // Get the query from the intent
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        class RetrieveFeedTask extends AsyncTask<String, Void, String> {

            private static final String API_URL = "http://food2fork.com/api/search";
            private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";

            private final ProgressBar progressBar;
            private ArrayList<HashMap<String, String>> recipeList;
            private JSONArray recipes;

            public RetrieveFeedTask(ProgressBar progressBar) {
                this.progressBar = progressBar;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                recipeList = new ArrayList<>();
            }

            @Override
            protected String doInBackground(String... params) {
                String query = params[0];

                try {
                    URL url = new URL(API_URL + "?key=" + API_KEY + "&q=" + query);
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

                        parseResponseToJSON(response);

                        return response;
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            private void parseResponseToJSON(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        // Getting JSON Array node
                        recipes = jsonObj.getJSONArray(TAG_RECIPES);

                        // looping through all recipes
                        for (int i = 0; i < recipes.length(); i++) {
                            JSONObject c = recipes.getJSONObject(i);

                            String title = c.getString(TAG_TITLE);
                            String socialRank = c.getString(TAG_SOCIAL_RANK);

                            // tmp hashmap for single recipe
                            HashMap<String, String> recipe = new HashMap<>();

                            // adding each child node to HashMap key => value
                            recipe.put(TAG_TITLE, title);
                            recipe.put(TAG_SOCIAL_RANK, socialRank);

                            // adding recipe to recipe list
                            recipeList.add(recipe);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(String response) {
                progressBar.setVisibility(View.GONE);

                // Updating parsed JSON data into ListView
                ListAdapter adapter = new SimpleAdapter(
                        SearchRecipesActivity.this, recipeList,
                        R.layout.list_item, new String[] { TAG_TITLE, TAG_SOCIAL_RANK },
                        new int[] { R.id.recipe_title,
                                R.id.social_rank });

                setListAdapter(adapter);
            }
        }

        RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask(progressBar);
        retrieveFeedTask.execute(query);
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
