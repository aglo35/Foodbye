package com.hill.variety;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hill.variety.model.Ingredient;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.variety.R;

public class AddIngredientFragment extends Fragment {

    private static final String INGREDIENT_SAVED_SUCCESS = "Ingredient added";
    private static final String INGREDIENT_SAVED_FAILED = "Failed to add ingredient";

    private EditText nameAddText;
    private Ingredient ingredient;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_ingredient_fragment, container, false);

        nameAddText = (EditText) view.findViewById(R.id.ingredientName);

        Button saveIngredientButton = (Button) view.findViewById(R.id.saveIngredient);
        saveIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });

        return view;
    }

    private void saveIngredient() {

        String ingredientName = nameAddText.getText().toString();

        ingredientName = ingredientName.trim();

        if (!ingredientName.isEmpty()) {

            // Check if ingredient is being created

            if (ingredient == null) {
                // create new ingredient

                ingredient = new Ingredient(ingredientName);
                // Set the current user, assuming a user is signed in
                ingredient.setOwner(ParseUser.getCurrentUser());
                // Immediately save the data asynchronously
                ingredient.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            // Saved successfully.
                            Toast.makeText(getActivity(), INGREDIENT_SAVED_SUCCESS, Toast.LENGTH_SHORT).show();

                            FragmentTransaction listIngredientFragTrans = getFragmentManager().beginTransaction();
                            MyIngredientsActivity.initializeIngredientListFragment(listIngredientFragTrans);

                            FragmentTransaction addIngredientFragTrans = getFragmentManager().beginTransaction();
                            handleAddIngredientsFrame(addIngredientFragTrans);
                        } else {
                            // The save failed.
                            Toast.makeText(getActivity(), INGREDIENT_SAVED_FAILED, Toast.LENGTH_SHORT).show();
//                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }

                        // Clear ingredient
                        ingredient = null;
                    }
                });
                // or for a more robust offline save
                // todoItem.saveEventually();
            }
        }
    }

    /**
     * Handles add ingredients frame after adding an ingredient.
     * Initializes fragment if needed and hides it if it's not hidden.
     */
    private void handleAddIngredientsFrame(FragmentTransaction fragmentTransaction) {
        Fragment addIngredientsFragment = getFragmentManager().findFragmentById(R.id.add_ingredients_frame);

        if (addIngredientsFragment == null) {
            addIngredientsFragment = new AddIngredientFragment();

            fragmentTransaction.replace(R.id.add_ingredients_frame, addIngredientsFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        } else {
            if (addIngredientsFragment.isHidden()) {
                fragmentTransaction.show(addIngredientsFragment);
            } else {
                fragmentTransaction.hide(addIngredientsFragment);
            }
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
