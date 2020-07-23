package com.reddit.views;

import com.reddit.models.PostManagement.Post;
import com.reddit.models.SubredditManagement.Subreddit;
import com.reddit.models.SubredditManagement.UserSubredditRelation;
import com.reddit.models.UserManagement.User;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Scanner;

public class SubredditPage {
    private Subreddit subreddit;
    private User owner ;
    private User currentUser;

    private SubredditPage(Subreddit subreddit,User currentUser) {
        this.subreddit =subreddit;

        //found subreddit owner
        for (User user: User.getUsers()) {
            for (UserSubredditRelation userSubredditRelation:user.getUserSubredditRelations() ) {
                if (subreddit.equals(userSubredditRelation.getSubreddit())){
                    this.owner = user;
                }
            }

        }


        this.currentUser = currentUser;
    }

    public static SubredditPage createSubredditPage(Subreddit subreddit , User currentUser){

        return new SubredditPage(subreddit,currentUser);
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




    public void render(){
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println(subreddit.getName() + " page: ");
        System.out.println("created by " + owner.getUsername() + "  on" +subreddit.getDate().toLocaleString());
        System.out.println();

        ArrayList<Post> subredditPosts = new ArrayList<>(Post.subredditPost(subreddit));
        showPosts(subredditPosts);

        System.out.println("1.Select a post\t2. Back to Home");
        System.out.println("Choose your option: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1 :
                System.out.println("Enter a post number : ");
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index>subredditPosts.size()){
                    System.out.println("this post not found");
                    render();
                }
                else {
                    PostPage postPage = PostPage.createPostPage(currentUser, subredditPosts.get(index - 1));
                    postPage.render();
                }
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

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public User getOwner() {
        return owner;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
