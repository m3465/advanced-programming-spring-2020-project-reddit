package com.reddit.views;

import com.reddit.models.UserManagement.Message;
import com.reddit.models.UserManagement.User;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatsPage {

    private User currentUser;

    private ChatsPage(User currentUser) {
        this.currentUser = currentUser;
    }

    public static ChatsPage createChatsPage(User user){
        return new ChatsPage(user);
    }

    private ArrayList<User> showChats(){
        ArrayList<User> users = new ArrayList<>();
        for (Message message: currentUser.getMessages()) {
            if (!users.contains(message.getReceiver())){
                users.add(message.getReceiver());
            }
        }
        for (User user :User.getUsers()) {
            for (Message message:user.getMessages()) {
                if ((!users.contains(user)) && message.getReceiver().equals(currentUser)){
                    users.add(user);
                }

            }
        }

        return users;
    }
    private void newChat(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter message's text");
        String text = scanner.nextLine();
        currentUser.sendMessage(user,text);
    }


    private void showChat(User user){
        ArrayList<Message>  messages = currentUser.chatMessages(user);
        for (Message message:messages) {
            if (message.getReceiver().equals(user)){
                System.out.println("You: " + message.getText() + "  " + message.getDate().getHours() + ":" + message.getDate().getMinutes());

            }
            else if (message.getReceiver().equals(currentUser)){
                System.out.println(user.getUsername()+ ": " + message.getText() + "  " + message.getDate().getHours() + ":" + message.getDate().getMinutes());


            }

        }
        System.out.println("1.new message\t2.back to chats page");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1){
            newChat(user);
            showChat(user);
        }
        else{
            render();
        }


    }

    public void  render(){
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("Chats:");
        Scanner scanner = new Scanner(System.in);

        System.out.println("1.show chats\t2.new chat\t3.homepage");

        int choice ;
        choice=scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                ArrayList<User> users = showChats();
                int x=1;
                for (User user: users) {
                    System.out.println(x + ". " + user.getUsername());
                    ++x;
                }

                System.out.println("select a chat number:");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice<=users.size()){
                    showChat(users.get(choice-1));
                }
                else{
                    System.out.println("chat not found");
                    render();
                }

                break;
            case 2 :
                System.out.println("enter your friend username");
                String username = scanner.nextLine();
                try {
                    User user = User.searchUser(username);
                    newChat(user);
                    render();

                }catch(Exception e){
                    System.out.println(e.getMessage());
                    render();
                }
                break;
            default:
                HomePage homePage = HomePage.createHomePage(currentUser);
                homePage.render();
                break;


        }




    }






    public User getCurrentUser() {
        return currentUser;
    }
}
