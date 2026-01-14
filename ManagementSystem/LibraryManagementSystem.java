package src.ManagementSystem;
/*
    CSC241/COG241 Fall 2024
    Assignment #: 6
    Due Date: Thursday, November 22
    Name: Su Shoon Lei Khaing
    ID#:806311427
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import Utility.Benchmark;

import javax.json.*;


public class LibraryManagementSystem {


    public static void main (String[] args) throws Exception{

//        Benchmark.decreaseCounterLS();
//        JsonObject jsonObject =  jsonReader.readObject();
        System.out.println("LMS - Library Management System On.");

        Properties prop = new Properties();
        FileInputStream fisConfig = new FileInputStream(System.getProperty("user.dir")+"/src/config.properties");
        prop.load(fisConfig);
        ArrayList<Book> books = new ArrayList<>();
//        String dataBook = "books";
//        String filePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + dataBook +".txt";
//        File inputFile = new File(filePath);
//        Scanner sc = new Scanner(inputFile);
//        String bookString;

//        String[] book;
//        while(sc.hasNext()){
//            bookString = sc.nextLine();
//
//            book = bookString.split(", ");
//
//            if( book.length == 8){
//                books.add(new Book(book[0],book[1],book[2],book[3],book[4],book[5],book[6],book[7]));}
//            else {
//                books.add(new Book(book[0],book[1],book[2],book[3],book[4]));
//            }
//        }


        String JdataBook = "books";
        String JfilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + JdataBook + ".json";
        File JinputFile = new File(JfilePath);
        System.out.println(JfilePath);
        InputStream JinputStream = new FileInputStream(JinputFile);
        JsonReader jsonReader = Json.createReader(JinputStream);
        JsonArray jsonArray = jsonReader.readObject().getJsonArray("books");
        for (JsonValue jo : jsonArray){
            books.add(new Book(jo.asJsonObject().get("title").toString().replace("\"","")
                    ,jo.asJsonObject().get("category").toString().replace("\"",""),
                    jo.asJsonObject().get("author").toString().replace("\"",""),
                    jo.asJsonObject().get("id").toString().replace("\"",""),
                    jo.asJsonObject().get("availability").toString().replace("\"",""),
                    jo.asJsonObject().get("borrowing_date").toString().replace("\"",""),
                    jo.asJsonObject().get("due_date").toString().replace("\"",""),
                    jo.asJsonObject().get("borrowers_account").toString().replace("\"","")));
        }

        System.out.println("The loading books is complete.");
        ArrayList<ArrayList<Book>> organizedBooks = new ArrayList<>();
        ArrayList<Book> org ;
        int index = 0;
        for(int i = 0 ; i < books.size() ; i++){
            if(!books.get(i).checked){
                String compare = books.get(i).type;
                org = new ArrayList<>();
                for(int j = 0 ; j < books.size() ; j++){
                    if(compare.equalsIgnoreCase(books.get(j).type)){
                        org.add(books.get(j));
                        books.get(j).checked = true;
                    }
                }
                organizedBooks.add(index,org);
                index++;
            }
        }


        String dataUser = "users";
        String userFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + dataUser +".json";
        File inputUserFile = new File(userFilePath);

        InputStream UinputStream = new FileInputStream(inputUserFile);
        JsonReader UjsonReader = Json.createReader(UinputStream);
        JsonArray UjsonArray = UjsonReader.readObject().getJsonArray("users");
        ArrayList<User> users = new ArrayList<>();
        Book bk = null;
        for (JsonValue jo : UjsonArray){
            String name =jo.asJsonObject().get("name").toString().replace("\"","");
            String account =jo.asJsonObject().get("account").toString().replace("\"","");
            String email = jo.asJsonObject().get("email").toString().replace("\"","");
            String phone = jo.asJsonObject().get("phone").toString().replace("\"","");

            if(account.substring(0,1).equalsIgnoreCase("T")){
                Guest gs = new Guest(name, email, phone, account);
                JsonArray booksborrowed = jo.asJsonObject().getJsonArray("borrowedBooks");
                if(booksborrowed.size() > 0){
                    for(JsonValue bkk : booksborrowed){
                        String title = bkk.asJsonObject().get("title").toString().replace("\"","");
                        String id = bkk.asJsonObject().get("id").toString().replace("\"","");

                        for(Book book: books){
                            if(book.title.equalsIgnoreCase(title) && book.id.equalsIgnoreCase(id)){
                                bk = book;
                                bk.avail = Availability.A;
                                break;
                            }
                        }
                        gs.borrowBook(bk);
                    }
                }
                users.add(gs);
            } else{
                Member mb = new Member(name, email, phone, account);
                JsonArray booksborrowed = jo.asJsonObject().getJsonArray("borrowedBooks");
                if(booksborrowed.size() > 0){
                    for(JsonValue bkk : booksborrowed){
                        String title = bkk.asJsonObject().get("title").toString().replace("\"","");
                        String id = bkk.asJsonObject().get("id").toString().replace("\"","");
                        for(Book book: books){
                            if(book.title.equalsIgnoreCase(title) && book.id.equalsIgnoreCase(id)){
                                bk = book;
                                bk.avail = Availability.A;
                                break;
                            }
                        }
                        mb.borrowBook(bk);
                    }
                }
                users.add(mb);
            }
        }
        System.out.println("The loading users is complete.");


        String dataTicket = "tickets";
        String ticketFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + dataTicket + ".json";
        File inputTicketFile = new File(ticketFilePath);

        InputStream TinputStream = new FileInputStream(inputTicketFile);
        JsonReader TjsonReader = Json.createReader(TinputStream);
        JsonArray TjsonArray = TjsonReader.readObject().getJsonArray("tickets");
        LinkedList tickets = new LinkedList();

        for (JsonValue jo : TjsonArray) {
            int index2 = jo.asJsonObject().getInt("index");
            String request = jo.asJsonObject().getString("request");
            JsonObject information = jo.asJsonObject().getJsonObject("information");

            Ticket ticket = null;

            switch (request) {
                case "register":
                    ticket = new Register(index2);
                    break;

                case "unregister":
                    ticket = new Unregister(index2);
                    break;

                case "add":
                    ticket = new Add(index2);
                    break;

                case "delete":
                    ticket = new Delete(index2);
                    break;

                default:
                    System.out.println("Unknown request type: " + request);
            }

            if (ticket != null) {
                ticket.readInformation(information);
                tickets.add(ticket);
            }
        }
        System.out.println("The loading ticket is complete.");


        if (args[0].equals("books")) {
            printBookinCatHead();
            for(int i = 0 ; i < index-1 ; i++){
                System.out.println("["+ organizedBooks.get(i).get(0).type +"] "+ organizedBooks.get(i).size()+" book(s)");
                for(Book book: organizedBooks.get(i)){
                       book.printBookinCat();
                }
            }
        }
        else if (args[0].equals("summary")) {
            printBookSummaryHead();
            int count;
            for(int i = 0 ; i < index-1 ; i++){
                count = 0;
                for(Book book1: organizedBooks.get(i)){
                    if(book1.avail == Availability.A){
                        count++;
                    }
                }
                printBookSummaryCat(organizedBooks.get(i).get(0).type,organizedBooks.get(i).size(),count);
            }
            printUserSummaryHead();
            for(int j = 0 ; j < users.size(); j++){
                users.get(j).printUserSummary();
            }
        }
        else if (args[0].equals("users")) {
            printUserHead();
            for(User us : users){
                us.printUser();
            }
            Test.testLab3();
        }

        else if (args[0].equals("search")) {
            String key = args[1];
            String value = args[2];
            String key1 = key.substring(0,1);
            String key2 = key.substring(1);
            if (key1.compareTo("b")==0){
                SortBooks(books,key2);
                System.out.println("[Searching Query] "+key+", " +value);
                if(value.compareTo("all")==0){
                    searchAll(books,key2);
                }else {
                    bauBS(value, books, key2);
                    ArrayList<Book> result = bauLS(value, books, key2);
                    if(result.isEmpty()){
                        System.out.println(value+" not found!");
//                        printBenchmark();
                    }else {
                        System.out.println("[Searching Result]");
                        printBookinCatHead();
                        for(Book bkk : result){
                            bkk.printBookinCat();
                        }
//                        printBenchmark();
                    }
                }
            }else if (key1.compareTo("u")==0){
                SortUsers(users,key2);
                System.out.println("[Searching Query] "+key+", " +value);
                unmBS(value,users,key2);
                ArrayList<User> result = unmLS(value,users,key2);

                if(result.isEmpty()){
                    System.out.println(value+" not found!");
//                    printBenchmark();
                }else {
                    System.out.println("[Searching Result]");
                    printUserHead();
                    for(User us : result){
                        us.printUser();
                    }
//                    printBenchmark();
                }
            }else {
                System.out.println("Wrong command!\n[bau] for searching book with author's name\n[unm] for searching user with user name");
            }

        }else if (args[0].equals("manage")) {
            ArrayList<Ticket> holdInfo = new ArrayList<>();
            while (tickets.head != null) {
                Ticket ticket = tickets.head.ticket;
                String ticketType = ticket.getType();
                System.out.println("[Ticket " + ticket.getIndex() + "] " + ticketType);
                if(ticketType.equalsIgnoreCase("delete")){
                    String Bookname = ticket.getInfo();
                    if (bauLS(Bookname,books,"ti").isEmpty()){
                        System.out.println("(Unsuccessful) "+ticket.getInfo()+", unknown");
                        if(tickets.head.next == null){
                            break;
                        }else {
                            tickets.head = tickets.head.next;
                            ticket = tickets.head.ticket;
                            ticketType = ticket.getType();
                            System.out.println("[Ticket " + ticket.getIndex() + "] " + ticketType);
                        }
                    }
                } else if (ticketType.equalsIgnoreCase("unregister")) {
                    String UserName = ticket.getInfo();
                    if(unmLS(UserName,users,"nm").isEmpty()){
                        System.out.println("(Unsuccessful) "+ticket.getInfo()+", unknown");
                        if(tickets.head.next == null){
                            break;
                        }else {
                            tickets.head = tickets.head.next;
                            ticket = tickets.head.ticket;
                            ticketType = ticket.getType();
                            System.out.println("[Ticket " + ticket.getIndex() + "] " + ticketType);
                        }
                    }

                }
                if (!ticket.proceedRequest(books, users)) {
                    holdInfo.add(ticket);
                }
                tickets.head = tickets.head.next;
            }
            System.out.println("[Hold requests] "+holdInfo.size()+" hold(s) \nIndex     Request      info");
            for(Ticket ticket: holdInfo){
                System.out.println(ticket.getIndex()+"     "+ticket.getType()+"     "+ticket.getInfo()+" ("+ticket.getMoreInfo()+")");
            }

//            OutputStream JOutStream = new FileOutputStream(JinputFile);
//            JsonWriter jsonWriter = Json.createWriter(JOutStream);
//            JsonArrayBuilder booksArrayBuilder = Json.createArrayBuilder();
//            for (Book book : books) {
//                if (book != null) { // Skip null entries
//                    JsonObjectBuilder bookObjectBuilder = Json.createObjectBuilder()
//                            .add("title", book.title)
//                            .add("category", book.type)
//                            .add("id", book.id)
//                            .add("author", book.author)
//                            .add("availability", book.avail.toString())
//                            .add("borrowing_date", book.bDate != null ? book.bDate : "")
//                            .add("due_date", book.dDate != null ? book.dDate : "")
//                            .add("borrowers_account", book.bAccount != null ? book.bAccount : "");
//                    booksArrayBuilder.add(bookObjectBuilder.build());
//                }
//            }
//                // Wrap the array in a JSON object with a "books" key
//            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
//                    .add("books", booksArrayBuilder.build());
//            jsonWriter.writeObject(jsonObjectBuilder.build());
//            OutputStream UOutputStream = new FileOutputStream(inputUserFile);
//            JsonWriter UjsonWriter = Json.createWriter(UOutputStream);
//            JsonArrayBuilder usersArrayBuilder = Json.createArrayBuilder();
//
//            for (User user : users) {
//                if (user != null) { // Skip null entries
//                    // Create a builder for the borrowedBooks array
//                    JsonArrayBuilder borrowedBooksArrayBuilder = Json.createArrayBuilder();
//                    if (user.getBorrowedBooks() != null) {
//                        for (Book book : user.getBorrowedBooks()) {
//                            if (book != null) { // Skip null borrowed books
//                                borrowedBooksArrayBuilder.add(
//                                        Json.createObjectBuilder()
//                                                .add("title", book.title)
//                                                .add("id", book.id)
//                                                .build()
//                                );
//                            }
//                        }
//                    }
//                        // Build the user object
//                    JsonObjectBuilder userObjectBuilder = Json.createObjectBuilder()
//                            .add("name", user.getName())
//                            .add("account", user.getAccount())
//                            .add("email", user.getEmail())
//                            .add("phone", user.getPhone())
//                            .add("borrowedBooks", borrowedBooksArrayBuilder.build());
//                    usersArrayBuilder.add(userObjectBuilder.build());
//                }
//            }
//
//                // Wrap the array in a JSON object with a "users" key
//            JsonObjectBuilder UserObjectBuilder = Json.createObjectBuilder()
//                    .add("users", usersArrayBuilder.build());
//            UjsonWriter.writeObject(UserObjectBuilder.build());
        }
    }

    private static void searchAll(ArrayList<Book> books, String key2) {
        int BSwinCount = 0, count = 0;
        double BSTotal = 0 , LSTotal=0;
        System.out.println("[Searching Result]");
        System.out.printf("%-30s %30s %30s\n", "Book Author", "Linear Search", "Binary Search");
        for(Book b : books){
            bauBS(b.author,books,key2);
            BSTotal += Benchmark.getCounterBS();
            bauLS(b.author,books,key2);
            LSTotal += Benchmark.getCounterLS();
            System.out.printf("%-30s %30d %30d\n", b.author, Benchmark.getCounterLS() , Benchmark.getCounterBS());
            if(Benchmark.getCounterBS() < Benchmark.getCounterLS()){
                BSwinCount++;
            }
            count++;
        }
        int wr = (BSwinCount*100)/count;
        System.out.println("[Benchmark Result]\nBy Linear Search: "+(LSTotal/count)+" comparison(s) | "+ (count-BSwinCount) +" times");
        System.out.println("By Binary Search: "+(BSTotal/count)+" comparison(s) | "+BSwinCount+" times");
        System.out.println("Binary Search Winning Rate (vs Linear Search): "+wr+"%");
    }



    public static void printBookinCatHead () {
        System.out.printf("%-30.30s %-13.13s %-15s %-5s %-13s %-13s %-13s\n", "Title", "Author", "ID", "Avail", "Borrow", "Due", "B-Account");
    }
    public static void printUserHead () {
        System.out.printf("%-30.30s %-10s %-30.30s %-15s %-11s\n", "Name", "Account", "Email", "Phone", "Borrowed Book");

    }
    public static void printUserSummaryHead () {
        System.out.printf("%-30s %-50s\n", "Name", "Number of Books (borrowed / total)");
    }

    // print book summary table header
    public static void printBookSummaryHead () {
        System.out.printf("%-30s %-50s\n", "Category", "Number of Books (avail / total)");
    }

    // print book summary table header
    public static void printBookSummaryCat (String category, int total, int avail) {
        System.out.printf("%-30s %3d / %3d\n", category, avail, total);
    }
    public static void printBenchmark(){
        System.out.println("[Benchmark Result]\n" +"By Linear Search: "+Benchmark.getCounterLS()+" comparison(s)\nBy Binary Search: "+Benchmark.getCounterBS()+" comparison(s)");
    }
    public static void SortUsers(ArrayList<User> users, String key2) {
        for (int i = 0; i < users.size() - 1; i++) {
            int minIndex = i;
            User min = users.get(i);
            String minStr = getUserAttribute(min, key2);

            for (int j = i + 1; j < users.size(); j++) {
                String compare = getUserAttribute(users.get(j), key2);

                if (compare.compareTo(minStr) < 0) {
                    min = users.get(j);
                    minStr = compare;
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                User temp = users.get(i);
                users.set(i, min);
                users.set(minIndex, temp);
            }
        }
    }

    private static String getUserAttribute(User user, String key) {
        switch (key) {
            case "nm": return user.getName();
            case "ac": return user.getAccount();
            case "em": return user.getEmail();
            case "ph": return user.getPhone();
            case "bb": return Integer.toString(user.getBorrowedBooks().length);
            default:
                System.out.println("Wrong key Command!");
                return "";
        }
    }
    public static void SortBooks(ArrayList<Book> books, String key2) {
        for (int i = 0; i < books.size() - 1; i++) {
            int minIndex = i;
            Book min = books.get(i);
            String minStr = getBookAttribute(min, key2);

            for (int j = i + 1; j < books.size(); j++) {
                String compare = getBookAttribute(books.get(j), key2);

                if (compare.compareTo(minStr) < 0) {
                    min = books.get(j);
                    minStr = compare;
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Book temp = books.get(i);
                books.set(i, min);
                books.set(minIndex, temp);
            }
        }
    }

    private static String getBookAttribute(Book book, String key) {
        switch (key) {
            case "au": return book.author;
            case "ti": return book.title;
            case "ca": return book.type;
            case "id": return book.id;
            case "av": return book.avail.toString();
            case "bd": return book.bDate;
            case "db": return book.dDate;
            case "ba": return book.bAccount;
            default:
                System.out.println("Wrong key Command!");
                return "";
        }
    }

    public static ArrayList<Book> bauLS(String value, ArrayList<Book> b, String key2){
        ArrayList<Book> bk = new ArrayList<>();
        Benchmark.resetCounterLS();
        for (Book book :b){
            Benchmark.increaseCounterLS();
            List<String> parts = new ArrayList<>(Arrays.asList(getBookAttribute(book,key2).split("[-.@ ]")));
            parts.add(getBookAttribute(book,key2));
//            System.out.println(getBookAttribute(book,key2)+" and "+value);
            for (String s: parts){
                if(s.equalsIgnoreCase(value)){
                    bk.add(book);
                    break;
                }
            }

        }
        return bk;
    }
    public static ArrayList<Book> bauBS(String value, ArrayList<Book> b, String key2) {
        ArrayList<Book> bk = new ArrayList<>();
        Benchmark.resetCounterBS();
        int low = 0;
        int high = b.size() - 1;

        while (low <= high) {
            Benchmark.increaseCounterBS();
            int mid = (low + high) / 2;

            int compare = getBookAttribute(b.get(mid),key2).compareToIgnoreCase(value);

            if (compare == 0) {
                bk.add(b.get(mid));
                break;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return bk;
    }

    public static ArrayList<User> unmLS(String value, ArrayList<User> U, String key2){
        ArrayList<User> us = new ArrayList<>();
        Benchmark.resetCounterLS();
        for (User User :U){
            Benchmark.increaseCounterLS();
            List<String> parts = new ArrayList<>(Arrays.asList(getUserAttribute(User, key2).split("[-.@ ]")));
            parts.add(getUserAttribute(User, key2));
            for (String s : parts){
                if(s.equalsIgnoreCase(value)) {
                    us.add(User);
//              } else if (book.compareTo(Author) > 0) {
//                 break;
                }
            }
        }
        return us;
    }
    public static ArrayList<User> unmBS(String UserName, ArrayList<User> U, String key2) {
        ArrayList<User> us = new ArrayList<>();
        Benchmark.resetCounterBS();
        int low = 0;
        int high = U.size() - 1;

        while (low <= high) {
            Benchmark.increaseCounterBS();
            int mid = (low + high) / 2;

            int compare = getUserAttribute(U.get(mid),key2).compareToIgnoreCase(UserName);

            if (compare == 0) {
                us.add(U.get(mid));
                break;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return us;
    }
}
