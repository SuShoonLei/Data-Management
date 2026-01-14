package src.ManagementSystem;

/*
    Member can borrow up to 5 books for 2 weeks.
    Therefore, Book[] book in User.java should have the size of 5.
 */

import java.util.Random;

public class Member extends User {

    int numBorrowed;                // number of books the member currently borrows

    private String name;                // name should be provided by user
    protected String account;             // account number is assigned by the LMS
    private String email;
    private String phone;
    protected Book[] borrowedBooks;       // list of borrowing books
    private static final int borrowingPeriod = 14;                // borrowing period will be differently assigned according to user level
    private static final int maxNumBorrowing = 5;
    // TODO: complete the subclass
    //  You can declare variables, constructors, and methods as may times as you want

    // Constructor
    public Member (String name, String email, String phone) {
        super(name, email, phone);
        createAccount();
        this.numBorrowed = 0 ;
        this.borrowedBooks = new Book[maxNumBorrowing];
    }
    public Member(String name, String email, String phone, String account) {
        super(name, email, phone);
        this.account = account;
        this.numBorrowed = 0 ;
        this.borrowedBooks = new Book[maxNumBorrowing];
    }

    @Override
    public void createAccount() {
        Random random = new Random();
        int randomNineDigitNumber = 100000000 + random.nextInt(900000000);  // Generate 8 digits
        this.account = String.valueOf(randomNineDigitNumber);
    }

    @Override
    public boolean borrowBook(Book book) {
        if(book.avail == Availability.A){
            if(numBorrowed<maxNumBorrowing){
                book.avail = Availability.B;
                book.bAccount = account;
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
    void printUser() {
        String borrowedBook = "";  // thing i need to change
         if(numBorrowed > 0){
            borrowedBook = borrowedBooks[0].id;
            }
         if(numBorrowed > 1 ){
             borrowedBook += "(+"+ (numBorrowed-1) +")";
         }
        System.out.printf("%-30.30s %-10s %-30.30s %-15s %-11s\n", getName(),getAccount(), getEmail(), getPhone(), borrowedBook);
    }

    @Override
    String getAccount(){return account;}
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
