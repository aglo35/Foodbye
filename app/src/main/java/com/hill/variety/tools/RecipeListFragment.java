package com.hill.variety.tools;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hill.variety.model.Recipe;
import com.hill.variety.my_ingredients.BackgroundContainer;
import com.hill.variety.my_ingredients.StableArrayAdapter;
import com.variety.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mari on 3.11.2015.
 */
public class RecipeListFragment extends ListFragment {
    private List<Recipe> recipes;
    private StableArrayAdapter mAdapter;
    private ListView mListView;

    BackgroundContainer mBackgroundContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);

        recipes = new ArrayList<>();

        mBackgroundContainer = (BackgroundContainer) view.findViewById(R.id.recipeListViewBackground);
//        ArrayAdapter<Ingredient> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_ingredient, ingredients);
//        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mListView = getListView();
        getListView().setAdapter(mAdapter);
    }
}





