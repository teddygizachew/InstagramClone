package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jjqT9GSsHEAnOZBF3fdJNhrsfvlOkvU1sCoyJDZU")
                .clientKey("Acww3XfHzkwNi1effhh1RQVCf8G6z8Y3btdxRE9R")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
