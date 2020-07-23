package com.reddit.views;

import com.reddit.models.PostManagement.Post;

import java.util.ArrayList;
import java.util.Scanner;

public class GuestPage {

    private GuestPage() {

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
    public static GuestPage createdGuestPage(){
        return new GuestPage();
    }
    public void render(){
        // clean console page
        System.out.println("\033[H\033[2J");
        System.out.flush();

        showPosts(Post.allPosts());

        System.out.println("If you enjoy our website please back to singup page and join us ");
        System.out.println("1. Back \t");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        LoginSingUpPage loginSingUpPage = LoginSingUpPage.createLoginSingUpPage();


    }
}
