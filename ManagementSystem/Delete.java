package src.ManagementSystem;

import javax.json.JsonObject;
import java.util.ArrayList;

public class Delete implements Ticket {
    private int index;
    private String title;
    private String id;

    public Delete(int index) {
        this.index = index;
    }

    @Override
    public void readInformation(JsonObject information) {
        this.title = information.getString("title");
        this.id = information.getString("id");
    }
    @Override
    public boolean proceedRequest(ArrayList<Book> books,ArrayList<User> users) {
        boolean isDeleted = false ;
        for(Book book : books){
            if (book.title.equalsIgnoreCase(title)){
                if(book.id.equalsIgnoreCase(id)){
                    if(book.avail.equals(Availability.A)){
                        books.remove(book);
                        System.out.println("(Successful) "+title+", id: "+ id);
                        isDeleted = true;
                        break;
                    }else {
                        System.out.println("(Hold) "+title+", Bw");
                        break;
                    }
                }
            }
        }
        return isDeleted;
    }


    public int getIndex(){return index;}
    public String getType(){return "Delete";}
    public String getInfo(){return title;}
    public String getMoreInfo(){return id;}
}
