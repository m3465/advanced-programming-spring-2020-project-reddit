package com.reddit.views;

import com.reddit.models.UserManagement.User;

import java.util.Scanner;

public class LoginSingUpPage {
    public LoginSingUpPage() {
    }

    public static LoginSingUpPage createLoginSingUpPage(){
        return new LoginSingUpPage();
    }

    private void login(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();

        try {
            User user = User.login(username,password);
            HomePage homePage = HomePage.createHomePage(user);
            homePage.render();
        }catch (Exception e){
            System.out.println(e.getMessage());
            render();
        }

    }

    private void singUp(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        System.out.println("email: ");
        String email = scanner.nextLine();

        try {
            User user = User.singup(username,password,email);
            HomePage homePage = HomePage.createHomePage(user);
            homePage.render();
        }catch (Exception e){
            System.out.println(e.getMessage());
            render();
        }

    }

    private void loginAsGuest(){
        GuestPage guestPage = GuestPage.createdGuestPage();
        guestPage.render();
    }



    public void render(){
        // clean console page
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("Welcome to reddit ");
        System.out.println("1. Login \t2.SingUp \t3.login as a guest\t4.exit");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                login();
                break;
            case 2:
                singUp();
                break;
            case 3:
                loginAsGuest();
            case 4:
                return;
            default:
                render();
                break;
        }


    }
}
