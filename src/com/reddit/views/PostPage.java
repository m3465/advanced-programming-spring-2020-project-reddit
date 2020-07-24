package com.reddit.views;

import com.reddit.models.PostManagement.Comment;
import com.reddit.models.PostManagement.Interaction;
import com.reddit.models.PostManagement.Score;
import com.reddit.models.PostManagement.Post;
import com.reddit.models.UserManagement.User;

import java.util.ArrayList;
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
    public void showCommentRepliesScore(Interaction interaction){
        Comment comment= (Comment)interaction;
        System.out.println("\t\tScore: "+comment.getScore());
        System.out.println("\t\treplies:");
        for (Comment comment1: comment.getReplies()) {
            System.out.println("\t\t\t"+comment1.getUser().getUsername() + " says:" +comment1.getText());
        }
    }


    public void render(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("post page");

        post.showPost();
        System.out.println("Comments : ");
        int i=1;
        ArrayList<Interaction> interactions=new ArrayList<>(post.getInteractions());
        ArrayList<Comment> comments =new ArrayList<>();
        for (Interaction interaction: interactions) {
            if (interaction instanceof Comment){
                System.out.print("\t"+ i +". ");
                ((Comment)interaction).showComment();
                comments.add((Comment)interaction);
                showCommentRepliesScore(interaction);
                ++i;
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
            System.out.println("1. Upvoted\t2. Comment\t3.Select a comment to reply or like\t4. Back Home");
        }
        else {
            System.out.println("1. Downvoted\t2. Comment\t3.Select a comment to reply or like\t4. Back Home");
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
            case 3 :
                System.out.println("enter comment number:");
                int ch = scanner.nextInt();
                scanner.nextLine();
                if (ch>comments.size()){
                    System.out.println("comment not found");
                    System.out.println("please press enter key ...");
                    scanner.nextLine();
                    render();
                }
                else {
                    Comment comment = comments.get(ch - 1);
                    System.out.println("1.reply\t2.score");
                    System.out.println("enter your choice:");
                    int ch1 = scanner.nextInt();
                    scanner.nextLine();
                    if (ch1 == 1) {
                        System.out.println("enter your reply:");
                        String text = scanner.nextLine();
                        comment.reply(currentUser, text);
                        render();
                    } else if (ch1 == 2) {
                        comment.score();
                        render();
                    } else {
                        render();
                    }
                }
                break;
            case 4:
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
