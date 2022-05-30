package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.ModelClass.ArticlesItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>
{

    Context context;
    List<ArticlesItem> newsList;

    public NewsAdapter(Context context, List<ArticlesItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newslist,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

    ArticlesItem singlelistitem=newsList.get(position);
    String title="",description="",imageUrl="";
    imageUrl=singlelistitem.getUrlToImage();
    title=singlelistitem.getTitle();
    description=singlelistitem.getDescription();
    holder.newstitle.setText(title);
    holder.newsdesc.setText(description);
    String articleUrl=singlelistitem.getUrl();
    if(imageUrl!=null && imageUrl.length()!=0)
    Glide.with(context).load(imageUrl).into(holder.newsImage);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent browseIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
            context.startActivity(browseIntent);
        }
    });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        TextView newstitle,newsdesc;
        ImageView newsImage;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            newstitle=itemView.findViewById(R.id.title);
            newsdesc=itemView.findViewById(R.id.desc);
            newsImage=itemView.findViewById(R.id.newsImage);
        }
    }
}
