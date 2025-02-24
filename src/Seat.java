package src;

public class Seat {
    private final SeatType type;
    private SeatStatus status;
    private final int seatNumber;
    private final double price;

    public Seat(SeatType type, int seatNumber, double price) {
        this.type = type;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
    }

    public SeatType getType() {
        return type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
        System.out.println("Seat " + seatNumber + " is now " + status);
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }
}
