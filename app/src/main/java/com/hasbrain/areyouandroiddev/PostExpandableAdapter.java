package com.hasbrain.areyouandroiddev;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chauphi90 on 23/11/2015.
 */
public class PostExpandableAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<String> categories;
    private HashMap<String, List<RedditPost>> hashMap;

    public PostExpandableAdapter(Activity context, List<String> categories, HashMap<String, List<RedditPost>> hashMap) {
        this.context = context;
        this.categories = categories;
        this.hashMap = hashMap;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String category = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.post_group_layout, parent, false);
        }
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
        tvCategory.setText(category);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.post_item_layout, parent, false);
        }

        // !!! use ViewHolder -> independent class
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
        TextView tvSticky = (TextView) convertView.findViewById(R.id.tvSticky);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvInfoPlus = (TextView) convertView.findViewById(R.id.tvInfoPlus);

        RedditPost post = (RedditPost) getChild(groupPosition, childPosition);
        tvScore.setText(post.getScore() + "");
        tvAuthor.setText(getAuthor(post));
        tvTitle.setText(post.getTitle());
        tvInfoPlus.setText(PostUtil.getInfoPlus(post));
        if (post.isStickyPost()) {
            tvSticky.setText("[M]");
            tvTitle.setTextColor(Color.parseColor("#387801"));
        } else {
            tvSticky.setText("");
            tvTitle.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    private Spanned getAuthor(RedditPost post) {
        String author = post.getAuthor();
        String subreddit = post.getSubreddit();
        Spanned spanned = Html.fromHtml(author + " <small>in</small> " + subreddit);

        return spanned;
    }

    @Override
    public int getGroupCount() {
        return this.categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getChildList(groupPosition).size();
    }

    public List<RedditPost> getChildList(int groupPosition) {
        String category = this.categories.get(groupPosition);
        return this.hashMap.get(category);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getChildList(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
