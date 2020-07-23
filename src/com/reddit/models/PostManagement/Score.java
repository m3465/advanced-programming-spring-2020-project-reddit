package com.reddit.models.PostManagement;

import com.reddit.models.UserManagement.User;

public class Score extends  Interaction {
    public Score(User user) {
        super(user);
    }

    public static Score like(User user , Post post){
        Score score = new Score(user);
        post.addInteraction(score);
        return score;
    }

    public static void disLike(User user, Post post){
        for (Interaction interaction:post.getInteractions()) {
            if (interaction instanceof Score && interaction.getUser().getUsername().equals(user.getUsername())){
                post.getInteractions().remove(interaction);
                return;
            }
        }
    }
}
