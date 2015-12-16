package com.hill.variety;

import com.hill.variety.util.FacebookUtil;
import com.hill.variety.util.ParseUtil;
import com.parse.ParseUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by allar on 19.11.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTestJUnit {

    @Mock
    ParseUtil parseUtil;

    @Mock
    FacebookUtil facebookUtil;

    @InjectMocks
    MainActivity activity;

    @Test
    public void testIsAuthenticatedTrue() {
        ParseUser currentUser = mock(ParseUser.class);
        when(parseUtil.getCurrentUser()).thenReturn(currentUser);
        when(facebookUtil.isLoggedIn()).thenReturn(true);
        boolean result = activity.isAuthenticated();
        assertTrue(result);
    }

}