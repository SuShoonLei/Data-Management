package src.ManagementSystem;

import javax.json.JsonObject;
import java.util.ArrayList;

public interface Ticket {
    void readInformation(JsonObject information);

    boolean proceedRequest(ArrayList<Book> books,ArrayList<User> users);
    int getIndex();
    String getType();
    String getInfo();
    String getMoreInfo();
}

