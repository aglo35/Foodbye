package com.hill.variety.util;

import com.facebook.AccessToken;

/**
 * Created by allar on 19.11.15.
 */
public class FacebookUtil {
    public boolean isLoggedIn() {
        return (AccessToken.getCurrentAccessToken() != null);
    }
}
