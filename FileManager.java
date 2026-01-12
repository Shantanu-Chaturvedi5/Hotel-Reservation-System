import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@SuppressWarnings("CallToPrintStackTrace")
public class FileManager {

    public static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                if (d.length < 4) {
                    System.err.println("Warning: Skipping invalid room entry: " + line);
                    continue;
                }
                try {
                    rooms.add(new Room(
                            Integer.parseInt(d[0]),
                            d[1],
                            Double.parseDouble(d[2]),
                            Boolean.parseBoolean(d[3])
                    ));
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Skipping room with invalid data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("ℹ rooms.txt not found. Starting with no rooms.");
        }
        return rooms;
    }

    public static void saveRooms(List<Room> rooms) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("rooms.txt"))) {
            for (Room r : rooms) {
                pw.println(r);
            }
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }

    public static List<Reservation> loadReservations(List<Room> rooms) {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                if (d.length < 9) {
                    System.err.println("Warning: Skipping invalid reservation entry: " + line);
                    continue;
                }
                
                try {
                    int roomNo = Integer.parseInt(d[2]);
                    Room foundRoom = null;
                    for (Room r : rooms) {
                        if (r.roomNumber == roomNo) {
                            foundRoom = r;
                            break;
                        }
                    }
                    
                    if (foundRoom != null) {
                        foundRoom.available = false;
                        LocalDate checkIn = LocalDate.parse(d[6]);
                        LocalDate checkOut = LocalDate.parse(d[7]);
                        double discount = Double.parseDouble(d[8]);
                        String paymentMethod = d.length > 9 ? d[9] : "Credit Card";
                        
                        reservations.add(new Reservation(
                                d[0],
                                d[1],
                                foundRoom,
                                d[5],
                                checkIn,
                                checkOut,
                                discount,
                                paymentMethod
                        ));
                    }
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Warning: Skipping reservation with invalid data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("ℹ reservations.txt not found. Starting with no reservations.");
        }
        return reservations;
    }

    public static void saveAllReservations(List<Reservation> reservations) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reservations.txt"))) {
            for (Reservation r : reservations) {
                pw.println(r);
            }
        } catch (IOException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }
}