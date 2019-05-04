package com.example.finalandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get realm instance
        realm=Realm.getDefaultInstance();

        //get all saved objects
        RealmResults<Restaurant> restaurants =realm.where(Restaurant.class).findAll();
        restaurantAdapter=new RestaurantAdapter(restaurants);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        //assign adapter to recycler view
        recyclerView.setAdapter(restaurantAdapter);

        //set layout manger to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //divider line between objects
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create verrtical linear layouts for edit text
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                //create edit texts to add to layout
                final EditText restaurantEditText = new EditText(MainActivity.this);
                restaurantEditText.setHint("restaurant name");
                layout.addView(restaurantEditText);

//                final EditText urlEditText = new EditText(MainActivity.this);
//                urlEditText.setHint("restaurant url");
//                layout.addView(urlEditText);

                //alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Add Restaurant");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get restaurant name
                        final String newRestaurantName = restaurantEditText.getText().toString();
//                        final String newURL = urlEditText.getText().toString();

                        if(!newRestaurantName.isEmpty()){
//                            addRestaurant(UUID.randomUUID().toString(), newRestaurantName,newURL);
                            addRestaurant(UUID.randomUUID().toString(), newRestaurantName);
                        }
                    }
                });
                dialog.setNegativeButton("Cancel",null);
                dialog.show();
            }
        });
    }

    public void addRestaurant(final String newId, final String newRestaurant){
        //start realm write transaction
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //create realm object
                Restaurant newrestaurant = realm.createObject(Restaurant.class,newId);
                newrestaurant.setRestaurant_name(newRestaurant);
//                newrestaurant.setUrl_link(newURL);
            }
        });
    }

    public void changeRestaurant(final String itemId, final String restaurant_name){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Restaurant restaurant = realm.where(Restaurant.class).equalTo("id", itemId).findFirst();
                restaurant.setRestaurant_name(restaurant_name);
//                restaurant.setUrl_link(url_name);
            }
        });
    }

    private void deleteItem(final String itemId){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Restaurant.class).equalTo("id",itemId).findFirst().deleteFromRealm();
            }
        });
    }

    public void editRestaurant(final String itemId){
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final Restaurant restaurant = realm.where(Restaurant.class).equalTo("id", itemId).findFirst();

        //create edit text and add layout
        final EditText restaurantEditText = new EditText(MainActivity.this);
        restaurantEditText.setText(restaurant.getRestaurant_name());
        layout.addView(restaurantEditText);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Edit Restaurant");
        dialog.setView(layout);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save and edit item
                final String updatedRestaurantName = restaurantEditText.getText().toString();
                if (!updatedRestaurantName.isEmpty()){
                    changeRestaurant(itemId, updatedRestaurantName);
                }
            }
        });
        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete item
                deleteItem(itemId);
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //close the Realm instance when the Activity exits
        realm.close();
    }
}
