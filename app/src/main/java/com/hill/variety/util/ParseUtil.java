package com.hill.variety.util;

import com.parse.ParseUser;

/**
 * Created by allar on 19.11.15.
 */
public class ParseUtil {

    public ParseUser getCurrentUser() {
        return ParseUser.getCurrentUser();
    }
}
