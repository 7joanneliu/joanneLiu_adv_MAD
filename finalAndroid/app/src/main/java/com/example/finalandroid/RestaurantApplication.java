package com.example.finalandroid;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.app.Application;

public class RestaurantApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        //initialize Realm
        Realm.init(this);
        //define the configuration for realm
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        //set the default realm configuration
//        Realm.deleteRealm(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }
}
