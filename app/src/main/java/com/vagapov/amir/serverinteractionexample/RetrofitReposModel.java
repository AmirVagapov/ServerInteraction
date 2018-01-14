package com.vagapov.amir.serverinteractionexample;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitReposModel {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String reposName;

    @SerializedName("full_name")
    @Expose
    private String fullReposName;


    @SerializedName("description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public String getReposName() {
        return reposName;
    }

    public String getFullReposName() {
        return fullReposName;
    }

    public String getDescription() {
        return description;
    }
}
