package com.reddit.models.PostManagement;

import com.reddit.models.UserManagement.User;

public abstract class Interaction {
    private User user;

    public Interaction(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
