package com.reddit.models.UserManagement;

import com.reddit.models.PostManagement.Interaction;
import com.reddit.models.PostManagement.Post;
import com.reddit.models.SubredditManagement.Subreddit;
import com.reddit.models.SubredditManagement.UserSubredditRelation;
import com.reddit.models.enums.TypeOfRelation;
import com.reddit.models.exceptions.UserNotFound;
import com.reddit.models.exceptions.UsernameAlreadyExist;
import com.reddit.models.exceptions.UsernameOrPasswordIsNotCorrect;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class User {
    private String username;
    private String password;
    private String email;
    private LocalDate registrationDate;

    private ArrayList<Post> posts ;
    private ArrayList<Message> messages ;
    private ArrayList<UserSubredditRelation> userSubredditRelations;
    private ArrayList<Interaction> interactions;
    private ArrayList<User> followings  ;

    private static ArrayList<User> users = new ArrayList<>() ;


    private User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.followings = new ArrayList<>();
        this.messages=new ArrayList<>();
        this.userSubredditRelations = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.interactions = new ArrayList<>();
        this.registrationDate = LocalDate.now();

    }

    public static User createUser(String username , String password, String email){
        User user =new User(username,password,email);
        users.add(user);
        return user;
    }

    public void sendMessage(User receiver,String text){
        Message message = Message.createMessage(receiver,text);
        this.messages.add(message);
    }

    public ArrayList<Message> chatMessages(User user1){

        ArrayList<Message> chatMessages = new ArrayList<>();
        for ( Message message: messages) {
            if (message.getReceiver().equals(user1)){
                chatMessages.add(message);
            }
        }
        for (Message message:user1.getMessages()) {
            if ((message.getReceiver().equals(this))){
                chatMessages.add(message);
            }

        }

        chatMessages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message message1, Message message2) {
                return message1.getDate().compareTo(message2.getDate());
            }
        });


        return chatMessages;
    }

    public void addUserSubredditRelations(UserSubredditRelation userSubredditRelation){
        userSubredditRelations.add(userSubredditRelation);

    }
    public Post newPost(String text, Subreddit subreddit){
        Post post = Post.createPost(text,subreddit);
        posts.add(post);
        return post;

    }

    public static User login(String username,String password)throws UsernameOrPasswordIsNotCorrect{
        for (User user:users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        throw new UsernameOrPasswordIsNotCorrect("password or username is not correct");
    }


    public static User singup(String username,String password ,String email) throws UsernameAlreadyExist {
        for (User user: users) {
            if (user.getUsername().equals(username)){
                throw new UsernameAlreadyExist("This username isn't available. Please try another.");
            }
        }
        User newUser = User.createUser(username,password,email);
        return newUser;
    }

    public static User searchUser(String username) throws UserNotFound{
        for (User user: users) {
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        throw new UserNotFound("user not found");
    }
    public void follow(String username) throws UsernameAlreadyExist,UserNotFound{
        User user = User.searchUser(username);
        for (User user1: followings) {
            if (user1.equals(user)){
                throw new UsernameAlreadyExist("user already follow");
            }
        }
        followings.add(user);

    }
    public ArrayList<User> userFollowers(){
        ArrayList<User> followers = new ArrayList<>();
        for (User user:users) {
            for (User user1: user.followings) {
                if (user1.equals(this)){
                    followers.add(user1);
                }
            }

        }

        return followers;
    }

    public void followSubreddit( Subreddit subreddit){
        UserSubredditRelation userSubredditRelation = UserSubredditRelation.createUserSubredditRelation(TypeOfRelation.FOLLOWER,subreddit);
        this.addUserSubredditRelations(userSubredditRelation);

    }
    public boolean hasFollowedSubreddit(Subreddit subreddit){
        for (UserSubredditRelation userSubredditRelation: userSubredditRelations) {
            if (userSubredditRelation.getSubreddit().equals(subreddit)){
                return true;
            }
        }
        return false;
    }

    public void unFollow(String username) throws UserNotFound{
        for (User user : followings) {
            if (user.getUsername().equals(username)){
                followings.remove(user);
            }
        }
        throw new UserNotFound("this username is not exist in your followings");


    }
    public boolean hasFollowed(User user){
        for (User user1: followings) {
            if (user1.getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }

    public ArrayList<UserSubredditRelation> getUserSubredditRelations() {
        return userSubredditRelations;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}