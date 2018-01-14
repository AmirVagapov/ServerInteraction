package com.vagapov.amir.serverinteractionexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitModel {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    public String getLogin(){
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
