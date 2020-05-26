package com.example.wordpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private ArrayList<Model> list;
    private RecyclerViewAdapter adapter;
    private String baseURL="http://blog.finakya.com";
    public static  List<WPPost> mListPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);

        list=new ArrayList<Model>();

        getRetrofit();

        adapter=new RecyclerViewAdapter(list, MainActivity.this);
        recyclerView.setAdapter(adapter);

    }

    public void getRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitArrayApi service= retrofit.create((RetrofitArrayApi.class));
        Call<List<WPPost>> call=service.getPostInfo();

        call.enqueue(new Callback<List<WPPost>>() {
            @Override
            public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {
                Log.e("mainactivity", "response"+response.body());
                mListPost=response.body();

                for (int i=0;i<response.body().size();i++){
                    Log.e("main", "title"+ response.body().get(i).getTitle().getRendered() + " " + response.body().get(i).getId());

                    String tempdetails=response.body().get(i).getExcerpt().getRendered().toString();
                    tempdetails = tempdetails.replace("<p>","");
                    tempdetails=tempdetails.replace("</p>","");
                    tempdetails=tempdetails.replace("[&hellip;]","");
                    String img = response.body().get(i).getJetpackFeaturedMediaUrl().toString();



                    list.add( new Model(response.body().get(i).getTitle().getRendered(), tempdetails, img));


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<WPPost>> call, Throwable t) {

            }
        });

    }
    public static List<WPPost> getList()
    {
        return mListPost;
    }
}
