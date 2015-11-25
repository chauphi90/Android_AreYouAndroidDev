package com.hasbrain.areyouandroiddev;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends PostListActivity {
    ExpandableListView expListView;
    BaseExpandableListAdapter adapter;
    List<String> categories = new ArrayList<>();
    List<RedditPost> listStickyPost = new ArrayList<>();
    List<RedditPost> listNormalPost = new ArrayList<>();
    HashMap<String, List<RedditPost>> hashMap = new HashMap<>();
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void displayPostList(final List<RedditPost> postList) {
        initData(postList);
        expListView = (ExpandableListView) findViewById(R.id.listView);

        // Footer
        footerView = getLayoutInflater().inflate(R.layout.footer_layout,null);
        expListView.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPost("https://www.reddit.com/r/androiddev/");
            }
        });

        adapter = new PostExpandableAdapter(this, categories, hashMap);
        expListView.setAdapter(adapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String category = categories.get(groupPosition);
                List<RedditPost> postList = hashMap.get(category);
                String url = postList.get(childPosition).getUrl();
                showPost(url);
                return false;
            }
        });
    }

    private void initData(List<RedditPost> postList) {
        categories.add("Sticky posts");
        categories.add("Normal posts");
        for (RedditPost post : postList) {
            if (post.isStickyPost()) {
                listStickyPost.add(post);
            } else {
                listNormalPost.add(post);
            }
        }
        hashMap.put(categories.get(0), listStickyPost);
        hashMap.put(categories.get(1), listNormalPost);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }
}
