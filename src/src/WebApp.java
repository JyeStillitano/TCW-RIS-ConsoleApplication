package src;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class WebApp extends OrderingInterface implements Reserver {

    public WebApp() {
        order = new Order(observers, false);
    }

    // Basic input/output to test the Web App functions without the Web Application
    // Customers accessing the web app can place reservations or create orders
    // to be picked up.
    public void WebAppControl(Scanner input) {
        boolean webAppControl = true;
        while (webAppControl) {
            System.out.println("What would you like to do?\n\t1. Create a Reservation\n\t2. Create an Order\n\t3. Exit System");
            int choice = input.nextInt();
            switch(choice) {
                case 1:
                    createReservationControl(input);
                    break;
                case 2:
                    createOrderControl(input);
                    break;
                case 3:
                    webAppControl = false;
                    break;
            }
        }
    }
    
    // Allows MenuItems to be added or removed from the order.
    // Allows the order to be viewed to assess the choices.
    // Allows the order to be placed, sending it to the kitchen
    // terminal to begin preparation.
    public void createOrderControl(Scanner input) {
        order.resetOrder();
        boolean creatingOrder = true;
        while(creatingOrder) {
            System.out.println("Would you like to...\n\t1. Add an item to order.\n\t2. Remove an item from order\n\t3. View Order\n\t4. Finalise (Place) Order\n\t5. Exit Terminal");
            int choice = input.nextInt();
            switch(choice) {
                case 1:
                    // Display menu and allow to add item.
                    addToOrderControl(input);
                    break;
                case 2:
                    // List items in order and remove selection.
                    removeFromOrderControl(input);
                    break;
                case 3:
                    viewOrderControl();
                    break;
                case 4:
                    placeOrder();
                    System.out.println("The order has been placed. Please come pick up the order in half an hour.");
                    payOrder();
                    break;
                case 5:
                    creatingOrder = false;
                    break;
            }
        }
    }
    
    // Get all relevant reservation information.
    // Check reservation doesn't clash.
    @Override
    public void createReservationControl(Scanner input)
    {
        // Enter customer details.
        Reservation reservation = null;
        System.out.println("Name: ");
        input.nextLine();
        String custName = input.nextLine();
        System.out.println("Number: ");
        String custNum = input.nextLine();
        
        // Enter date and confirm the pattern meets "yyyy-MM-dd"
        System.out.println("Enter requested date... (YYYY-MM-DD)");
        Date date = null;
        boolean dateGiven = false;
        while(!dateGiven){
            String stringDate = input.next("\\d{4}\\-(0\\d|1[0|1|2])\\-(0\\d|1\\d|2\\d|3[0|1])");
            try {
                date = java.sql.Date.valueOf(stringDate);
                dateGiven = true;
            } catch (Exception e) {
                System.out.println("Invalid date format entered, please try again.");
            }
        }
        
        // Enter time and confirm the pattern meets "HH:MM:00"
        System.out.println("Enter requested time... (HH:MM:00) 24 Hour Format - Specify Seconds as 00");
        Time time = null;
        boolean timeGiven = false;
        while(!timeGiven) {
            String stringTime = input.next("(0\\d|1\\d|2[0|1|2|3]):([0|1|2|3|4|5]\\d):00");
            try {
                time = java.sql.Time.valueOf(stringTime);
                timeGiven = true;
            } catch (Exception e) {
                System.out.println("Invalid time format entered, please try again.");
            }
        }
        
        // Enter number of seats for the table.
        System.out.println("Seats needed: ");
        int seats = input.nextInt();
        
        // Get an available table for the given time slot.
        int tableNum = reservation.getAvailableTable(date, time);
        if (tableNum == 0) {System.out.println("There are no available tables for this date and time.");}
        else {
            reservation = new Reservation(custName, custNum, date, time, seats, tableNum);
        }
        // If everything went well place the reservation.
        if (reservation != null) { placeReservation(reservation);}
    }
    
    // Store reservation in database.
    // Returns the reservation information for the customer.
    @Override
    public void placeReservation(Reservation reservation)
    {
        if (reservation.placeReservation()) {
            System.out.println("Reservation placed."
                    + "\n\tName: " + reservation.getCustName()
                    + "\n\tContact number: " + reservation.getCustNum()
                    + "\n\tDate: " + reservation.getDate()
                    + "\n\tTime: " + reservation.getTime()
                    + "\n\tSeats: " + reservation.getSeats()
            );
        } else {System.out.println("Something went wrong please try again.");}
    }
}
