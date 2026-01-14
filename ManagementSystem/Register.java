package src.ManagementSystem;
import javax.json.JsonObject;
import java.util.ArrayList;

public class Register implements Ticket {
    private int index;
    private String type; // "member" or "guest"
    private String name;
    private String email;
    private String phone;

    public Register(int index) {
        this.index = index;
    }

    @Override
    public void readInformation(JsonObject information) {
        this.type = information.getString("type");
        this.name = information.getString("name");
        this.email = information.getString("email");
        this.phone = information.getString("phone");
    }
    @Override
    public boolean proceedRequest(ArrayList<Book> books,ArrayList<User> users) {
        User newUser = null;
        if (type.equalsIgnoreCase("member")) {
            newUser = new Member(name,email,phone);
        } else if (type.equalsIgnoreCase("guest")) {
            newUser = new Guest(name,email,phone);
        }
        users.add(newUser);
        System.out.println("(Successful) "+name+", account: "+ newUser.getAccount());
        return true;
    }
    public int getIndex(){return index;}
    public String getType(){return "Register";}
    public String getInfo(){return name;}
    public String getMoreInfo(){return type;}
}

