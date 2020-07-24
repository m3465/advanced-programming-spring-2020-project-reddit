package com.reddit.views;

import com.reddit.models.PostManagement.Post;
import com.reddit.models.SubredditManagement.Subreddit;
import com.reddit.models.SubredditManagement.UserSubredditRelation;
import com.reddit.models.UserManagement.User;
import com.reddit.models.enums.TypeOfRelation;

import java.util.ArrayList;
import java.util.Scanner;

public class HomePage {
    private User currentUser;

    private HomePage(User currentUser) {
        this.currentUser = currentUser;
    }
    public static HomePage createHomePage(User currentUser){
        return new HomePage(currentUser);
    }

    public void post(){
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Enter your post text : ");
        Scanner scanner = new Scanner(System.in);
        String  postText = scanner.nextLine();
        System.out.println("please enter Subreddit that you want post in it: ");
        String subredditName = scanner.nextLine();
        try {
            Subreddit subreddit = Subreddit.search(subredditName);
            if (currentUser.hasFollowedSubreddit(subreddit)){
                Post post = currentUser.newPost(postText,subreddit);
                PostPage postPage = PostPage.createPostPage(currentUser,post);
                postPage.render();

            }
            else{
                System.out.println("you should follow subreddit to post in it");
                System.out.println("1. follow this subreddit and continue posting");
                System.out.println("2. i don't want to follow ");

                int x = scanner.nextInt();
                scanner.nextLine();
                if (x==1){
                    currentUser.followSubreddit(subreddit);
                    Post post = currentUser.newPost(postText,subreddit);
                    PostPage postPage = PostPage.createPostPage(currentUser,post);

                    postPage.render();

                }
                else{
                    HomePage homePage = HomePage.createHomePage(currentUser);
                    homePage.render();
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("please enter a correct subreddit or create this subreddit from homepage");
            System.out.println("1.try again \t 2.back to my page");
            System.out.println("please enter your choice :");
            int choice;
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1 ){
                post();
            }
            else {
                render();
            }

        }

    }

    public void searchForUser(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("Enter username to search : ");
        Scanner scanner = new Scanner(System.in);
        String  username = scanner.nextLine();
        try {
            User user = User.searchUser(username);
            UserPage userPage = UserPage.createUserPage(currentUser,user);
            userPage.render();
        }catch (Exception e ){
            System.out.println(e.getMessage());
            System.out.println("1.search again \t 2.back to my page");
            System.out.println("please enter your choice :");
            int choice;
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1 ){
                searchForUser();
            }
            else {
                render();
            }
        }


    }
    private void subredditSearch(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("Enter subreddit's name to search : ");
        Scanner scanner = new Scanner(System.in);
        String  subredditName = scanner.nextLine();
        try {
            Subreddit subreddit = Subreddit.search(subredditName);
            SubredditPage subredditPage =SubredditPage.createSubredditPage(subreddit,currentUser);
            subredditPage.render();
        }catch (Exception e ){
            System.out.println(e.getMessage());
            System.out.println("1.try again \t 2.back to my page");
            System.out.println("please enter your choice :");
            int choice;
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1 ){
                subredditSearch();
            }
            else {
                render();
            }
        }


    }

    private void  logout(){
        LoginSingUpPage loginSingUpPage = LoginSingUpPage.createLoginSingUpPage();
        loginSingUpPage.render();
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

    private void showMyPage(){
        UserPage userPage = UserPage.createUserPage(currentUser,currentUser);
        userPage.render();
    }
    private void createSubreddit(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("subreddit name :");
        String subredditName = scanner.nextLine();
        try {
            Subreddit subreddit = Subreddit.createSubreddit(subredditName);
            UserSubredditRelation userSubredditRelation = UserSubredditRelation.createUserSubredditRelation(TypeOfRelation.OWNER,subreddit);
            currentUser.addUserSubredditRelations(userSubredditRelation);
            System.out.println("Done! ");
            System.out.println("press Enter Key to continue ...");
            scanner.nextLine();
            SubredditPage subredditPage = SubredditPage.createSubredditPage(subreddit,currentUser);
            subredditPage.render();

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("1.try again \t 2.back to my page");
            System.out.println("please enter your choice :");
            int choice;
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1 ){
                createSubreddit();
            }
            else {
                render();
            }
        }

    }

    private void userChats(){
        ChatsPage chatsPage = ChatsPage.createChatsPage(currentUser);
        chatsPage.render();

    }


    public void render(){
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Home Page for : "+ currentUser.getUsername());
        System.out.println();


        ArrayList<Post> posts = new ArrayList<>(Post.postsForUser(currentUser));
        showPosts(posts);
        System.out.println();
        System.out.println("1.Create Subreddit\t2.New post\t3.Select post\t4.chats\t5.show my page\t6.User Search\t7.Subreddit search\t8.logout\t9.exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                createSubreddit();
                break;
            case 2:
                post();
                break;
            case 3:
                System.out.println("Choose post number: ");
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index >posts.size()){
                    render();
                }
                else{
                    Post post = posts.get(index-1);
                    PostPage postPage = PostPage.createPostPage(currentUser,post);
                    postPage.render();
                }
                break;
            case 4 :
                userChats();

                break;
            case 5:
                showMyPage();
                break;
            case 6:
                searchForUser();
                break;
            case 7:
                subredditSearch();
                break;
            case 8:
                logout();
                break;
            case 9:
                return;
            default:
                render();
                break;

        }

    }



    public User getCurrentUser() {
        return currentUser;
    }
}
