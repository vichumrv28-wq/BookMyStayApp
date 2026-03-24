import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request intake using Queue (FIFO).
 * Ensures fair ordering of booking requests without modifying inventory.
 *
 * @author YourName
 * @version 5.1
 */
class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 5.1 ");
        System.out.println("======================================");

        // Create booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulating guest booking requests
        bookingQueue.add(new Reservation("Guest1", "Single Room"));
        bookingQueue.add(new Reservation("Guest2", "Double Room"));
        bookingQueue.add(new Reservation("Guest3", "Suite Room"));
        bookingQueue.add(new Reservation("Guest4", "Single Room"));

        System.out.println("\nBooking Requests in Queue (FIFO Order):\n");

        // Display queue without modifying it
        for (Reservation r : bookingQueue) {
            r.display();
        }

        System.out.println("\nTotal Requests in Queue: " + bookingQueue.size());
    }
}

/**
 * Reservation class representing a booking request
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}