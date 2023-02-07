package com.example.cookmeanapp;

import android.content.Context;

import com.example.cookmeanapp.Listeners.RandomRecipeResponseListener;
import com.example.cookmeanapp.model.RandomRecipeApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManger {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManger(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener){

        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.CallRandomRecipe(context.getString(R.string.api_key),"10");// this number indicates how many random recipes we will get
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


    private interface CallRandomRecipes{
        @GET("recipes/random")

        Call<RandomRecipeApiResponse> CallRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number

        );
    }
}
