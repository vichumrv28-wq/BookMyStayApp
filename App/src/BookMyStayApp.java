import java.util.HashMap;
import java.util.Map;

/**
 * UseCase4RoomSearch
 *
 * Demonstrates read-only room search using centralized inventory.
 * Displays only available rooms without modifying system state.
 *
 * @author YourName
 * @version 4.1
 */
class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 4.1 ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room objects (domain model)
        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom(1, 200, 1000));
        rooms.put("Double Room", new DoubleRoom(2, 350, 1800));
        rooms.put("Suite Room", new SuiteRoom(3, 500, 3000));

        // Perform search (read-only)
        System.out.println("\nAvailable Rooms:\n");

        for (String type : rooms.keySet()) {
            int available = inventory.getAvailability(type);

            // Filter unavailable rooms
            if (available > 0) {
                Room room = rooms.get(type);
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

/**
 * Inventory class (read-only usage here)
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // intentionally unavailable
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/**
 * Abstract Room class
 */
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

/**
 * Single Room
 */
class SingleRoom extends Room {
    public SingleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

/**
 * Double Room
 */
class DoubleRoom extends Room {
    public DoubleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

/**
 * Suite Room
 */
class SuiteRoom extends Room {
    public SuiteRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}