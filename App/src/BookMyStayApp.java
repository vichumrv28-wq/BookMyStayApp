import java.util.*;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting of confirmed reservations.
 * Maintains insertion order using List and generates summary reports.
 *
 * @author YourName
 * @version 8.1
 */
class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay ");
        System.out.println(" Version: 8.1 ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Guest1", "Single Room"));
        bookingQueue.add(new Reservation("Guest2", "Double Room"));
        bookingQueue.add(new Reservation("Guest3", "Single Room"));
        bookingQueue.add(new Reservation("Guest4", "Suite Room"));

        // Booking service with history
        BookingService bookingService = new BookingService(inventory);

        System.out.println("\nProcessing Bookings:\n");

        while (!bookingQueue.isEmpty()) {
            bookingService.processBooking(bookingQueue.poll());
        }

        // Reporting
        BookingReportService reportService = new BookingReportService(bookingService.getBookingHistory());

        System.out.println("\nBooking History Report:\n");
        reportService.displayAllBookings();

        System.out.println("\nSummary Report:");
        reportService.displayBookingSummary();
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
 * BookingService handles allocation, confirmation, and history tracking
 */
class BookingService {

    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private List<Reservation> bookingHistory = new ArrayList<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation request) {

        String type = request.getRoomType();

        if (inventory.getAvailability(type) > 0) {

            // Generate unique room ID
            String roomId;
            do {
                roomId = type.replace(" ", "").substring(0, 2).toUpperCase() + counter++;
            } while (allocatedRoomIds.contains(roomId));

            allocatedRoomIds.add(roomId);

            // Update inventory immediately
            inventory.updateRoom(type, -1);

            // Store in booking history
            request.setRoomId(roomId);
            bookingHistory.add(request);

            System.out.println("Booking CONFIRMED for " + request.getGuestName()
                    + " | Room Type: " + type
                    + " | Room ID: " + roomId);

        } else {
            System.out.println("Booking FAILED for " + request.getGuestName()
                    + " | Room Type: " + type + " (Not Available)");
        }
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }
}

/**
 * Reservation class
 */
class Reservation {

    private String guestName;
    private String roomType;
    private String roomId; // assigned after confirmation

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

/**
 * BookingReportService handles reporting from booking history
 */
class BookingReportService {

    private List<Reservation> history;

    public BookingReportService(List<Reservation> history) {
        this.history = history;
    }

    // Display all confirmed bookings
    public void displayAllBookings() {
        for (Reservation r : history) {
            System.out.println("Guest: " + r.getGuestName()
                    + " | Room Type: " + r.getRoomType()
                    + " | Room ID: " + r.getRoomId());
        }
    }

    // Display summary (number of bookings per room type)
    public void displayBookingSummary() {
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : history) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (Map.Entry<String, Integer> e : summary.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue() + " bookings");
        }
    }
}
