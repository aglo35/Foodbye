package com.foodbye;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetRecipeTask extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String API_URL = "http://food2fork.com/api/get";
    private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";

    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String TAG_RECIPE = "recipe";

    private ArrayList<String> ingredientsList;

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String recipe_id = params[0];

        try {
            URL url = new URL(API_URL + "?key=" + API_KEY + "&rId=" + recipe_id);
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

                ingredientsList = new ArrayList<>();
                parseResponseToJSON(response);

                return ingredientsList;
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

    private void parseResponseToJSON(String response) {
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);

                // Getting JSON Array node
                JSONObject recipe = jsonObj.getJSONObject(TAG_RECIPE);

                JSONArray ingredients = recipe.getJSONArray(TAG_INGREDIENTS);

                // looping through all recipes
                for (int i = 0; i < ingredients.length(); i++) {
                    String ingredient = String.valueOf(ingredients.get(i));

                    ingredientsList.add(ingredient);
                }
            } catch (JSONException e) {
                // TODO: Proper error handling.
                e.printStackTrace();
            }
        }
    }


}
