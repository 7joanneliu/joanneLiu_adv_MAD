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
public class ToPackFragment extends Fragment {
    private Realm realm;
    private TpAdapter tpAdapter;

    public ToPackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewT = inflater.inflate(R.layout.fragment_to_pack, container, false);

        //get realm instance
        realm = Realm.getDefaultInstance();

        //get saved objects
        RealmResults<TPList> tpLists = realm.where(TPList.class).findAll();

        tpAdapter = new TpAdapter(tpLists, this);

        RecyclerView recyclerView = viewT.findViewById(R.id.recyclerView);

        //assign adapter to recycler view
        recyclerView.setAdapter(tpAdapter);

        //set layout manager on recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //divider line between rows
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));


        FloatingActionButton fab = viewT.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vert lin layout for edit text
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                //create edit texts and add to layout
                final EditText itemEditText = new EditText(getActivity());
                itemEditText.setHint("item name");
                layout.addView(itemEditText);

                //create alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Add Item");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get item name
                        final String newItemName = itemEditText.getText().toString();

                        if (!newItemName.isEmpty()){
                            addTPItem(UUID.randomUUID().toString(), newItemName);
                        }
                    }
                });
                dialog.setNegativeButton("Cancel",null);
                dialog.show();
            }
        });

        // Inflate the layout for this fragment
        return viewT;
    }

    public void addTPItem(final String newId, final String newTPItem){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TPList newtpitem = realm.createObject(TPList.class,newId);
                newtpitem.setItem_name(newTPItem);
            }
        });
    }

    public void changeItemPacked(final String itemId){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TPList tpList = realm.where(TPList.class).equalTo("idTP", itemId).findFirst();
                tpList.getPacked(!tpList.getPacked());
            }
        });
    }

    public void editItem(final String itemId){
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        final TPList tpList = realm.where(TPList.class).equalTo("idTP", itemId).findFirst();

        //create edit text and add layout
        final EditText itemEditText = new EditText(getActivity());
        itemEditText.setText(tpList.getItem_name());
        layout.addView(itemEditText);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Edit Item");
        dialog.setView(layout);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save and edit item
                final String updatedItemName = itemEditText.getText().toString();
                if (!updatedItemName.isEmpty()){
                    changeTpItem(itemId, updatedItemName);
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

    private void changeTpItem(final String itemId, final String item_name){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TPList tpList = realm.where(TPList.class).equalTo("idTP",itemId).findFirst();
                tpList.setItem_name(item_name);
            }
        });
    }

    private void deleteItem(final String itemId){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(TPList.class).equalTo("idTP",itemId).findFirst().deleteFromRealm();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //close the Realm instance when the Activity exits
        realm.close();
    }
}
