package com.reddit.models.exceptions;

public class UsernameAlreadyExist extends Exception {
    public UsernameAlreadyExist() {
        super();
    }

    public UsernameAlreadyExist(String message) {
        super(message);
    }

}
