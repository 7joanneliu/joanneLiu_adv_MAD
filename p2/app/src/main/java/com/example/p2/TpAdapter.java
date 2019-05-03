package com.example.p2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class TpAdapter extends RealmRecyclerViewAdapter<TPList,TpAdapter.ViewHolder> {
    private ToPackFragment fragment;

    public TpAdapter(RealmResults<TPList> data, ToPackFragment fragment){
        super(data,true);
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.tplist_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TpAdapter.ViewHolder viewHolder, int i){
        TPList tpList = getItem(i);
        viewHolder.tpName.setText(tpList.getItem_name());
        viewHolder.hasPacked.setChecked(tpList.getPacked());
        viewHolder.hasPacked.setTag(i);

        viewHolder.hasPacked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isShown()){
                    int position = (Integer) viewHolder.hasPacked.getTag();
                    TPList tpList = getItem(position);
                    fragment.changeItemPacked(tpList.getIdTP());
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) viewHolder.hasPacked.getTag();
                TPList tpList = getItem(position);
                fragment.editItem(tpList.getIdTP());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tpName;
        CheckBox hasPacked;

        public ViewHolder(@NonNull View itemView){
        super(itemView);
        tpName=itemView.findViewById(R.id.tpTextView);
        hasPacked=itemView.findViewById(R.id.checkBox);
        }
    }

}
