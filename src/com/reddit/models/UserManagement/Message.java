package com.reddit.models.UserManagement;

import java.util.ArrayList;
import java.util.Date;

public class Message {
    private User receiver;
    private String text;
    private Date date;
    private static ArrayList<Message> messages = new ArrayList<>();

    private Message(User receiver, String text) {
        this.date = new Date();
        this.receiver = receiver;
        this.text = text;
    }

    public static Message createMessage( User receiver, String text){
        Message message =new Message(receiver,text);;
        messages.add(message);
        return message;

    }



    public User getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }
}
