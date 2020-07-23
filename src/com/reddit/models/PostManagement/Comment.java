package com.reddit.models.PostManagement;

import com.reddit.models.UserManagement.User;

public class Comment extends Interaction {

    private String text;

    public Comment(User user, String text) {
        super(user);
        this.text = text;
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
