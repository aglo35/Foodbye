package com.hill.variety;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.variety.R;

/**
 * Main activity responsible for "My Ingredients" page.
 *
 * Created by Allar on 25.10.15.
 */
public class MyIngredientsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_my_ingredients_view);

        // Initialize fragment containing all ingredients
        // associated with the current user.
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        initializeIngredientListFragment(fragmentTransaction);
    }

    public static void initializeIngredientListFragment(FragmentTransaction fragmentTransaction) {
        Fragment fragment = new IngredientListFragment();
        fragmentTransaction.replace(R.id.ingredients_list_frame, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_my_ingredients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_new: {
                initializeAddIngredientFragment();
                break;
            }
            case R.id.action_settings: {
                // TODO:
                // Do something when user selects Settings from Action Bar overlay
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeAddIngredientFragment() {

        Fragment fragment = getFragmentManager().findFragmentById(R.id.add_ingredients_frame);
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (fragment == null) {
            fragment = new AddIngredientFragment();

            ft.replace(R.id.add_ingredients_frame, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        } else {
            if (fragment.isHidden()) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }

        ft.addToBackStack(null);
        ft.commit();
    }
}
