package com.example.david.helloworld.helpers;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.david.helloworld.R;
import com.example.david.helloworld.models.user.UserImage;

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

    public static void setUserAvatar(ImageView imageView, UserImage userImage) {
        switch (userImage) {
            case Anonymous:
                imageView.setImageResource(R.drawable.anonymous_user_logo);
                break;
            case DefaultImage:
                imageView.setImageResource(R.drawable.user_default_image);
                break;
            case Detective:
                imageView.setImageResource(R.drawable.detective_user_logo);
                break;
            case Ghost:
                imageView.setImageResource(R.drawable.ghost_user_logo);
                break;
            case Hacker:
                imageView.setImageResource(R.drawable.hacker_user_logo);
                break;
            case Ninja:
                imageView.setImageResource(R.drawable.ninja_user_logo);
                break;
            default:
                imageView.setImageResource(R.drawable.user_default_image);
                break;
        }
    }
}
