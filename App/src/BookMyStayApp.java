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

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Guest1", "Single Room"));
        queue.add(new Reservation("Guest2", "Double Room"));
        queue.add(new Reservation("Guest3", "Single Room"));
        queue.add(new Reservation("Guest4", "Suite Room"));

        BookingService bookingService = new BookingService(inventory);

        System.out.println("\nProcessing Bookings:\n");

        while (!queue.isEmpty()) {
            bookingService.processBooking(queue.poll());
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();
    }
}

/**
 * BookingService handles allocation and confirmation
 */
class BookingService {

    private RoomInventory inventory;

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation request) {

        String type = request.getRoomType();

        if (inventory.getAvailability(type) > 0) {

            // ✅ SAFE UNIQUE ID GENERATION
            String roomId;
            do {
                roomId = generateRoomId(type);
            } while (allocatedRoomIds.contains(roomId));

            allocatedRoomIds.add(roomId);

            allocationMap.putIfAbsent(type, new HashSet<>());
            allocationMap.get(type).add(roomId);

            inventory.updateRoom(type, -1);

            System.out.println("Booking CONFIRMED for " + request.getGuestName()
                    + " | Room Type: " + type
                    + " | Room ID: " + roomId);

        } else {
            System.out.println("Booking FAILED for " + request.getGuestName()
                    + " | Room Type: " + type + " (Not Available)");
        }
    }

    // Separate method for ID generation
    private String generateRoomId(String type) {
        String prefix = type.replace(" ", "").substring(0, 2).toUpperCase();
        return prefix + (counter++);
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
 * Reservation class
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