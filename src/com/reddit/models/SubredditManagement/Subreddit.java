package com.reddit.models.SubredditManagement;

import com.reddit.models.exceptions.SubredditAlreadyExist;
import com.reddit.models.exceptions.SubredditNotFound;

import java.util.ArrayList;
import java.util.Date;

public class Subreddit {
    private String name;
    private Date date;
    private static ArrayList<Subreddit> subreddits =new ArrayList<>();


    public Subreddit(String name) {
        this.date = new Date();
        this.name = name;

    }
    public static void addSubreddit(Subreddit subreddit){
        subreddits.add(subreddit);
    }

    public static Subreddit createSubreddit (String name ) throws SubredditAlreadyExist{
        for (Subreddit subreddit :subreddits) {
            if (subreddit.getName().equals(name)){
                throw new SubredditAlreadyExist("Subreddit already exist");
            }
        }

        Subreddit subreddit = new Subreddit(name);
        addSubreddit(subreddit);
        return subreddit;
    }




    public static Subreddit search(String name) throws SubredditNotFound{
        for ( Subreddit subreddit: subreddits) {
            if(subreddit.getName().equals(name)){
                return subreddit;
            }
        }
        throw new SubredditNotFound("Subreddit not found");
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
