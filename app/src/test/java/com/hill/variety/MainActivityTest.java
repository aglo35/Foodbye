package com.hill.variety;

import com.variety.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 *
 * Created by Allar on 7.11.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @InjectMocks
    private MainActivity mainActivity;

    @Test
    public void testSum() throws Exception {
        //expected: 6, sum of 1 and 5
        assertEquals(6d, mainActivity.sum(1d, 5d), 0);
    }
}