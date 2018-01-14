package com.vagapov.amir.serverinteractionexample;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private ProgressBar progressBar;
    private EditText editText;

    RestAPIforUser restAPIforUser;

    private static final String GIT_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        mInfoTextView = findViewById(R.id.tvLoad);
        progressBar = findViewById(R.id.progressBar);
        Button btnLoad  = findViewById(R.id.btnLoad);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickLoadUser();
            }
        });

        Button btn_load_repos = findViewById(R.id.load_repos);
        btn_load_repos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLoadRepos();
            }
        });
    }

    private void onClickLoadRepos() {
        mInfoTextView.setText("");
        Retrofit retrofit;

        try{
            retrofit = new Retrofit.Builder().baseUrl(GIT_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            restAPIforUser = retrofit.create(RestAPIforUser.class);
        }catch (Exception e){
            mInfoTextView.setText(getString(R.string.no_retrofit, e.getMessage()));
            return;
        }

        Call<List<RetrofitReposModel>> call = restAPIforUser.loadRepos(editText.getText().toString());
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            try{
                progressBar.setVisibility(View.VISIBLE);
                downloadReposRetrofit(call);
            }catch (Exception e){
                e.printStackTrace();
                mInfoTextView.setText(e.getMessage());
            }
        }else {
            Toast.makeText(this, R.string.try_to_connect, Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadReposRetrofit(Call<List<RetrofitReposModel>> call) {
        call.enqueue(new Callback<List<RetrofitReposModel>>() {
            @Override
            public void onResponse(Call<List<RetrofitReposModel>> call, Response<List<RetrofitReposModel>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();

                    RetrofitReposModel reposModel;

                    for (int i = 0; i < response.body().size(); i++) {
                        reposModel = response.body().get(i);
                        mInfoTextView.append("\n id = " + reposModel.getId() +
                                "\nRepos Name = " + reposModel.getReposName() +
                                "\nFull repos name = " + reposModel.getFullReposName() +
                                "\nDescription = " + reposModel.getDescription() +
                                "\n ------------");
                    }
                }else {
                    mInfoTextView.setText(getString(R.string.response_error, String.valueOf(response.code())));
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<RetrofitReposModel>> call, Throwable t) {
                mInfoTextView.setText(getString(R.string.on_failure, t.getMessage()));
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onClickLoadUser() {
        mInfoTextView.setText("");
        Retrofit retrofit;

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GIT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPIforUser = retrofit.create(RestAPIforUser.class);
        }catch (Exception e){
            mInfoTextView.setText(getString(R.string.no_retrofit, e.getMessage()));
            return;
        }

       retrofit2.Call<RetrofitModel> call = restAPIforUser.loadUser(editText.getText().toString());
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            try {
                progressBar.setVisibility(View.VISIBLE);
                downloadOneUrlRetrofit(call);
            }catch (IOException e){
                e.printStackTrace();
                mInfoTextView.setText(e.getMessage());
            }
        }else {
            Toast.makeText(this, getString(R.string.try_to_connect), Toast.LENGTH_SHORT).show();
        }

    }

    private void downloadOneUrlRetrofit(retrofit2.Call<RetrofitModel> call) throws IOException{
        call.enqueue(new retrofit2.Callback<RetrofitModel>() {
            @Override
            public void onResponse(retrofit2.Call<RetrofitModel> call, retrofit2.Response<RetrofitModel> response) {
                if(response.isSuccessful()){
                    RetrofitModel curRetrofitModel = response.body();

                    mInfoTextView.append("\n login = " + curRetrofitModel.getLogin() +
                    "\nId = " + curRetrofitModel.getId() +
                    "\nURI = " + curRetrofitModel.getAvatarUrl() +
                    "\n ------------");
                }else {
                    mInfoTextView.setText(getString(R.string.response_error, String.valueOf(response.code())));
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(retrofit2.Call<RetrofitModel> call, Throwable t) {
                mInfoTextView.setText(getString(R.string.on_failure, t.getMessage()));
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
