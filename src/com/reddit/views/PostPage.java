package com.reddit.views;

import com.reddit.models.PostManagement.Comment;
import com.reddit.models.PostManagement.Interaction;
import com.reddit.models.PostManagement.Score;
import com.reddit.models.PostManagement.Post;
import com.reddit.models.UserManagement.User;

import java.util.Scanner;

public class PostPage<tweet> {
    private User currentUser;
    private Post post;

    private PostPage(User currentUser, Post post) {
        this.currentUser = currentUser;
        this.post = post;
    }

    public static PostPage createPostPage(User currentUser , Post post){

        return new PostPage(currentUser,post);
    }

    private void  upvoted(){
        Score score = Score.like(currentUser,post);
        render();
    }

    private void downvoted(){
        Score.disLike(currentUser,post);
        render();
    }

    private void comment(){
        System.out.println("Enter your comment: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        Comment.comment(currentUser,post,text);
        render();


    }


    public void render(){
        System.out.println("\033[H\033[2J");
        System.out.flush();

        post.showPost();
        System.out.println("Comments : ");
        for (Interaction interaction: post.getInteractions()) {
            if (interaction instanceof Comment){

                System.out.print("\t");
                ((Comment)interaction).showComment();
                System.out.println();
            }
        }
        boolean hasLiked = false;
        for (Interaction interaction:post.getInteractions()) {
            if (interaction instanceof Score){
                if (interaction.getUser().getUsername().equals(currentUser.getUsername())){
                    hasLiked=true;
                }
            }

        }
        if (!hasLiked){
            System.out.println("1. Upvoted\t2. Comment\t3. Back Home");
        }
        else {
            System.out.println("1. Downvoted\t2. Comment\t3. Back Home");
        }
        System.out.println("Choose your option: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                if(!hasLiked){
                    upvoted();
                }
                else {
                    downvoted();
                }
                break;
            case 2:
                comment();
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


    public User getCurrentUser() {
        return currentUser;
    }

    public Post getPost() {
        return post;
    }
}
