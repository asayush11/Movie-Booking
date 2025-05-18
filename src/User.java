package src;

public class User {
    private final String name;
    private final String id;
    private final String email;

    public User(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Booking bookTicket(Show show, int noOfSeats, SeatType seatType, Theatre theatre) {
        return MovieBookingSystem.getInstance().addBooking(theatre, show, this, noOfSeats, seatType);
    }

    public void cancelTicket(Booking booking) {
        MovieBookingSystem.getInstance().cancelBookingByUser(booking.getId(), this.getId());
    }

}
