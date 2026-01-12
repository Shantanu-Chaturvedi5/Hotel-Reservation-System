import java.time.LocalDate;
import java.util.*;

public class Main {

    static final Scanner sc = new Scanner(System.in);
    static final List<Room> rooms;
    static final List<Reservation> reservations;
    
    static {
        rooms = FileManager.loadRooms();
        reservations = FileManager.loadReservations(rooms);
    }

    public static void main(String[] args) {
        displayWelcome();
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> searchRooms();
                case 3 -> bookRoom();
                case 4 -> viewReservation();
                case 5 -> viewAllReservations();
                case 6 -> customerBookingHistory();
                case 7 -> cancelReservation();
                case 8 -> viewHotelStats();
                case 9 -> exitSystem();
                default -> System.out.println("✗ Invalid choice! Please try again.");
            }
        }
    }

    static void displayWelcome() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO HOTEL RESERVATION SYSTEM        ║");
        System.out.println("║         Your Perfect Stay Awaits!            ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }

    static void displayMainMenu() {
        System.out.println("\n" + "═".repeat(45));
        System.out.println("           MAIN MENU");
        System.out.println("═".repeat(45));
        System.out.println("1. View All Available Rooms");
        System.out.println("2. Search Rooms (by Category/Price)");
        System.out.println("3. Book a Room");
        System.out.println("4. View Booking Details");
        System.out.println("5. View All Reservations");
        System.out.println("6. Customer Booking History");
        System.out.println("7. Cancel Reservation");
        System.out.println("8. View Hotel Statistics");
        System.out.println("9. Exit");
        System.out.println("═".repeat(45));
        System.out.print("Enter your choice (1-9): ");
    }

    static int getIntInput() {
        try {
            int input = sc.nextInt();
            sc.nextLine();
            return input;
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1;
        }
    }

    static void viewRooms() {
        System.out.println("\n┌─ AVAILABLE ROOMS ─────────────────────────┐");
        List<Room> available = rooms.stream().filter(r -> r.available).toList();
        
        if (available.isEmpty()) {
            System.out.println("│ No rooms available at the moment.          │");
        } else {
            System.out.printf("│ %-41s │\n", "Room# | Category   | Price/Night");
            System.out.println("├───────────────────────────────────────────┤");
            for (Room r : available) {
                System.out.printf("│ %4d | %-10s | ₹%-7.0f              │\n", 
                    r.roomNumber, r.category, r.price);
            }
        }
        System.out.println("└───────────────────────────────────────────┘");
    }

    static void searchRooms() {
        System.out.println("\n┌─ SEARCH ROOMS ─────────────────────────────┐");
        System.out.println("│ 1. Search by Category                       │");
        System.out.println("│ 2. Search by Price Range                    │");
        System.out.println("│ 3. Back to Main Menu                         │");
        System.out.println("└───────────────────────────────────────────┘");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> searchByCategory();
            case 2 -> searchByPrice();
            case 3 -> {
                // Return to main menu
            }
            default -> System.out.println("✗ Invalid choice!");
        }
    }

    static void searchByCategory() {
        System.out.println("\nAvailable Categories: Standard, Deluxe, Suite");
        System.out.print("Enter category to search: ");
        String category = sc.nextLine().trim();
        
        List<Room> results = rooms.stream()
            .filter(r -> r.category.equalsIgnoreCase(category) && r.available)
            .toList();
        
        if (results.isEmpty()) {
            System.out.println("✗ No available rooms in '" + category + "' category.");
        } else {
            System.out.println("\n" + "─".repeat(45));
            System.out.println("Rooms in " + category + " category:");
            System.out.println("─".repeat(45));
            for (Room r : results) {
                System.out.printf("Room %d - ₹%.0f/night\n", r.roomNumber, r.price);
            }
        }
    }

    static void searchByPrice() {
        System.out.print("Enter minimum price: ₹");
        double minPrice = sc.nextDouble();
        System.out.print("Enter maximum price: ₹");
        double maxPrice = sc.nextDouble();
        sc.nextLine();
        
        List<Room> results = rooms.stream()
            .filter(r -> r.available && r.price >= minPrice && r.price <= maxPrice)
            .toList();
        
        if (results.isEmpty()) {
            System.out.println("✗ No available rooms in this price range.");
        } else {
            System.out.println("\n" + "─".repeat(45));
            System.out.println("Rooms in ₹" + minPrice + " - ₹" + maxPrice + " range:");
            System.out.println("─".repeat(45));
            for (Room r : results) {
                System.out.printf("Room %d (%s) - ₹%.0f/night\n", r.roomNumber, r.category, r.price);
            }
        }
    }

    static void bookRoom() {
        System.out.println("\n┌─ BOOK A ROOM ──────────────────────────────┐");
        
        boolean bookMore = true;
        while (bookMore) {
            viewRooms();
            System.out.print("\nEnter Room Number to book: ");
            String roomInput = sc.nextLine().trim();
            
            if (!ValidationUtil.isValidRoomNumber(roomInput, rooms)) {
                continue;
            }
            
            int roomNo = Integer.parseInt(roomInput);
            Room selectedRoom = null;
            
            for (Room r : rooms) {
                if (r.roomNumber == roomNo) {
                    if (!r.available) {
                        System.out.println("✗ This room is already booked.");
                        continue;
                    }
                    selectedRoom = r;
                    break;
                }
            }
            
            if (selectedRoom == null) {
                System.out.println("✗ Room not available.");
                continue;
            }
            
            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine().trim();
            
            if (!ValidationUtil.isValidCustomerName(name)) {
                continue;
            }
            
            if (ValidationUtil.hasActiveReservation(name, reservations)) {
                System.out.print("Continue anyway? (yes/no): ");
                if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    continue;
                }
            }
            
            System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
            LocalDate checkIn = null;
            while (checkIn == null) {
                checkIn = ValidationUtil.parseDate(sc.nextLine().trim());
                if (checkIn == null) {
                    System.out.print("Try again (yyyy-MM-dd): ");
                }
            }
            
            System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
            LocalDate checkOut = null;
            while (checkOut == null || !checkOut.isAfter(checkIn)) {
                checkOut = ValidationUtil.parseDate(sc.nextLine().trim());
                if (checkOut != null && !checkOut.isAfter(checkIn)) {
                    System.out.println("Error: Check-out date must be after check-in date.");
                    System.out.print("Try again (yyyy-MM-dd): ");
                    checkOut = null;
                }
            }
            
            double discount = Payment.applyPromoCode(sc);
            String paymentMethod = Payment.selectPaymentMethod(sc);
            
            double totalCost = selectedRoom.price * (checkOut.getDayOfYear() - checkIn.getDayOfYear());
            
            if (Payment.processPayment(totalCost, sc)) {
                selectedRoom.available = false;
                Reservation res = new Reservation(
                        ReservationIDGenerator.generateReservationID(reservations),
                        name,
                        selectedRoom,
                        "Paid",
                        checkIn,
                        checkOut,
                        discount,
                        paymentMethod
                );
                reservations.add(res);
                FileManager.saveRooms(rooms);
                FileManager.saveAllReservations(reservations);
                System.out.println("\n✓ Booking successful!");
                System.out.println(res.display());
            } else {
                System.out.println("✗ Booking cancelled.");
            }
            
            System.out.print("\nBook another room? (yes/no): ");
            bookMore = sc.nextLine().trim().equalsIgnoreCase("yes");
        }
        System.out.println("└───────────────────────────────────────────┘");
    }

    static void viewReservation() {
        System.out.print("\nEnter Reservation ID: ");
        String id = sc.nextLine().trim();

        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(id)) {
                System.out.println(r.display());
                return;
            }
        }
        System.out.println("✗ Reservation not found.");
    }

    static void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("\n✗ No reservations found.");
            return;
        }
        
        System.out.println("\n┌─ ALL RESERVATIONS ─────────────────────────┐");
        System.out.printf("│ %-10s | %-15s | Room | Status │\n", "Res ID", "Customer");
        System.out.println("├───────────────────────────────────────────┤");
        
        for (Reservation r : reservations) {
            System.out.printf("│ %-10s | %-15s | %4d | %-6s │\n", 
                r.getReservationId(), 
                r.getCustomerName(), 
                r.getRoomNumber(), 
                r.paymentStatus);
        }
        System.out.println("└───────────────────────────────────────────┘");
    }

    static void customerBookingHistory() {
        System.out.print("\nEnter Customer Name: ");
        String customerName = sc.nextLine().trim();
        
        List<Reservation> history = reservations.stream()
            .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
            .toList();
        
        if (history.isEmpty()) {
            System.out.println("✗ No bookings found for '" + customerName + "'.");
            return;
        }
        
        System.out.println("\n" + "─".repeat(50));
        System.out.println("Booking History for: " + customerName);
        System.out.println("─".repeat(50));
        
        for (Reservation r : history) {
            System.out.println("\nReservation ID: " + r.getReservationId());
            System.out.println("Room: " + r.getRoomNumber() + " (" + r.room.category + ")");
            System.out.println("Check-In: " + r.checkInDate + " | Check-Out: " + r.checkOutDate);
            System.out.println("Stay Duration: " + r.getStayDuration() + " nights");
            System.out.println("Total Cost: ₹" + String.format("%.2f", r.getTotalCost()));
            System.out.println("Status: " + r.paymentStatus);
        }
    }

    static void cancelReservation() {
        System.out.print("\nEnter Reservation ID to cancel: ");
        String id = sc.nextLine().trim();

        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation r = it.next();
            if (r.getReservationId().equalsIgnoreCase(id)) {
                System.out.println("\nReservation Details:");
                System.out.println("  Reservation ID: " + r.getReservationId());
                System.out.println("  Customer: " + r.getCustomerName());
                System.out.println("  Room: " + r.getRoomNumber());
                System.out.println("  Total Cost: ₹" + String.format("%.2f", r.getTotalCost()));
                
                double refund = r.getTotalCost() * 0.8; // 80% refund
                System.out.println("  Refund Amount (80%): ₹" + String.format("%.2f", refund));
                
                System.out.print("\nConfirm cancellation? (yes/no): ");
                if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    r.room.available = true;
                    it.remove();
                    FileManager.saveRooms(rooms);
                    FileManager.saveAllReservations(reservations);
                    System.out.println("✓ Reservation cancelled successfully.");
                    System.out.println("✓ Refund of ₹" + String.format("%.2f", refund) + " processed.");
                } else {
                    System.out.println("Cancellation aborted.");
                }
                return;
            }
        }
        System.out.println("✗ Invalid Reservation ID.");
    }

    static void viewHotelStats() {
        int totalRooms = rooms.size();
        long availableRooms = rooms.stream().filter(r -> r.available).count();
        long bookedRooms = totalRooms - availableRooms;
        double occupancyRate = (bookedRooms * 100.0) / totalRooms;
        
        double totalRevenue = reservations.stream()
            .mapToDouble(Reservation::getTotalCost)
            .sum();
        
        long standardRooms = rooms.stream().filter(r -> r.category.equals("Standard") && r.available).count();
        long deluxeRooms = rooms.stream().filter(r -> r.category.equals("Deluxe") && r.available).count();
        long suiteRooms = rooms.stream().filter(r -> r.category.equals("Suite") && r.available).count();
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         HOTEL STATISTICS                ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Rooms          : %20d ║\n", totalRooms);
        System.out.printf("║ Available Rooms      : %20d ║\n", availableRooms);
        System.out.printf("║ Booked Rooms         : %20d ║\n", bookedRooms);
        System.out.printf("║ Occupancy Rate       : %19.1f%% ║\n", occupancyRate);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Available - Standard : %20d ║\n", standardRooms);
        System.out.printf("║ Available - Deluxe   : %20d ║\n", deluxeRooms);
        System.out.printf("║ Available - Suite    : %20d ║\n", suiteRooms);
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Revenue        : ₹%-17.2f ║\n", totalRevenue);
        System.out.printf("║ Active Reservations  : %20d ║\n", reservations.size());
        System.out.println("╚════════════════════════════════════════╝");
    }

    static void exitSystem() {
        FileManager.saveRooms(rooms);
        FileManager.saveAllReservations(reservations);
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   Thank you for using our system!       ║");
        System.out.println("║   We look forward to your next stay!    ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        System.exit(0);
    }
}