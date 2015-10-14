package com.hill.variety.tools;

import android.widget.ImageView;

import junit.framework.TestCase;

// Erko, Kristiina, Allar, Mari
public class ImageLoadTaskTest extends TestCase {

    private static final String IMG_URL = "https://scontent-frt3-1.xx.fbcdn.net/hphotos-xtf1/v/t1.0-9/11401491_1093064344040489_1327137758770073648_n.jpg?oh=2776d88b4db9e05b999dfc64aee18a68&oe=56955717";

    public void testGetMyBitmap() throws Exception {

        ImageLoadTask imageLoadTask = new ImageLoadTask(IMG_URL);

        assertNotNull(imageLoadTask);
    }

    public void testGetMyBitmapNull() throws Exception {

        ImageLoadTask imageLoadTask = new ImageLoadTask(null);

        assertNotNull(imageLoadTask);
    }
}