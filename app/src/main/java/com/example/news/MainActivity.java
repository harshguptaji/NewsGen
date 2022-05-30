package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.news.ModelClass.ArticlesItem;
import com.example.news.ModelClass.MainResponse;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView newsRecycler;
    private Retrofit retrofit;
    private ActionBar actionBar;
    private NewsInterface newsInterface;
    private final String generalCategory="general",healthCategory="health",sportsCategory="sports",TechCategory="technology",BusCategory="business";
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecycler=findViewById(R.id.newsRecyclerView);
        progressBar=findViewById(R.id.progressBar3);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(getResources().getColor(R.color.action_bar)));
        actionBar = getSupportActionBar();

        actionBar.setTitle("General");
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        setNewsRetrofit("general");
        setNavigationListner();







    }

    private void setNavigationListner() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.general:
                        //actionBar.setTitle("General");
                        setNewsRetrofit("general");

                        return true;
                    case R.id.tech:
                       // actionBar.setTitle("Technology");
                        setNewsRetrofit("technology");
                        return true;
                    case R.id.business:
                        //actionBar.setTitle("Business");
                        setNewsRetrofit("business");
                        return true;
                    case R.id.sports:
                        //actionBar.setTitle("Sports");
                        setNewsRetrofit("sports");
                        return true;
                    case R.id.health:
                        //actionBar.setTitle("Health");
                        setNewsRetrofit("health");
                        return true;
                    default:
                        return false;


                }
            }
        });
    }

    private void setNewsRetrofit(String category) {
        char ch=category.charAt(0);
        ch=Character.toUpperCase(ch);
        String st=category.substring(1);


        actionBar.setTitle(ch+st);
        progressBar.setVisibility(View.VISIBLE);
        newsRecycler.setVisibility(View.INVISIBLE);
        retrofit =new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build();
        newsInterface=retrofit.create(NewsInterface.class);

        Call<MainResponse> responseCall=newsInterface.getNewsData(category);
        responseCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful())
                {
                    MainResponse mainResponse=response.body();
                    List<ArticlesItem> news=mainResponse.getArticles();
                    newsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    NewsAdapter adapter=new NewsAdapter(MainActivity.this,news);
                    newsRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    newsRecycler.setVisibility(View.VISIBLE);

                }else
                {

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

    }


}