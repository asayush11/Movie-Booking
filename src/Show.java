package src;

import java.util.List;

public class Show {
    private final String id;
    private final Theatre theatre;
    private final Movie movie;
    private final String time;
    private final List<Seat> seats;

    public Show(String id, Movie movie, String time, List<Seat> seats, Theatre theatre) {
        this.id = id;
        this.movie = movie;
        this.time = time;
        this.seats = seats;
        this.theatre = theatre;
    }

   public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getTime() {
        return time;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    private boolean getSeatAvailability(int numberOfSeats, SeatType seatType) {
        long availableSeats = seats.stream()
                                 .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE && seat.getType() == seatType)
                                 .count();
        return availableSeats >= numberOfSeats;
    }

    public synchronized boolean bookTicket(int noOfSeats, SeatType seatType) {
        if(getSeatAvailability(noOfSeats, seatType)) {
           seats.stream()
                   .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE && seat.getType() == seatType)
                   .limit(noOfSeats)
                   .forEach(seat -> seat.setStatus(SeatStatus.BOOKED));
            System.out.println("Seats are marked booked!");
            double amount = seats.stream()
                                .filter(seat -> seat.getType() == seatType)
                                .limit(noOfSeats)
                                .mapToDouble(Seat::getPrice)
                                .sum();
            System.out.println("Amount to be paid: " + amount);
            return true;
        } else {
            System.out.println("Seats not available!");
            return false;
        }
    }

    public void cancelTicket(int noOfSeats, SeatType seatType) {
        seats.stream()
                .filter(seat -> seat.getStatus() == SeatStatus.BOOKED && seat.getType() == seatType)
                .limit(noOfSeats)
                .forEach(seat -> seat.setStatus(SeatStatus.AVAILABLE));
        System.out.println("Seats are made available");
    }
}
