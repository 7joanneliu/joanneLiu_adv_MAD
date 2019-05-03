package com.example.p2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RememberItem {
    private static final String PREFS_NAME = "ITEMS";
    private String name;
    private boolean packedr;

    RememberItem(String newItem) {
        name = newItem;
    }

    public static List<RememberItem> myItems = new ArrayList<RememberItem>() {{
    }};

    public String getName() {
        return name;
    }

    public boolean getPackedR(){
        return packedr;
    }

    public void getPackedR(boolean done){
        this.packedr = done;
    }


    public static void storeData(Context context) {
        //get access to a shared preferences file
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //create an editor to write to the shared preferences file
        SharedPreferences.Editor editor = sharedPrefs.edit();
        //add size to the editor
        editor.putInt("size", myItems.size());
        //add items to the editor
        for (int i = 0; i < myItems.size(); i++) {
            editor.putString("item" + i, myItems.get(i).getName());
        }

        editor.apply();
    }

    public static void loadData(Context context) {
        //get access to a shared preferences file
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //create an editor to read from the shared preferences file
        SharedPreferences.Editor editor = sharedPrefs.edit();

        int size = sharedPrefs.getInt("size", 0);

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                myItems.add(new RememberItem(sharedPrefs.getString("item" + i, "")));
            }
        }
    }
}

