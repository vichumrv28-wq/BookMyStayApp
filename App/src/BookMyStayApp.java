import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates structured input validation and error handling
 * for booking requests. Prevents invalid room types and negative inventory.
 * @author YourName
 * @version 9.1
 */
class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 9.1 ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulating some guest requests (one invalid room type)
        bookingQueue.add(new Reservation("Guest1", "Single Room"));
        bookingQueue.add(new Reservation("Guest2", "Penthouse")); // invalid
        bookingQueue.add(new Reservation("Guest3", "Double Room"));
        bookingQueue.add(new Reservation("Guest4", "Suite Room"));
        bookingQueue.add(new Reservation("Guest5", "Single Room"));

        System.out.println("\nProcessing Bookings with Validation:\n");

        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();
            try {
                bookingService.processBooking(request);
            } catch (InvalidBookingException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();
    }
}

/**
 * Custom Exception for invalid booking scenarios
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * BookingService handles allocation, confirmation, and error handling
 */
class BookingService {

    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private List<Reservation> bookingHistory = new ArrayList<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation request) throws InvalidBookingException {

        String type = request.getRoomType();

        // Validate room type
        if (!inventory.isValidRoomType(type)) {
            throw new InvalidBookingException("Invalid room type '" + type + "' requested by " + request.getGuestName());
        }

        // Validate availability
        int available = inventory.getAvailability(type);
        if (available <= 0) {
            throw new InvalidBookingException("No available rooms for type '" + type + "' for " + request.getGuestName());
        }

        // Generate unique room ID safely
        String roomId;
        do {
            roomId = type.replace(" ", "").substring(0, 2).toUpperCase() + counter++;
        } while (allocatedRoomIds.contains(roomId));

        allocatedRoomIds.add(roomId);

        // Update inventory immediately
        inventory.updateRoom(type, -1);

        // Store booking in history
        request.setRoomId(roomId);
        bookingHistory.add(request);

        System.out.println("Booking CONFIRMED for " + request.getGuestName()
                + " | Room Type: " + type
                + " | Room ID: " + roomId);
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
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

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateRoom(String type, int change) {
        int newCount = getAvailability(type) + change;
        if (newCount < 0) {
            throw new IllegalStateException("Inventory cannot be negative for " + type);
        }
        inventory.put(type, newCount);
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
    private String roomId;

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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}