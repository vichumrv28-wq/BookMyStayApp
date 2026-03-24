import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory using HashMap.
 * Replaces scattered variables with a single source of truth.
 *
 * @author YourName
 * @version 3.1
 */
class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 3.1 ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        System.out.println("\nInitial Room Availability:");
        inventory.displayInventory();

        // Update availability
        inventory.updateRoom("Single Room", -1); // booking 1 room
        inventory.updateRoom("Suite Room", +1);  // adding 1 room

        // Display updated inventory
        System.out.println("\nUpdated Room Availability:");
        inventory.displayInventory();
    }
}

/**
 * RoomInventory class manages centralized availability
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled)
    public void updateRoom(String roomType, int change) {
        int current = getAvailability(roomType);
        int updated = current + change;

        if (updated >= 0) {
            inventory.put(roomType, updated);
            System.out.println(roomType + " updated to " + updated);
        } else {
            System.out.println("Cannot reduce below zero for " + roomType);
        }
    }

    // Display all inventory
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}