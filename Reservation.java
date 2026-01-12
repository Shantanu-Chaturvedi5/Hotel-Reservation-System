import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    String reservationId;
    String customerName;
    Room room;
    String paymentStatus;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    double totalCost;
    double discount;
    String paymentMethod;

    public Reservation(String reservationId, String customerName, Room room, String paymentStatus,
                       LocalDate checkInDate, LocalDate checkOutDate, double discount, String paymentMethod) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.room = room;
        this.paymentStatus = paymentStatus;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.totalCost = calculateTotalCost();
    }

    private double calculateTotalCost() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double baseCost = room.price * nights;
        return baseCost - (baseCost * discount / 100);
    }

    public String getReservationId() {
        return reservationId;
    }

    public int getRoomNumber() {
        return room.roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getStayDuration() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return reservationId + "," + customerName + "," +
                room.roomNumber + "," + room.category + "," +
                room.price + "," + paymentStatus + "," +
                checkInDate + "," + checkOutDate + "," +
                discount + "," + paymentMethod;
    }

    public String display() {
        return "\n" +
                "╔════════════════════════════════════════╗\n" +
                "║       BOOKING DETAILS                  ║\n" +
                "╚════════════════════════════════════════╝\n" +
                "\n" +
                "Reservation ID  : " + reservationId + "\n" +
                "Customer Name   : " + customerName + "\n" +
                "Room Number     : " + room.roomNumber + "\n" +
                "Category        : " + room.category + "\n" +
                "Price/Night     : ₹" + room.price + "\n" +
                "\n" +
                "Check-In Date   : " + checkInDate + "\n" +
                "Check-Out Date  : " + checkOutDate + "\n" +
                "Stay Duration   : " + getStayDuration() + " nights\n" +
                "\n" +
                "Discount        : " + discount + "%\n" +
                "Total Cost      : ₹" + String.format("%.2f", totalCost) + "\n" +
                "Payment Method  : " + paymentMethod + "\n" +
                "Payment Status  : " + paymentStatus;
    }
}