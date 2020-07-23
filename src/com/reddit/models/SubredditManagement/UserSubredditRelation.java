package com.reddit.models.SubredditManagement;

import com.reddit.models.UserManagement.User;
import com.reddit.models.enums.TypeOfRelation;

import java.time.LocalDateTime;
import java.util.Date;

public class UserSubredditRelation {
    private TypeOfRelation typeOfRelation;
    private Subreddit subreddit;
    private Date date;



    private UserSubredditRelation(TypeOfRelation typeOfRelation, Subreddit subreddit) {
        this.typeOfRelation = typeOfRelation;
        this.subreddit = subreddit;
        this.date = new Date();

    }
    public static UserSubredditRelation createUserSubredditRelation(TypeOfRelation typeOfRelation,Subreddit subreddit){

        return new UserSubredditRelation(typeOfRelation,subreddit);
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public TypeOfRelation getTypeOfRelation() {
        return typeOfRelation;
    }

    public Date getDate() {
        return date;
    }
}
