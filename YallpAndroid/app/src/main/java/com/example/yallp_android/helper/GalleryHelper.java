package com.example.yallp_android.helper;

import android.app.Activity;
import android.content.Intent;

public class GalleryHelper {

    public static void openGallery(Activity activity) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }
}
