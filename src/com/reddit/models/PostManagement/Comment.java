package com.reddit.models.PostManagement;

import com.reddit.models.UserManagement.User;

import java.util.ArrayList;

public class Comment extends Interaction {

    private String text;
    private int score;
    private ArrayList<Comment> replies;


    public Comment(User user, String text) {
        super(user);
        this.replies=new ArrayList<>();
        this.score=0;
        this.text = text;
    }
    public void reply(User user, String text){
        Comment comment = new Comment(user,text);
        replies.add(comment);
    }
    public void score(){
        ++score;
    }

    public ArrayList<Comment> getReplies() {
        return replies;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Comment comment(User user,Post post,String text){
        Comment comment= new Comment(user,text);
        post.addInteraction(comment);
        return comment;
        // add comment to post interaction
    }
    public void showComment(){
        System.out.println(getUser().getUsername() + " says: " + text);
    }

}
