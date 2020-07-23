package com.reddit.models.exceptions;



public class SubredditAlreadyExist extends Exception {
    public SubredditAlreadyExist() {
        super();
    }

    public SubredditAlreadyExist(String message) {
        super(message);
    }

}
