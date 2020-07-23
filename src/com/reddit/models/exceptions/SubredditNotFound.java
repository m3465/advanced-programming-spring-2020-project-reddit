package com.reddit.models.exceptions;


public class SubredditNotFound extends Exception {
    public SubredditNotFound() {
        super();
    }

    public SubredditNotFound(String message) {
        super(message);
    }

}
