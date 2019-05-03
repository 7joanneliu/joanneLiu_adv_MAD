package com.example.p2;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;



import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.v7.widget.LinearLayoutManager.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class RememberFragment extends Fragment {
//    private Realm realm;
//    private com.example.p2.RAdapter rAdapter;

    public RememberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        RecyclerView recyclerViewR;
//        RAdapter RAdapter;

        View viewR = inflater.inflate(R.layout.fragment_remember, container, false);

        RecyclerView recyclerViewR = viewR.findViewById(R.id.recyclerViewR);

        //define an adapter
        final RAdapter rAdapter = new RAdapter(RememberItem.myItems);

        //assign adapter to recycler view
        recyclerViewR.setAdapter(rAdapter);

        //set layout manager on recycler view
        recyclerViewR.setLayoutManager(new LinearLayoutManager(getActivity()));

        //divider line between rows
        recyclerViewR.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));


        //load data
        if(RememberItem.myItems.isEmpty()) {
            RememberItem.loadData(getActivity());
        }

        //fab
        FloatingActionButton fab = viewR.findViewById(R.id.fabR);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //create alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                //create edit text
                final EditText edittext = new EditText(getActivity());
                //add edit text to dialog
                dialog.setView(edittext);
                //set dialog title
                dialog.setTitle("Add Item");
                //sets OK action
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //get item entered
                        String newItem = edittext.getText().toString();
                        if (!newItem.isEmpty()) {
                            // add item
                            rAdapter.addItem(newItem);
                        }
                    }
                });
                //sets cancel action
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // cancel
                    }
                });
                //present alert dialog
                dialog.show();
            }
        });
        return viewR;
    }


    @Override
    public void onPause() {
        super.onPause();
        //save data
        RememberItem.storeData(getActivity());
    }
//
//        //get realm instance
//        realm = Realm.getDefaultInstance();
//
//        //get saved objects
//        RealmResults<RemList> rLists = realm.where(RemList.class).findAll();
//
//        rAdapter = new com.example.p2.RAdapter(rLists, this);
//
//        RecyclerView recyclerView = viewT.findViewById(R.id.recyclerView);
//
//        //assign adapter to recycler view
//        recyclerView.setAdapter(rAdapter);
//
//        //set layout manager on recycler view
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        //divider line between rows
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));
//
//
//        FloatingActionButton fab = viewT.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //vert lin layout for edit text
//                LinearLayout layout = new LinearLayout(getActivity());
//                layout.setOrientation(LinearLayout.VERTICAL);
//
//                //create edit texts and add to layout
//                final EditText itemEditText = new EditText(getActivity());
//                itemEditText.setHint("item name");
//                layout.addView(itemEditText);
//
//                //create alert dialog
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setTitle("Add Item");
//                dialog.setView(layout);
//                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //get item name
//                        final String newItemName = itemEditText.getText().toString();
//
//                        if (!newItemName.isEmpty()){
//                            addRItem(UUID.randomUUID().toString(), newItemName);
//                        }
//                    }
//                });
//                dialog.setNegativeButton("Cancel",null);
//                dialog.show();
//            }
//        });
//
//        // Inflate the layout for this fragment
//        return viewR
//}
//
//    public void addRItem(final String newId, final String newRItem){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RemList newritem = realm.createObject(RemList.class,newId);
//                newritem.setItem_nameR(newRItem);
//            }
//        });
//    }
//
//    public void changeItemPacked(final String itemId){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RemList rList = realm.where(RemList.class).equalTo("idR", itemId).findFirst();
//                rList.getPackedR(!rList.getPackedR());
//            }
//        });
//    }
//
//    public void editItem(final String itemId){
//        LinearLayout layout = new LinearLayout(getActivity());
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        final RemList rList = realm.where(RemList.class).equalTo("idR", itemId).findFirst();
//
//        //create edit text and add layout
//        final EditText itemEditText = new EditText(getActivity());
//        itemEditText.setText(rList.getItem_nameR());
//        layout.addView(itemEditText);
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        dialog.setTitle("Edit Item");
//        dialog.setView(layout);
//        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //save and edit item
//                final String updatedItemName = itemEditText.getText().toString();
//                if (!updatedItemName.isEmpty()){
//                    changeRItem(itemId, updatedItemName);
//                }
//            }
//        });
//        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //delete item
//                deleteItem(itemId);
//            }
//        });
//        dialog.create();
//        dialog.show();
//    }
//
//    private void changeRItem(final String itemId, final String item_name){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RemList rList = realm.where(RemList.class).equalTo("idR",itemId).findFirst();
//                rList.setItem_nameR(item_name);
//            }
//        });
//    }
//
//    private void deleteItem(final String itemId){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.where(RemList.class).equalTo("idR",itemId).findFirst().deleteFromRealm();
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //close the Realm instance when the Activity exits
//        realm.close();
//    }
}
