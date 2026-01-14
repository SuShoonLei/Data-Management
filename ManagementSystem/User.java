package src.ManagementSystem;

/*
This is the abstract class User, which is extended to class Member and Guest
This class SHOULD NOT be edited.
 */


public abstract class User {
    private String name;                // name should be provided by user
    protected String account;             // account number is assigned by the LMS
    private String email;
    private String phone;
    private Book[] borrowedBooks;       // list of borrowing books
    int borrowingPeriod;                // borrowing period will be differently assigned according to user level
    int maxNumBorrowing;                // maximum number of borrowing books are different accroding to user level

    // Constructor
    public User (String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    String getAccount() {
        return account;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getEmail() {
        return email;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getPhone() {
        return phone;
    }

    Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    // Abstract methods; MUST be implemented in subclasses extending Class User
    public abstract void createAccount();                  // the LMS creates a unique account number
    public abstract boolean borrowBook(Book book);         // borrow the book; if it is unavailable, it returns false
    public abstract void listBorrowedBooks();              // list all the borrowed books

    void printUser() {
        System.out.printf("%-30.30s %-10s %-30.30s %-20s\n", name, account, email, phone);
    }

    public abstract void printUserSummary();

    public int compareTo(String Username) {
        if (name.compareTo(Username) == 0)
            return 0;
        else if (name.compareTo(Username) > 0)
            return 1;
        else
            return -1;
    }
}


