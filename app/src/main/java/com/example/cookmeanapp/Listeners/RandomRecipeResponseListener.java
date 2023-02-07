package com.example.cookmeanapp.Listeners;

import com.example.cookmeanapp.model.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String messege) ;
    void didError(String messege);

}
