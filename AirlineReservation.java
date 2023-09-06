package miniproject;

import java.util.*;

class Flight {
    String name;
    int totalSeats;
    boolean[] seatAvailability;

    public Flight(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.seatAvailability = new boolean[totalSeats];
        Arrays.fill(seatAvailability, true);
    }

    public boolean isSeatAvailable(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= totalSeats && seatAvailability[seatNumber - 1];
    }

    public void bookSeat(int seatNumber) {
        seatAvailability[seatNumber - 1] = false;
    }

    public void cancelSeat(int seatNumber) {
        seatAvailability[seatNumber - 1] = true;
    }

    public int getTotalAvailableSeats() {
        int count = 0;
        for (boolean available : seatAvailability) {
            if (available) {
                count++;
            }
        }
        return count;
    }
}

class Reservation {
    Flight flight;
    int seatNumber;

    public Reservation(Flight flight, int seatNumber) {
        this.flight = flight;
        this.seatNumber = seatNumber;
    }


    public String toString() {
        return "Reservation - Flight: " + flight.name + " | Seat Number: " + seatNumber;
    }
}

public class AirlineReservation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Flight[] flights = {
                new Flight("Flight A", 50),
                new Flight("Flight B", 40)
        };

        while (true) {
            System.out.println("Welcome to Airline Reservation System");
            System.out.println("1. Book a Seat");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Display Available Seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookSeat(scanner, flights);
                    break;
                case 2:
                    cancelReservation(scanner, flights);
                    break;
                case 3:
                    displayAvailableSeats(flights);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void bookSeat(Scanner scanner, Flight[] flights) {
        System.out.println("Select a flight:");
        for (int i = 0; i < flights.length; i++) {
            System.out.println((i + 1) + ". " + flights[i].name);
        }
        int flightIndex = scanner.nextInt() - 1;
        if (flightIndex < 0 || flightIndex >= flights.length) {
            System.out.println("Invalid flight selection.");
            return;
        }

        Flight selectedFlight = flights[flightIndex];
        System.out.println("Available seats on " + selectedFlight.name + ": " + selectedFlight.getTotalAvailableSeats());

        System.out.print("Enter seat number: ");
        int seatNumber = scanner.nextInt();
        if (!selectedFlight.isSeatAvailable(seatNumber)) {
            System.out.println("Seat " + seatNumber + " is not available.");
            return;
        }

        selectedFlight.bookSeat(seatNumber);
        Reservation reservation = new Reservation(selectedFlight, seatNumber);
        System.out.println("Reservation successful: " + reservation);
    }

    public static void cancelReservation(Scanner scanner, Flight[] flights) {
        System.out.println("Select a flight for cancellation:");
        for (int i = 0; i < flights.length; i++) {
            System.out.println((i + 1) + ". " + flights[i].name);
        }
        int flightIndex = scanner.nextInt() - 1;
        if (flightIndex < 0 || flightIndex >= flights.length) {
            System.out.println("Invalid flight selection.");
            return;
        }

        Flight selectedFlight = flights[flightIndex];
        System.out.println("Enter the seat number to cancel: ");
        int seatNumber = scanner.nextInt();

        if (selectedFlight.isSeatAvailable(seatNumber)) {
            System.out.println("Seat " + seatNumber + " is not reserved.");
        } else {
            selectedFlight.cancelSeat(seatNumber);
            System.out.println("Reservation for seat " + seatNumber + " canceled successfully.");
        }
    }

    public static void displayAvailableSeats(Flight[] flights) {
        System.out.println("Available Seats:");
        for (int i = 0; i < flights.length; i++) {
            System.out.println("Flight: " + flights[i].name);
            for (int j = 1; j <= flights[i].totalSeats; j++) {
                if (flights[i].isSeatAvailable(j)) {
                    System.out.print(j + " ");
                } else {
                    System.out.print("X ");
                }
                if (j % 10 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
    }
}

