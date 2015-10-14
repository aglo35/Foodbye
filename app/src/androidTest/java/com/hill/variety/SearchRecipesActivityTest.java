package com.hill.variety;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Erko, Kristiina, Allar, Mari
public class SearchRecipesActivityTest extends TestCase {

    private static final String API_KEY = "d7d9a961ed44ce2f707a056eb3d29c38";
    private static final String API_URL_GET = "http://food2fork.com/api/get";
    private static final String RECIPE_ID = "8";
    private static final String API_URL_SEARCH = "http://food2fork.com/api/search";

    public void testMakeGetCall() {
        String response = makeGetCall(RECIPE_ID);

        assertNotNull(response);
    }

    //TEST
    public void testMakeGetCallNoParameter() {
        String response = makeGetCall(null);

        assertNotNull(response);
    }

    //TEST
    public void testMakeSearchCallNoParameter() {
        String response = makeSearchCall(null);

        assertNotNull(response);
    }

    public String makeGetCall(String recipeId) {

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

                return stringBuilder.toString();
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

                return stringBuilder.toString();
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
}