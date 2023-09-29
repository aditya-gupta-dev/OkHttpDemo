package com.example.okhttpdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.api.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String apiKey = "AIzaSyCRHuB3HRpfVy1YgdNCNHlSwZVIIQBw-3g";
    String url = "https://via.placeholder.com/600/771796";
    String baseUrl = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<JsonArray> call = service.getPhotos();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String[] titles = new String[response.body().size()];
                    String[] urls = new String[response.body().size()];
                    JsonArray json = response.body();

                    for (int i = 0; i < json.size(); i++) {
                        titles[i] = "title: " + json.get(i).getAsJsonObject().get("title").getAsString();
                        urls[i] = "url: " + json.get(i).getAsJsonObject().get("url").getAsString();
                    }

                    CustomAdapter customAdapter = new CustomAdapter((Context) MainActivity.this, titles, urls);
                    listView.setAdapter(customAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Request was unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

