package com.reddit.models.PostManagement;

import com.reddit.models.SubredditManagement.Subreddit;
import com.reddit.models.SubredditManagement.UserSubredditRelation;
import com.reddit.models.UserManagement.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Post {
    private String text;
    private Subreddit subreddit;
    private ArrayList<Interaction> interactions;
    private Date createdDate ;


    private Post(String text,Subreddit subreddit) {
        this.text = text;
        this.createdDate = new Date();
        this.subreddit = subreddit;
        this.interactions = new ArrayList<>();

    }
    public static Post createPost(String text,Subreddit subreddit){
        return new Post(text,subreddit);
    }
    public void showPost(){
        System.out.println("subreddit: "+ subreddit.getName() );
        System.out.println(text);
        System.out.println( " Score: " +this.score()+"\t" + "Comments: " +this.countComments()  );
    }


    private static ArrayList<Post> sortPostsByLike(ArrayList<Post> posts){
        posts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return Integer.compare(post2.score(), post1.score());
            }
        });
        return posts;
    }

    public static ArrayList<Post> subredditPost(Subreddit subreddit){

        ArrayList<Post> postSubreddits = new ArrayList<>();
        for (User user: User.getUsers()) {
            for (Post post: user.getPosts()) {
                if (post.getSubreddit().getName().equals(subreddit.getName())){
                    postSubreddits.add(post);
                }

            }
        }


        return sortPostsByLike(postSubreddits);
    }
    public static ArrayList<Post> postsForUser(User user){
        ArrayList<Post> postsForUser = new ArrayList<>();

        for (UserSubredditRelation userSubredditRelation: user.getUserSubredditRelations()) {
            postsForUser.addAll(subredditPost(userSubredditRelation.getSubreddit()));
        }

        return sortPostsByLike(postsForUser);

    }
    public static ArrayList<Post> allPosts(){

        ArrayList<Post> posts = new ArrayList<>();

        for (User user: User.getUsers()) {
            posts.addAll(user.getPosts());
        }
        return sortPostsByLike(posts);

    }

    public static ArrayList<Post> userPosts(User user){
        ArrayList<Post> userPosts = new ArrayList<>(user.getPosts());
        return sortPostsByLike(userPosts);
    }


    public int score(){
        int count = 0;
        for (Interaction interaction:interactions) {
            if (interaction instanceof Score){
                ++count;
            }

        }
        return count;

    }

    public int countComments(){
        int count = 0;
        for (Interaction interaction:interactions) {
            if (interaction instanceof Comment){
                ++count;
            }

        }
        return count;

    }



    public void addInteraction(Interaction interaction){
        interactions.add(interaction);

    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

}
