package src.ManagementSystem;

public class LinkedList {
    public Node head;

    // Inner Node class to hold ticket data
    public static class Node {
        Ticket ticket;
        Node next;

        Node(Ticket ticket) {
            this.ticket = ticket;
            this.next = null;
        }
    }

    // Add a ticket to the end of the linked list
    public void add(Ticket ticket) {
        Node newNode = new Node(ticket);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Print all tickets in the linked list
    public void printTickets() {
        Node current = head;
        while (current != null) {
            System.out.println(current.ticket); // Assuming Ticket has a meaningful toString() method
            current = current.next;
        }
    }
}
