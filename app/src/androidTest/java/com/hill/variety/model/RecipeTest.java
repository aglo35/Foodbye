package com.hill.variety.model;

import junit.framework.TestCase;

import static org.junit.Assert.assertTrue;

public class RecipeTest extends TestCase {

    public void testGetIngredientsForDisplay() throws Exception {
        Recipe recipe = new Recipe();
        assertTrue(recipe != null);
        assertNotNull(recipe.getIngredientsForDisplay());
    }

}