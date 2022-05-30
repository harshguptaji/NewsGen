package com.example.news;


import com.example.news.ModelClass.MainResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {
    @GET("/v2/top-headlines?country=in&apiKey=287bfe552b7648c8bb7c8c35cb50fd7e")
    Call<MainResponse> getNewsData(@Query("category") String category);
}
