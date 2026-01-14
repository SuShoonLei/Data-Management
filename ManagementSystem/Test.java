package src.ManagementSystem;

public class Test {
    public static void testLab3() {

        // TODO: you must borrow books only using the three test scenarios.
        //  But do not update the origiral data file - books.json, users.json.

        // Test Scenario 1: a new member borrows a book borrowed by another user
        // Note that if you have a constructor which can be instantiated with title and id, you may use it
        // for example,
        //  Book[] testBooks1 = {new Book("Advanced Programming", "id")};
        System.out.println("\nScenario 1");
        Book[] testBooks1 = {new Book()};
        testBooks1[0].id = "TEC-78910";
        testBooks1[0].title = "Advanced Programming";
        test("William Laker", "William.laker@oswego.edu", "315-111-2222", "member", testBooks1);

        // Test Scenario 2: a guest borrows 2 books
        System.out.println("\nScenario 2");
        Book[] testBooks2 = {new Book(), new Book()};
        testBooks2[0].id = "HIS-67890";
        testBooks2[0].title = "History of Time";
        testBooks2[1].id = "TEC-56789";
        testBooks2[1].title = "Programming Basics";
        test("Elise Oswego", "Elise.oswego@oswego.edu", "315-342-5623", "guest", testBooks2);

        // Test Scenario 3: a new member borrows 6 books
        System.out.println("\nScenario 3");
        Book[] testBooks3 = {new Book(), new Book(), new Book(), new Book(), new Book(), new Book()};
        testBooks3[0].id = "TEC-12434";
        testBooks3[0].title = "Advanced Data Structure Explorer";
        testBooks3[1].id = "SCI-23456";
        testBooks3[1].title = "A Brief History";
        testBooks3[2].id = "TEC-78901";
        testBooks3[2].title = "Tech Innovations";
        testBooks3[3].id = "SCI-12346";
        testBooks3[3].title = "The Last Frontier";
        testBooks3[4].id = "FIC-12345";
        testBooks3[4].title = "The Great Adventure";
        testBooks3[5].id = "ART-45678";
        testBooks3[5].title = "Creative Arts";
        test("Stephen Jobbs", "Stephen.jobbs@abc.com", "985-434-2521", "member", testBooks3);

        // TODO: more scenarios as requested
    }

    // test cases for Lab 3
    public static void test(String uName, String uEmail, String uPhone, String type, Book[] testBooks) {
        User user;

        // create an account according to the type of the user
        if (type.equals("member"))
            user = new Member(uName, uEmail, uPhone);
        else
            user = new Guest(uName, uEmail, uPhone);

        // borrow book(s)
        for (Book book: testBooks) {
            if (!user.borrowBook(book))
                System.out.println("* failed: " + book.id +"("+book.title + ") cannot be borrowed");
        }

        // list the borrowed book(s)
        user.listBorrowedBooks();
    }
}
