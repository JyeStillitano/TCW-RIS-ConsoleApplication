package src;

import java.util.Scanner;

// Very basic Reserver interfaces to ensure that
//      - WebApp
//      - Cashier Terminal
// implement the required methods to create Reservations
public interface Reserver {
    // Get all relevant reservation information.
    public void createReservationControl(Scanner input);
    // Store reservation in database.
    public void placeReservation(Reservation reservation);         // USE RESERVATION METHODS AND CALL FROM TERMINAL
}
