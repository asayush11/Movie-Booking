package src;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Theatre {
    private final String name;
    private final String id;
    private final List<Show> shows;

    public Theatre(String name, String id) {
        this.name = name;
        this.id = id;
        this.shows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Show addShow(Movie movie, String time) {
        String showId = "SH" + id + UUID.randomUUID().toString().substring(0,6);
        List<Seat> seats = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            char seatType = i < 5 ? 'N' : i < 8 ? 'V' : 'P';
            switch (seatType) {
                case 'N':
                    seats.add(new Seat(SeatType.NORMAL, i, 50.0));
                    break;
                case 'V':
                    seats.add(new Seat(SeatType.VIP, i, 100.0));
                    break;
                default: seats.add(new Seat(SeatType.PREMIUM, i, 150.0));
            }
        }
        Show show = new Show(showId, movie, time, seats, this);
        shows.add(show);
        System.out.println("Show added successfully");
        return show;
    }

    public void removeShow(Show show) {
        if(shows.removeIf(s -> s.getId().equals(show.getId()))) {
            MovieBookingSystem.getInstance().cancelBookingByTheatre(show.getId());
            System.out.println("Show removed successfully");
        } else {
            System.out.println("Show not found");
        }
    }

    public void cancelTickets(String showId, int noOfSeats, SeatType seatType) {
        Show show = shows.stream()
                        .filter(s -> s.getId().equals(showId))
                        .findFirst()
                        .orElse(null);
        if(show == null) {
            System.out.println("Show not found");
            return;
        }
        show.cancelTicket(noOfSeats, seatType);
    }

    public boolean bookTickets(String showId, int noOfSeats, SeatType seatType) {
        Show show = shows.stream()
                        .filter(s -> s.getId().equals(showId))
                        .findFirst()
                        .orElse(null);
        if(show == null) {
            System.out.println("Show not found");
            return false;
        }
        return show.bookTicket(noOfSeats, seatType);
    }

    public void displayShows() {
        shows.forEach(show -> {
            System.out.println("Show ID: " + show.getId());
            System.out.print("  Movie: " + show.getMovie().getName());
            System.out.print("  Time: " + show.getTime());
        });
    }

}
