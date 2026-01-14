package src.ManagementSystem;

/*
    Guest can borrow only 1 book for 1 week.
    Therefore, Book[] book in User.java should have the size of 1.
 */

import java.util.Random;

public class Guest extends User {

    int numBorrowed;
    private String name;                // name should be provided by user
    protected String account;             // account number is assigned by the LMS
    private String email;
    private String phone;
    protected Book[] borrowedBooks;       // list of borrowing books
    private static final int borrowingPeriod = 7;                // borrowing period will be differently assigned according to user level
    private static final int maxNumBorrowing = 1;
    // TODO: complete the subclass
    //  You can declare variables, constructors, and methods as may times as you want

    // Constructor
    public Guest(String name, String email, String phone) {
        super(name, email, phone);
        createAccount();
        this.numBorrowed = 0 ;
        this.borrowedBooks = new Book[maxNumBorrowing];
    }

    public Guest(String name, String email, String phone, String account) {
        super(name, email, phone);
        this.account = account;
        this.numBorrowed = 0 ;
        this.borrowedBooks = new Book[maxNumBorrowing];
    }

    @Override
    public void createAccount() {
        Random random = new Random();
        int randomEightDigitNumber = 10000000 + random.nextInt(90000000);  // Generate 8 digits
        this.account = "T" + randomEightDigitNumber;
    }

    @Override
    public boolean borrowBook(Book book) {
        if(book.avail == Availability.A){
            if(numBorrowed < maxNumBorrowing){
                book.avail = Availability.B;
                borrowedBooks[numBorrowed]=book;
                numBorrowed++;
                return true;
            }
        }
        return false;
    }

    @Override
    public void listBorrowedBooks() {
        System.out.println("["+name+"] "+ numBorrowed+" book(s) borrowed");
        printBookinCatHead();
        for(int i = 0 ; i < numBorrowed ; i++){
            borrowedBooks[i].printBookinCat();
        }
    }

    public static void printBookinCatHead () {
        System.out.printf("%-30.30s %-13.13s %-15s %-5s %-13s %-13s %-13s\n", "Title", "Author", "ID", "Avail", "Borrow", "Due", "B-Account");
    }
    @Override
    String getAccount(){return account;}
    @Override
    void printUser() {
        String borrowedBook = "";
        if(numBorrowed > 0){
            borrowedBook = borrowedBooks[0].id;
        }
        System.out.printf("%-30.30s %-10s %-30.30s %-15s %-11s\n", getName(), getAccount(), getEmail(), getPhone(), borrowedBook);
    }
@Override
    public void printUserSummary() {
        System.out.printf("%-30s %d / %d\n", getName(), numBorrowed, maxNumBorrowing);
    }

    @Override
    public int compareTo(String Username) {
        return super.compareTo(Username);
    }
    // TODO: you are allowed to add more methods if necessary.
}
