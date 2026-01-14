package src.ManagementSystem;

import java.util.Random;

enum Availability {A, B, Bw;
    public static Availability fromString(String str) {
        switch (str.toUpperCase()) {
            case "A":
                return A; // Available
            case "B":
                return B; // Reserved
            case "BW":
                return Bw; // Borrowed
            case "RW":
                return Bw;
            case "R":
                return B;
            default:
                throw new IllegalArgumentException("Unknown availability: " + str);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
public class Book {
    public String title;
    String type;
    String author;
    public String id;
    Availability avail;
    String bDate;
    String dDate;
    String bAccount;
    boolean checked;
    public Book(){
        this.title = null;
        this.id = null;
        avail = Availability.A;
    }
    public Book(String title,String type, String Author,String ID,String Avail,String Borrow,String Due,String account){
        this.title = title;
        this.type = type;
        this.author = Author;
        this.id = ID;
        this.avail = Availability.fromString(Avail);
        this.bDate = Borrow;
        this.dDate = Due;
        this.bAccount = account;
        checked = false;
    }
    public Book(String title,String type, String Author,String ID,String Avail){
        this.title = title;
        this.type = type;
        this.author = Author;
        this.id = ID;
        this.avail = Availability.fromString(Avail);
        checked = false;
    }
    public void printBookinCat () {
        if(bDate != null){
            System.out.printf("%-30.30s %-13.13s %-18s %-5s %-13s %-13s %-13s\n", title, author, id, avail, bDate, dDate, bAccount);
        }else {
            System.out.printf("%-30.30s %-13.13s %-18s %-5s\n", title, author, id, avail);
        }
    }


    public int compareTo(String AuthorName) {
        if (author.compareTo(AuthorName) == 0)
            return 0;
        else if (author.compareTo(AuthorName) > 0)
            return 1;
        else
            return -1;
    }
}
