package com.hill.variety;

import android.view.MenuItem;

import com.hill.variety.util.FacebookUtil;
import com.hill.variety.util.ParseUtil;
import com.parse.ParseUser;
import com.variety.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * Created by Allar on 19.11.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTestJUnit {

    @Mock
    ParseUtil parseUtil;

    @Mock
    FacebookUtil facebookUtil;

    private @Mock MenuItem item;

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

    @Test
    public void onOptionsItemSelectedTest() {
        when(item.getItemId()).thenReturn(R.id.action_settings);

        boolean actionSetting = activity.onOptionsItemSelected(item);
        assertTrue(actionSetting);

//        when(item.getItemId()).thenReturn(R.id.action_logout);
//
//        boolean actionLogout = activity.onOptionsItemSelected(item);
//        assertTrue(actionSetting);
    }

}