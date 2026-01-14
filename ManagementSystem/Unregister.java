package src.ManagementSystem;

import javax.json.JsonObject;
import java.util.ArrayList;

public class Unregister implements Ticket {
    private int index;
    private String name;
    private String account;

    public Unregister(int index) {
        this.index = index;
    }

    @Override
    public void readInformation(JsonObject information) {
        this.name = information.getString("name");
        this.account = information.getString("account");
    }

    @Override
    public boolean proceedRequest(ArrayList<Book> books,ArrayList<User> users){
        boolean isUnregistered = false;
        for(User user : users){
            if (user.getName().equalsIgnoreCase(name)){
                if(user.getAccount().equalsIgnoreCase(account)){
                    if( user.getBorrowedBooks()!= null && user.getBorrowedBooks().length==0){
                        users.remove(user);
                        System.out.println("(Successful) "+name+", account: "+ account);
                        isUnregistered = true;
                        break;
                    }else {
                        users.remove(user);
                        System.out.println("(Hold) "+name+", account: borrowedBooks");
                        break;
                    }
                }
            }
        }
        return isUnregistered;
    }
    public int getIndex(){return index;}
    public String getType(){return "Unregister";}
    public String getInfo(){return name;}
    public String getMoreInfo(){return account;}

}

