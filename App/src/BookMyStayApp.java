import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates booking confirmation, room allocation,
 * uniqueness enforcement, and inventory synchronization.
 *
 * @author YourName
 * @version 6.1
 */
class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 6.1 ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Booking queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Guest1", "Single Room"));
        queue.add(new Reservation("Guest2", "Double Room"));
        queue.add(new Reservation("Guest3", "Single Room"));
        queue.add(new Reservation("Guest4", "Suite Room"));

        // Booking service
        BookingService bookingService = new BookingService(inventory);

        // Process queue
        System.out.println("\nProcessing Bookings:\n");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll(); // FIFO
            bookingService.processBooking(request);
        }

        // Final inventory
        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();
    }
}

/**
 * BookingService handles allocation and confirmation
 */
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room type → allocated room IDs
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    // Counter for generating unique IDs
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation request) {

        String type = request.getRoomType();

        // Check availability
        if (inventory.getAvailability(type) > 0) {

            // Generate unique room ID
            String roomId = type.replace(" ", "").substring(0, 2).toUpperCase() + counter++;

            // Ensure uniqueness
            if (!allocatedRoomIds.contains(roomId)) {

                allocatedRoomIds.add(roomId);

                // Map room type to IDs
                allocationMap.putIfAbsent(type, new HashSet<>());
                allocationMap.get(type).add(roomId);

                // Update inventory immediately
                inventory.updateRoom(type, -1);

                System.out.println("Booking CONFIRMED for " + request.getGuestName()
                        + " | Room Type: " + type
                        + " | Room ID: " + roomId);

            }

        } else {
            System.out.println("Booking FAILED for " + request.getGuestName()
                    + " | Room Type: " + type + " (Not Available)");
        }
    }
}

/**
 * Inventory Service
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateRoom(String type, int change) {
        inventory.put(type, getAvailability(type) + change);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

/**
 * Reservation (Booking Request)
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}