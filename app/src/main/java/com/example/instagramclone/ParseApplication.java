package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    //Initializes Parse SDK to as soon as application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("acvmnxEttTimMaGEWG8AKrKppE87Uq7f8UVqahTR")
                .clientKey("bEp3l0GWNskbwqxQty8CZpb6del7jLlVyO9e9XBD")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
