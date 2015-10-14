package com.hill.variety.tests;

import com.hill.variety.model.Recipe;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kristiina on 14.10.2015.
 */
public class RecipeTest extends Recipe {

    @org.junit.Test
    public void testGetIngredientsForDisplay() throws Exception {
        Recipe recipe = new Recipe();
        assertTrue(recipe != null);
    }
}