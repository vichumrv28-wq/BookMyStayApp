/**
 * UseCase2RoomInitialization
 *
 * Demonstrates Room types using abstraction and inheritance.
 * Displays room details and static availability.
 *
 * @author YourName
 * @version 2.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 2.1 ");
        System.out.println("======================================");

        // Polymorphism: using Room reference
        Room single = new SingleRoom(1, 200, 1000);
        Room doub = new DoubleRoom(2, 350, 1800);
        Room suite = new SuiteRoom(3, 500, 3000);

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\nRoom Details:\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doub.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");
    }
}

// Abstract class
abstract class Room {
    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }

    public abstract String getRoomType();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}