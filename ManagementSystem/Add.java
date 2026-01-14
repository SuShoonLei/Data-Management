package src.ManagementSystem;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Random;

public class Add implements Ticket {
    private int index;
    private String title;
    private String category;
    private String author;
    private String ID;

    public Add(int index) {
        this.index = index;
    }

    @Override
    public void readInformation(JsonObject information) {
        this.title = information.getString("title");
        this.category = information.getString("category");
        this.author = information.getString("author");
    }

    @Override
    public boolean proceedRequest(ArrayList<Book> books,ArrayList<User> users) {
        ID = generateID(category);
        books.add(new Book(title,category, author,ID,"A"));
        System.out.println("(Successful) "+title+", id: " + ID);
        return true;
    }

    public String generateID(String category){
        String prefix = category.substring(0, Math.min(3, category.length())).toUpperCase();
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000);
        return prefix + "-" + randomNumber;
    }

    public int getIndex(){return index;}
    public String getType(){return "Add";}
    public String getInfo(){return title;}
    public String getMoreInfo(){return ID;}
}

