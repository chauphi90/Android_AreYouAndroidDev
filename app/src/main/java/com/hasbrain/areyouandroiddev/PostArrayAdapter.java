package com.hasbrain.areyouandroiddev;

import android.app.Activity;
import android.graphics.Color;
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


    public PostArrayAdapter(Activity context, List<RedditPost> list) {
        super(context, R.layout.post_item_layout, list);
        this.context = context;
        this.list = list;

    }

    private class ViewHolder {
        private TextView tvScore;
        private TextView tvAuthor;
        private TextView tvSubreddit;
        private TextView tvSticky;
        private TextView tvTitle;
        private TextView tvCommentCount;
        private TextView tvDomain;
        private TextView tvCreatedUTC;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.post_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvScore = (TextView) rowView.findViewById(R.id.tvScore);
            viewHolder.tvAuthor = (TextView) rowView.findViewById(R.id.tvAuthor);
            viewHolder.tvSubreddit = (TextView) rowView.findViewById(R.id.tvSubreddit);
            viewHolder.tvSticky = (TextView) rowView.findViewById(R.id.tvSticky);
            viewHolder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
            viewHolder.tvCommentCount = (TextView) rowView.findViewById(R.id.tvCommentCount);
            viewHolder.tvDomain = (TextView) rowView.findViewById(R.id.tvDomain);
            viewHolder.tvCreatedUTC = (TextView) rowView.findViewById(R.id.tvCreatedUTC);

            rowView.setTag(viewHolder);
        } else {
            rowView = convertView;
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        RedditPost post = list.get(position);
        holder.tvScore.setText(post.getScore() + "");
        holder.tvAuthor.setText(post.getAuthor());
        holder.tvSubreddit.setText(post.getSubreddit());
        holder.tvTitle.setText(post.getTitle());
        holder.tvCommentCount.setText(showCommentCount(post.getCommentCount()));
        holder.tvDomain.setText(post.getDomain());
        holder.tvCreatedUTC.setText(getTime(post.getCreatedUTC() * 1000));

        if (post.isStickyPost()) {
            holder.tvSticky.setText("[M]");
            holder.tvTitle.setTextColor(Color.parseColor("#387801"));
        } else {
            holder.tvSticky.setText("");
            holder.tvTitle.setTextColor(Color.BLACK);
        }

        return rowView;
    }

    public String showCommentCount(int count) {
        return (count < 2) ? count + " comment" : count + " comments";
    }

    // Show time when create a post
    public String getTime(long num) {
        String result = "";
        Date currentDate = new Date();
        long diff = currentDate.getTime() - num;

        long aSecond = 1000;
        long aMinute = 1000 * 60;
        long aHour = aMinute * 60;
        long aDay = aHour * 24;
        long aMonth = aDay * 30;
        long aYear = aMonth * 12;

        if (diff < aMinute * 2) {
            result = diff / aSecond + " seconds";
        } else if (diff < aHour * 2) {
            result = diff / aMinute + " minutes";
        } else if (diff < aDay * 2) {
            result = diff / aHour + " hours";
        } else if (diff < aMonth * 2) {
            result = diff / aDay + " days";
        } else if (diff < aYear * 2) {
            result = diff / aMonth + " months";
        } else {
            result = diff / aYear + " years";
        }

        return result;
    }
}
