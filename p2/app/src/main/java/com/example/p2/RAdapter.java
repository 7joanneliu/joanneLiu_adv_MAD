package com.example.p2;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    private RememberFragment fragment;
    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView textViewR;
        CheckBox hasPacked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewR = itemView.findViewById(R.id.textViewR);
            hasPacked=itemView.findViewById(R.id.checkBox);
        }
    }

    private List<RememberItem> mItems;

    public RAdapter(List<RememberItem> items){
        mItems = items;
    }

    @NonNull
    @Override
    public RAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.rlist_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RAdapter.ViewHolder viewHolder, int i) {
        final RememberItem item = mItems.get(i);
        viewHolder.textViewR.setText(item.getName());
        viewHolder.hasPacked.setChecked(item.getPackedR());
        viewHolder.hasPacked.setTag(i);

        viewHolder.hasPacked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isShown()){
                    int position = (Integer) viewHolder.hasPacked.getTag();
                    RememberItem item = mItems.get(position);
                }
            }
        });

//        /long click listener
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });


        //context menu
        viewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                //set the menu title
                menu.setHeaderTitle("Delete " + item.getName());
                //add the choices to the menu
                menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        removeItem(viewHolder.getAdapterPosition());
                        return false;
                    }
                });
                menu.add(2, 2, 2, "No");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItem(String newItem){
        RememberItem.myItems.add(new RememberItem(newItem));
        notifyItemInserted(getItemCount());
    }

    public void removeItem(int itemPosition){
        RememberItem.myItems.remove(itemPosition);
        notifyItemRemoved(itemPosition);
    }
}

//
//import io.realm.RealmRecyclerViewAdapter;
//import io.realm.RealmResults;
//
//public class RAdapter extends RealmRecyclerViewAdapter<RemList,RAdapter.ViewHolder> {
//    private RememberFragment fragment;
//
//    public RAdapter(RealmResults<RemList> data, RememberFragment fragment){
//        super(data,true);
//        this.fragment=fragment;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
//        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
//        View itemView = layoutInflater.inflate(R.layout.rlist_item,viewGroup,false);
//        ViewHolder viewHolder = new ViewHolder(itemView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final RAdapter.ViewHolder viewHolder, int i){
//        RemList rList = getItem(i);
//        viewHolder.rName.setText(rList.getItem_nameR());
//        viewHolder.hasPacked.setChecked(rList.getPackedR());
//        viewHolder.hasPacked.setTag(i);
//
//        viewHolder.hasPacked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.isShown()){
//                    int position = (Integer) viewHolder.hasPacked.getTag();
//                    RemList rList = getItem(position);
//                    fragment.changeItemPacked(rList.getIdR());
//                }
//            }
//        });
//
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = (Integer) viewHolder.hasPacked.getTag();
//                RememberItem rList = getItem(position);
//                fragment.editItem(rList.getIdR());
//            }
//        });
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView rName;
//        CheckBox hasPacked;
//
//        public ViewHolder(@NonNull View itemView){
//            super(itemView);
//            rName=itemView.findViewById(R.id.textViewR);
//            hasPacked=itemView.findViewById(R.id.checkBox);
//        }
//    }
//
//}
