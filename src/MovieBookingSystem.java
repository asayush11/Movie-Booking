package src;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieBookingSystem {
    private static MovieBookingSystem bookingSystem;
    private final List<Theatre> theatres;
    private final List<User> users;
    private final List<Booking> bookings;

    private MovieBookingSystem() {
        this.theatres = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public synchronized static MovieBookingSystem getInstance() {
        if(bookingSystem == null) {
            bookingSystem = new MovieBookingSystem();
        }
        return bookingSystem;
    }

    public Theatre addTheatre(String name) {
        String id = "TH" + UUID.randomUUID().toString().substring(0,6);
        Theatre theatre = new Theatre(name, id);
        theatres.add(theatre);
        System.out.println("Theatre added successfully");
        return theatre;
    }

    public void removeTheatre(String theatreId) {
        theatres.removeIf(theatre -> theatre.getId().equals(theatreId));
        System.out.println("Theatre removed successfully");
    }

    public User addUser(String name, String email) {
        String id = "US" + UUID.randomUUID().toString().substring(0,6);
        User user = new User(name, id, email);
        users.add(user);
        System.out.println("User added successfully");
        return user;
    }

    public void removeUser(String userId) {
        users.removeIf(user -> user.getId().equals(userId));
        System.out.println("User removed successfully");
    }

    public Booking addBooking(Show show, User user, int seats, SeatType seatType) {
        boolean reserved = show.getTheatre().bookTickets(show.getId(), seats, seatType);
        if(!reserved) {
            System.out.println("Booking failed");
            return null;
        }
        String id = "BK" + UUID.randomUUID().toString().substring(0,6);
        Booking booking = new Booking(id, show, user, seats, seatType);
        bookings.add(booking);
        System.out.println("Booking created successfully");
        return booking;
    }

    public void cancelBookingByUser(String bookingId, String userId) {
        Booking booking = bookings.stream()
                                .filter(b -> b.getId().equals(bookingId) && b.getUser().getId().equals(userId))
                                .findFirst()
                                .orElse(null);
        if(booking == null) {
            System.out.println("Booking not found");
            return;
        }
        booking.cancelBooking(booking.getShow());
    }

    public void displayTheatres() {
        theatres.forEach(theatre -> {
            System.out.println(theatre.getId() + " " + theatre.getName());
            theatre.displayShows();
        });
    }

    public void displayUsers() {
        users.forEach(user -> {
            System.out.println(user.getId() + " " + user.getName());
        });
    }

    public void displayBookings() {
        bookings.forEach(booking -> {
            System.out.println(booking.getId() + " " + booking.getUser().getName() + " " + booking.getShow().getMovie().getName() + " " +booking.getStatus());
        });
    }

    public void cancelBookingByTheatre(String showId) {
        System.out.println("Bookings cancelled by theatre");
        bookings.stream()
                .filter(booking -> booking.getShow().getId().equals(showId))
                .forEach(booking -> booking.setStatus(BookingStatus.CANCELLED));
    }
}
