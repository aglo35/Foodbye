package com.hill.variety.search_recipes;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hill.variety.model.Ingredient;
import com.hill.variety.my_ingredients.BackgroundContainer;
import com.hill.variety.my_ingredients.StableArrayAdapter;
import com.variety.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mari on 10.11.2015.
 */
public class SearchRecipesFragment extends ListFragment {
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

    BackgroundContainer backgroundContainer;
    private List<Ingredient> recipes;
    private StableArrayAdapter mAdapter;
    private ListView mListView;
/*
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.search_recipes, container, false);

        recipes = new ArrayList<>();

        backgroundContainer = (BackgroundContainer) view.findViewById(R.id.listViewBackground2);

        return view;
    }*/
}
