package org.numisoft.usquarters.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by kukolka on 3/14/2017.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }

}
