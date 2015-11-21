package com.hasbrain.areyouandroiddev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
        Gson gson = gsonBuilder.create();
        InputStream is = null;
        try {
            is = getAssets().open(DATA_JSON_FILE_NAME);
            feedDataStore = new FileBasedFeedDataStore(gson, is);
            feedDataStore.getPostList(new FeedDataStore.OnRedditPostsRetrievedListener() {
                @Override
                public void onRedditPostsRetrieved(List<RedditPost> postList, Exception ex) {
                    displayPostList(postList);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void displayPostList(final List<RedditPost> postList) {
        BaseAdapter adapter = new PostArrayAdapter(this, postList);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > postList.size() - 1) { // For footer
                    showPost("https://www.reddit.com/r/androiddev/");
                } else {
                    showPost(postList.get(position).getUrl());
                }
            }

        };

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ListView listView = (ListView) findViewById(R.id.listView);

            // Footer of listview
            View footerView = getLayoutInflater().inflate(R.layout.footer_layout, null, false);
            listView.addFooterView(footerView);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);
        } else {
            GridView gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(listener);
        }
    }

    private void showPost(String url) {
        Bundle data = new Bundle();
        data.putString("url", url);
        Intent intent = new Intent(this, PostViewActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }

}
