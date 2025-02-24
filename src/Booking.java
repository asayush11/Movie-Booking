package src;

public class Booking {
    private final String id;
    private final Show show;
    private final User user;
    private final int noOfSeats;
    private final SeatType seatType;
    private BookingStatus status;

    public Booking(String id, Show show, User user, int seats, SeatType seatType) {
        this.id = id;
        this.show = show;
        this.user = user;
        this.seatType = seatType;
        this.noOfSeats = seats;
        this.status = BookingStatus.BOOKED;
    }

    public String getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public User getUser() {
        return user;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void cancelBooking(Show show) {
        show.getTheatre().cancelTickets(show.getId(), noOfSeats, seatType);
        this.status = BookingStatus.CANCELLED;
        System.out.println("Booking cancelled successfully");
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
        System.out.println("Booking status updated successfully");
    }
}
