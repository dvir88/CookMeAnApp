package com.example.cookmeanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cookmeanapp.Adapters.RandomRecipeAdapter;
import com.example.cookmeanapp.Listeners.RandomRecipeResponseListener;
import com.example.cookmeanapp.model.RandomRecipeApiResponse;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManger manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManger(this);
        manager.getRandomRecipes(randomRecipeResponseListener);
        dialog.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String messege) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));

            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes);

            recyclerView.setAdapter(randomRecipeAdapter);

        }

        @Override
        public void didError(String messege) {
            Toast.makeText(MainActivity.this, messege, Toast.LENGTH_SHORT).show();
        }
    };
}