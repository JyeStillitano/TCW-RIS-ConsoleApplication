package src;
import java.util.Scanner;

public class TableTerminal extends OrderingInterface {

    public TableTerminal() {
        order = new Order(observers, false);
    }

    // Basic input/output control for Table Terminals
    // Table Terminals are used to create orders, 
    // place orders when selection is complete and
    // notify the cashier when ready to pay.
    public void terminalControl(Scanner input) {
        boolean tableTerminal = true;
        while(tableTerminal) {
            switch(order.getState()) {
                // When "PLACING", allows the order to be created and put into a "PLACED" state.
                case PLACING:
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
                            // Prints order with all MenuItems
                            viewOrderControl();
                            break;
                        case 4:
                            // Place order, puts into a "PLACED" state to
                            // notify the kitchen staff to begin preparing it.
                            placeOrder();
                            break;
                        case 5:
                            // Exit terminal
                            tableTerminal = false;
                    }
                    break;
                // When order is "PLACED", wait for order to be brought out.
                // Allows terminal to be exited for testing purposes.
                case PLACED:
                    System.out.println("Your order is being prepared!\nPress enter once your meal has been brought out to you and you are ready to pay! \n[EXIT SYSTEM BY INPUTTING 99]");
                    int exit = input.nextInt();
                    if (exit == 99) { tableTerminal = false; }
                    break;
                // When an order is "RECEIVED" the only option is to finish the
                // meal and pay when ready.
                case RECEIVED:
                    System.out.println("Would you like to\n\t1. Pay your Bill\n\t2. Exit System");
                    int pay = input.nextInt();
                    if (pay == 1) { payOrder(); }
                    else if (pay == 2) { tableTerminal = false; }
                    break;
                // Once the order has been paid the order object is reset
                // to prepare for the next customer.
                // Exits the terminal after resetting.
                case PAID:
                    resetOrder();
                    tableTerminal = false;
            }
        }
    }

}
