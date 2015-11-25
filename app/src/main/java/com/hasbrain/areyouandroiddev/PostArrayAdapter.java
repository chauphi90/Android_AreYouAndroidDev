package com.hasbrain.areyouandroiddev;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Date;
import java.util.List;

/**
 * Created by chauphi90 on 14/11/2015.
 */
public class PostArrayAdapter extends ArrayAdapter<RedditPost> {
    private Activity context;
    private List<RedditPost> list;
    private int orientation;
    private int defaultTextColor;

    public PostArrayAdapter(Activity context, List<RedditPost> list) {
        super(context, R.layout.post_item_layout, list);
        this.context = context;
        this.list = list;
        initConfigVars();
    }

    private void initConfigVars() {
        orientation = context.getResources().getConfiguration().orientation;

        // Get text color from current theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(android.R.attr.textColor, typedValue, true);
        defaultTextColor = typedValue.data;
    }

    private class ViewHolder {
        private TextView tvScore;
        private TextView tvAuthor;
        private TextView tvSticky;
        private TextView tvTitle;
        private TextView tvInfoPlus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.post_item_layout, parent, false);
            ViewHolder viewHolder = createViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            rowView = convertView;
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        RedditPost post = list.get(position);
        setPostContent(holder, post);

        return rowView;
    }

    private ViewHolder createViewHolder(View rowView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvScore = (TextView) rowView.findViewById(R.id.tvScore);
        viewHolder.tvAuthor = (TextView) rowView.findViewById(R.id.tvAuthor);
        viewHolder.tvSticky = (TextView) rowView.findViewById(R.id.tvSticky);
        viewHolder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        viewHolder.tvInfoPlus = (TextView) rowView.findViewById(R.id.tvInfoPlus);
        return viewHolder;
    }

    private void setPostContent(ViewHolder viewHolder, RedditPost post) {
        viewHolder.tvScore.setText(post.getScore() + "");
        viewHolder.tvAuthor.setText(getAuthor(post));
        viewHolder.tvTitle.setText(post.getTitle());
        viewHolder.tvInfoPlus.setText(PostUtil.getInfoPlus(post));

        if (post.isStickyPost()) {
            viewHolder.tvSticky.setText("[M]");
            viewHolder.tvTitle.setTextColor(Color.parseColor("#387801"));
        } else {
            viewHolder.tvSticky.setText("");
            viewHolder.tvTitle.setTextColor(defaultTextColor);
        }
    }

    private Spanned getAuthor(RedditPost post) {
        String author = post.getAuthor();
        String subreddit = post.getSubreddit();
        Spanned spanned;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanned = Html.fromHtml(author);
        } else {
            spanned = Html.fromHtml(author + " <small>in</small> " + subreddit);

        }
        return spanned;
    }

//    public String getInfoPlus(RedditPost post) {
//        int cmtCount = post.getCommentCount();
//        String cmtStr = cmtCount + ((cmtCount > 1) ? " Comments" : " Comment");
//        String domain = post.getDomain();
//        String createdTime = this.getCreatedTime(post.getCreatedUTC());
//        return cmtStr + " • " + domain + " • " + createdTime;
//    }
//
//    // Show time when create a post
//    public String getCreatedTime(long num) {
//        String result = "";
//        Date currentDate = new Date();
//        long diff = currentDate.getTime() - num;
//
//        long aSecond = 1000;
//        long aMinute = 1000 * 60;
//        long aHour = aMinute * 60;
//        long aDay = aHour * 24;
//        long aMonth = aDay * 30;
//        long aYear = aMonth * 12;
//
//        if (diff < aMinute * 2) {
//            result = diff / aSecond + " seconds";
//        } else if (diff < aHour * 2) {
//            result = diff / aMinute + " minutes";
//        } else if (diff < aDay * 2) {
//            result = diff / aHour + " hours";
//        } else if (diff < aMonth * 2) {
//            result = diff / aDay + " days";
//        } else if (diff < aYear * 2) {
//            result = diff / aMonth + " months";
//        } else {
//            result = diff / aYear + " years";
//        }
//
//        return result;
//    }
}
