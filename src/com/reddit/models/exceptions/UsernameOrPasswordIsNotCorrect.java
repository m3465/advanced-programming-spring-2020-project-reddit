package com.reddit.models.exceptions;

public class UsernameOrPasswordIsNotCorrect extends Exception {
    public UsernameOrPasswordIsNotCorrect(){
        super();
    }
    public UsernameOrPasswordIsNotCorrect(String message) {
        super(message);
    }
}
