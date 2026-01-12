public class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    public Room(int roomNumber, String category, double price, boolean available) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    @Override
    public String toString() {
        return roomNumber + "," + category + "," + price + "," + available;
    }
}