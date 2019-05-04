package com.example.finalandroid;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Restaurant extends RealmObject{
    @Required
    @PrimaryKey
    private String id;
    private String restaurant_name;
    private String url_link;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getRestaurant_name(){
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant){
        this.restaurant_name = restaurant;
    }

    public String getUrl_link(){
        return url_link;
    }

    public void setUrl_link(String url){
        this.url_link=url;
    }
}
