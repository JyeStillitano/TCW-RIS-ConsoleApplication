package src;
import java.util.Scanner;
import java.sql.Date;
import java.sql.Time;

public class CashierTerminal extends OrderingInterface implements OrderObserver, Reserver {

    // Setup the order as a takeaway order. Pass in observers (foh and boh staff).
    public CashierTerminal() {
        order = new Order(observers, true);
    }

    // Implements update from OrderObserver
    // Ensures class follows the Observer pattern
    public void update(Order order) {
        switch (order.orderState) {
            // Once the order has been prepare the foh staff must be notified to 
            // bring the order to the table for an eat-in order or to the
            // cashier counter for the customer awaiting a takeaway order.
            case PREPARED:
                if (order.takeAway) {
                    System.out.println("Cashier Terminal: A staff member brings the order to the counter for pick up.");
                } else {
                    System.out.println("Cashier Terminal: A staff member brings the order to the table and marks it as RECEIVED.");
                    order.orderReceived();
                }
                break;
            // If an order is marked as late the kitchen staff is notfied to bring out complimentary bread sticks.
            case LATE:
                System.out.println("Kitchen Staff: A staff member brings out some free bread sticks to apologise for the wait.");
                break;
            // When an order's marked as finished the customer is ready to pay their bill.
            // Gets the invoice/statement and bills the customer.
            case FINISHED:
                float cost = order.payBill();
                System.out.println("Cashier Terminal: your bill comes to $" + cost + ".\nThank you for your business, have a nice day!");
                break;
        }

    }

    // Cashier Terminal basic input/output, select what functionality to do.
    // Cashier Terminals can be used to create reservations or create orders.
    public void terminalControl(Scanner input) {
        boolean terminalControl = true;
        while (terminalControl) {
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
                    terminalControl = false;
                    break;
            }
        }
    }
    
    // Input/output for creating an order
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
                    // List items in order
                    viewOrderControl();
                    break;
                case 4:
                    // Place order to be prepared by the kitchen staff
                    placeOrder();
                    System.out.println("The order has been placed.");
                    payOrder();
                    break;
                case 5:
                    // Exit this screen
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
        System.out.println("Enter customers name...");
        input.nextLine();
        String custName = input.nextLine();
        System.out.println("Enter customers number...");
        String custNum = input.nextLine();
        
        // Enter date and confirm the pattern meets "yyyy-MM-dd"
        System.out.println("Enter requested date... (YYYY-MM-DD)");
        Date date = null;
        boolean dateGiven = false;
        while(!dateGiven){
            try {
                String stringDate = input.next("\\d{4}\\-(0\\d|1[0|1|2])\\-(0\\d|1\\d|2\\d|3[0|1])");
                date = java.sql.Date.valueOf(stringDate);
                dateGiven = true;
            } catch (Exception e) {
                System.out.println("Invalid date format entered, please try again.");
                input.nextLine();
            }
        }
        
        // Enter time and confirm the pattern meets "HH:MM:00"
        System.out.println("Enter requested time... (HH:MM:00) 24 Hour Format - Specify Seconds as 00");
        Time time = null;
        boolean timeGiven = false;
        while(!timeGiven) {
            try {
                String stringTime = input.next("(0\\d|1\\d|2[0|1|2|3]):([0|1|2|3|4|5]\\d):00");
                time = java.sql.Time.valueOf(stringTime);
                timeGiven = true;
            } catch (Exception e) {
                System.out.println("Invalid time format entered, please try again.");
                input.nextLine();
            }
        }
        
        // Enter number of seats for the table.
        System.out.println("Enter number of seats needed...");
        int seats = input.nextInt();
        
        // Get an available table for the given time slot.
        int tableNum = reservation.getAvailableTable(date, time);
        if (tableNum == 0) {System.out.println("There are no available tables for this date and time.");}
        else {
            System.out.println("Available table: " + tableNum + ".");
            reservation = new Reservation(custName, custNum, date, time, seats, tableNum);
        }
        // If everything went well place the reservation.
        if (reservation != null) { placeReservation(reservation);}
    }
    
    // Store reservation in database. 
    // Print the reservation information for the customer.
    @Override
    public void placeReservation(Reservation reservation)
    {
        if (reservation.placeReservation()) {
            System.out.println("Reservation placed."
                    + "\n\tReserved under Name: " + reservation.getCustName()
                    + "\n\tContact number: " + reservation.getCustNum()
                    + "\n\tDate: " + reservation.getDate()
                    + "\n\tTime: " + reservation.getTime()
                    + "\n\tSeats: " + reservation.getSeats()
                    + "\n\tTable Number: " + reservation.getTableNum()
            );
        } else {System.out.println("Something went wrong please try again.");}
    }
}
