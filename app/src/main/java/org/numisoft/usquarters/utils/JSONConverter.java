package org.numisoft.usquarters.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kukolka on 10/16/2016.
 */

public class JSONConverter {

    public static String convert(Context context, String source) {
        String json;
        try {
            InputStream is = context.getAssets().open(source);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
