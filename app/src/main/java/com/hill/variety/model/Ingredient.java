package com.hill.variety.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 *
 * Created by allar on 25.10.15.
 */
@ParseClassName("Ingredient")
public class Ingredient extends ParseObject {

    // Ensure that your subclass has a public default constructor
    public Ingredient() {
        super();
    }

    // Add a constructor that contains core properties
    public Ingredient(String name) {
        super();
        setName(name);
    }

    // Use getString and others to access fields
    public String getName() {
        return getString("name");
    }

    // Use put to modify field values
    public void setName(String value) {
        put("name", value);
    }

    // Get the user for this item
    public ParseUser getUser()  {
        return getParseUser("owner");
    }

    // Associate each item with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
