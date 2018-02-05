package com.example.david.helloworld.helpers;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by david on 3.2.2018..
 */

public class ImageHelper {

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
