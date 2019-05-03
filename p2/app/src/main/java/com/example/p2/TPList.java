package com.example.p2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TPList extends RealmObject {
    @Required
    @PrimaryKey
    private String idTP;
    private String item_name;
    private boolean packed;

    public String getIdTP(){

        return idTP;
    }

    public void setIdTP(String idTP){
        this.idTP = idTP;
    }

    public String getItem_name(){
        return item_name;
    }

    public void setItem_name(String item_name){
        this.item_name = item_name;
    }

    public boolean getPacked(){
        return packed;
    }

    public void getPacked(boolean done){
        this.packed = done;
    }
}
