package com.hill.variety.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public Recipe() {

    }

    private ArrayList<String> ingredients;

    private String recipe_id;

    private String recipe_title;

    private String social_rank;

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getIngredientsForDisplay() {
        ArrayList<String> ingredientsArray = getIngredients();

        StringBuilder builder = new StringBuilder();
        for (String ingredient : ingredientsArray) {
            builder.append("\u2022 ").append(ingredient).append("\n");
        }

        return builder.toString();
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_title() { return recipe_title; }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public String getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(String social_rank) {
        this.social_rank = social_rank;
    }

    /* This section must be added in order for Parcable to work. */
    private int mData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe(Parcel in) {
        mData = in.readInt();
    }
    /* End of Parcable section. */
}
