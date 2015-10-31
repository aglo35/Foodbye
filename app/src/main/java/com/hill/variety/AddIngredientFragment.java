package com.hill.variety;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
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

    private EditText nameAddText;
    private Button saveIngredientButton;
    private String ingredientName;
    private Ingredient ingredient;

    private FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_ingredient_fragment, container, false);

        nameAddText = (EditText) view.findViewById(R.id.ingredientName);

        saveIngredientButton = (Button) view.findViewById(R.id.saveIngredient);
        saveIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });

        return view;
    }

    private void saveIngredient() {

        ingredientName = nameAddText.getText().toString();

        ingredientName = ingredientName.trim();

        if (!ingredientName.isEmpty()) {

            // Check if post is being created or edited

            if (ingredient == null) {
                // create new post

                ingredient = new Ingredient(ingredientName);
                // Set the current user, assuming a user is signed in
                ingredient.setOwner(ParseUser.getCurrentUser());
                // Immediately save the data asynchronously
                ingredient.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            // Saved successfully.
                            Toast.makeText(getActivity(), "Ingredient added", Toast.LENGTH_SHORT).show();

                            fragmentTransaction = getFragmentManager().beginTransaction();
                            MyIngredientsActivity.initializeIngredientListFragment(fragmentTransaction);
                        } else {
                            // The save failed.
                            Toast.makeText(getActivity(), "Failed to add ingredient", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });
                // or for a more robust offline save
                // todoItem.saveEventually();
            }
        }
    }
}
