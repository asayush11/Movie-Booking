package src;

import java.time.LocalDateTime;

public class Demo {
    public static void main(String[] args) {
        MovieBookingSystem movieBookingSystem = MovieBookingSystem.getInstance();

        // Add theatres
        Theatre theatre1 = movieBookingSystem.addTheatre("PVR");
        Theatre theatre2 = movieBookingSystem.addTheatre("INOX");

        // Add movies
        Movie movie1 = new Movie( "The Shawshank Redemption", "Drama", "English");
        Movie movie2 = new Movie( "The Godfather", "Crime", "English");
        Movie movie3 = new Movie( "The Dark Knight", "Action", "English");
        Movie movie4 = new Movie( "Forrest Gump", "Drama", "English");

        // Add shows
        Show show1 = theatre1.addShow(movie1, LocalDateTime.now().plusDays(5));
        Show show2 = theatre1.addShow(movie2, LocalDateTime.now().plusDays(2));
        Show show3 = theatre2.addShow(movie3, LocalDateTime.now().plusDays(10));
        Show show4 = theatre2.addShow(movie4, LocalDateTime.now().plusDays(15));

        // Add users
        User user1 = movieBookingSystem.addUser("Alice", "alice.gmail");
        User user2 = movieBookingSystem.addUser("Bob", "bob.gmail");
        User user3 = movieBookingSystem.addUser("Charlie", "charlie.gmail");
        User user4 = movieBookingSystem.addUser("David", "david.gmail");

        // Book tickets
        Booking booking1 = user1.bookTicket(show1, 2, SeatType.NORMAL, theatre1);
        Booking booking2 = user2.bookTicket(show2, 3, SeatType.VIP, theatre1);
        Booking booking3 = user3.bookTicket(show3, 4, SeatType.PREMIUM, theatre2);
        Booking booking4 = user4.bookTicket(show4, 5, SeatType.NORMAL, theatre2);
        Booking booking5 = user1.bookTicket(show1, 4, SeatType.NORMAL, theatre1);
        Booking booking6 = user3.bookTicket(show3, 3, SeatType.VIP, theatre2);
        Booking booking7 = user3.bookTicket(show3, 3, SeatType.VIP, theatre1);

        // Cancel booking
        user1.cancelTicket(booking6);
        user2.cancelTicket(booking2);

        // Remove shows
        theatre1.removeShow(show4);
        theatre2.removeShow(show4);

        // display
        movieBookingSystem.displayUsers();
        movieBookingSystem.displayTheatres();
        movieBookingSystem.displayBookings();

    }
}
