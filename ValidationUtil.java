import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    public static boolean isValidCustomerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Customer name cannot be empty.");
            return false;
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            System.out.println("Error: Customer name can only contain letters and spaces.");
            return false;
        }
        return true;
    }

    public static boolean isValidRoomNumber(String input, List<Room> rooms) {
        try {
            int roomNo = Integer.parseInt(input);
            for (Room r : rooms) {
                if (r.roomNumber == roomNo) {
                    return true;
                }
            }
            System.out.println("Error: Room number does not exist.");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid room number.");
            return false;
        }
    }

    public static boolean isValidPrice(String input) {
        try {
            double price = Double.parseDouble(input);
            return price > 0;
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid price.");
            return false;
        }
    }

    public static LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Error: Date cannot be in the past.");
                return null;
            }
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use yyyy-MM-dd (e.g., 2026-01-15)");
            return null;
        }
    }

    public static boolean hasActiveReservation(String customerName, List<Reservation> reservations) {
        for (Reservation r : reservations) {
            if (r.getCustomerName().equalsIgnoreCase(customerName)) {
                System.out.println("Warning: Customer '" + customerName + "' already has an active reservation.");
                return true;
            }
        }
        return false;
    }

    public static boolean isValidDiscount(String input) {
        try {
            double discount = Double.parseDouble(input);
            if (discount >= 0 && discount <= 100) {
                return true;
            }
            System.out.println("Error: Discount must be between 0 and 100.");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid discount percentage.");
            return false;
        }
    }
}
