package com.hill.variety;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hill.variety.model.Ingredient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.variety.R;

import java.util.ArrayList;
import java.util.List;


public class IngredientListFragment extends ListFragment {

    private List<Ingredient> ingredients;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_ingredients, container, false);

        ingredients = new ArrayList<>();
        ArrayAdapter<Ingredient> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_ingredient, ingredients);
        setListAdapter(adapter);

        refreshIngredientsList();

        return view;
    }

    private void refreshIngredientsList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ingredient");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> ingredientList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    ingredients.clear();
                    for (ParseObject ingredientObject : ingredientList) {
                        Ingredient ingredient = new Ingredient(ingredientObject.getString("name"));
                        ingredients.add(ingredient);
                    }
                    ((ArrayAdapter<Ingredient>) getListAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
}
