package com.reddit;

import com.reddit.models.exceptions.SubredditAlreadyExist;
import com.reddit.views.LoginSingUpPage;

public class MainClass {
    public static void str () throws SubredditAlreadyExist {

        throw new SubredditAlreadyExist("hello");
    }


    public static void main(String[] args) {
        LoginSingUpPage loginSingUpPage = LoginSingUpPage.createLoginSingUpPage();
        loginSingUpPage.render();




    }
}
