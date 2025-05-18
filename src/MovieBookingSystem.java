package src;

import java.util.*;

public class MovieBookingSystem {
    private static volatile MovieBookingSystem bookingSystem;
    private final Map<String,Theatre> theatres;
    private final List<User> users;
    private final List<Booking> bookings;

    private MovieBookingSystem() {
        this.theatres = new HashMap<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public synchronized static MovieBookingSystem getInstance() {
        if(bookingSystem == null) {
            synchronized (MovieBookingSystem.class){
                if(bookingSystem == null){
                    bookingSystem = new MovieBookingSystem();
                }
            }
        }
        return bookingSystem;
    }

    public Theatre addTheatre(String name) {
        String id = "TH" + UUID.randomUUID().toString().substring(0,6);
        Theatre theatre = new Theatre(name, id);
        theatres.put(id,theatre);
        System.out.println("Theatre added successfully");
        return theatre;
    }

    public void removeTheatre(String theatreId) {
        theatres.remove(theatreId);
        bookings.stream()
                .filter(booking -> booking.getTheatre().getId().equals(theatreId))
                .forEach(booking -> booking.setStatus(BookingStatus.CANCELLED));
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
        bookings.stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .forEach(booking -> booking.setStatus(BookingStatus.CANCELLED));
        System.out.println("User removed successfully");
    }

    public Booking addBooking(Theatre theatre, Show show, User user, int seats, SeatType seatType) {
        if(users.stream().noneMatch(u -> u.getId().equals(user.getId()))){
            System.out.println("User not found");
            return null;
        }
        if(theatres.containsKey(theatre.getId())){
            boolean reserved = theatres.get(theatre.getId()).bookTickets(show.getId(), seats, seatType);
            if(!reserved) {
                System.out.println("Booking failed");
                return null;
            }
            String id = "BK" + UUID.randomUUID().toString().substring(0,6);
            Booking booking = new Booking(id, show, user, seats, seatType, theatre);
            bookings.add(booking);
            System.out.println("Booking created successfully");
            return booking;
        } else {
            System.out.println("Theatre not found");
            return null;
        }
    }

    public void cancelBookingByUser(String bookingId, String userId) {
                  bookings.stream()
                                .filter(b -> b.getId().equals(bookingId) && b.getUser().getId().equals(userId))
                                .findFirst()
                                .ifPresentOrElse(b -> b.cancelBooking(b.getShow()),
                                                () -> System.out.println("Booking not found"));
    }

    public void displayTheatres() {
        theatres.values().forEach(theatre -> {
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
