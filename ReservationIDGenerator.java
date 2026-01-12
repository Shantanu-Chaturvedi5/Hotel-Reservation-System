import java.util.List;

public class ReservationIDGenerator {

    public static String generateReservationID(List<Reservation> reservations) {
        // Get the highest existing ID number
        int maxId = 1000;
        for (Reservation r : reservations) {
            String id = r.getReservationId();
            if (id.startsWith("RES")) {
                try {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "RES" + (maxId + 1);
    }
}
