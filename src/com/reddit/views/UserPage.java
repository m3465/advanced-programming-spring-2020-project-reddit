package com.reddit.views;

import com.reddit.models.PostManagement.Post;
import com.reddit.models.UserManagement.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserPage {

    private User currentUser;
    private User user ;

    public UserPage(User currentUser, User user) {
        this.currentUser = currentUser;
        this.user = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserPage createUserPage(User currentUser ,User user){
        return  new UserPage(currentUser,user);

    }
    private void follow(){
        try {
            currentUser.follow(user.getUsername());
            render();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            render();

        }


    }
    private void unFollow(){
       try {
           currentUser.unFollow(user.getUsername());
           render();
       }catch (Exception e){
           System.out.println(e.getMessage());
           render();
       }
    }
    private void showPosts(ArrayList<Post> posts){
        int x = 1 ;
        for (Post post:posts) {
            System.out.println("post " + x + ": ");
            post.showPost();
            System.out.println();
            ++x;
        }
    }



    public void render() {
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println(user.getUsername() + "'s page");
        System.out.println("following : " + user.getFollowings().size() + "\t followers: "+user.userFollowers().size()) ;

        ArrayList<Post> userPosts = new ArrayList<>(Post.userPosts(user));
        showPosts(userPosts);
        if (currentUser.equals(user)){
            //user on his(her) page
            System.out.println("1. Select a post\t2. Back to Home");
            System.out.println("Choose your option : ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1 :
                    System.out.println("Enter a post number : ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    PostPage postPage = PostPage.createPostPage(currentUser, userPosts.get(index - 1));
                    postPage.render();
                    break;
                case 2 :
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.render();
                    break;
                default:
                    render();
                    break;

            }


        }
        else{
            if (currentUser.hasFollowed(user)) {
                System.out.println("1. unFollow\t2. Select a Post\t3. Back to Home");

            } else {
                System.out.println("1. Follow\t2. Select a Post\t3. Back to Home");

            }

            System.out.println("Choose your option : ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (currentUser.hasFollowed(user)) {
                        unFollow();
                    } else {
                        follow();
                    }
                    break;
                case 2:
                    System.out.println("Enter a post number : ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    PostPage postPage = PostPage.createPostPage(currentUser, userPosts.get(index - 1));
                    postPage.render();
                    break;
                case 3:
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.render();
                    break;
                default:
                    render();
                    break;
            }
        }
    }
}
