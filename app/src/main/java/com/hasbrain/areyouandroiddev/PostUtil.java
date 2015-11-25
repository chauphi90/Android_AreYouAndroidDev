package com.hasbrain.areyouandroiddev;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Date;

/**
 * Created by chauphi90 on 25/11/2015.
 */
public class PostUtil {
    public static String getInfoPlus(RedditPost post) {
        int cmtCount = post.getCommentCount();
        String cmtStr = cmtCount + ((cmtCount > 1) ? " Comments" : " Comment");
        String domain = post.getDomain();
        String createdTime = getCreatedTime(post.getCreatedUTC());
        return cmtStr + " • " + domain + " • " + createdTime;

    }

    public static String getCreatedTime(long num) {
        String result="";
        Date currentDate = new Date();
        long diff = currentDate.getTime() - num*1000;// ? data.json

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
