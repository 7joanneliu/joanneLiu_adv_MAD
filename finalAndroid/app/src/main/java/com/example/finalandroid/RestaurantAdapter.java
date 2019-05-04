package com.example.finalandroid;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class RestaurantAdapter extends RealmRecyclerViewAdapter<Restaurant, RestaurantAdapter.ViewHolder> {
    private MainActivity activity;

    public RestaurantAdapter(RealmResults<Restaurant> data){
        super(data, true);
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantAdapter.ViewHolder viewHolder, int i){
        Restaurant restaurant = getItem(i);
        viewHolder.restaurantName.setText(restaurant.getRestaurant_name());
//        viewHolder.has
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = (Integer)viewHolder.restaurantName.getTag();
//                Restaurant restaurant = getItem(position);
//                activity.editRestaurant(restaurant.getId());
                Snackbar.make(v, "Have code for delete and edit, ran out of time on position of item/object selected in data.", Snackbar.LENGTH_LONG) .setAction("Action", null).show();

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView restaurantName;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            restaurantName=itemView.findViewById(R.id.restaurantTextView);
        }
    }
}

