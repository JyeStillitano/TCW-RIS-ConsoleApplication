package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderingInterface {
    private Menu dinnerMenu;
    private Menu lunchMenu;

    Order order;
    List<OrderObserver> observers;

    // Initialise menu's to call constructor and fill with MenuItems
    public OrderingInterface() {
        observers = new ArrayList<OrderObserver>();
        dinnerMenu = new Menu("DINNER");
        lunchMenu = new Menu("LUNCH");
    }

    // Menu Getters
    public Menu getDinnerMenu() {
        return dinnerMenu;
    }
    public Menu getLunchMenu() {
        return lunchMenu;
    }
    
    // Basic input/output for adding a MenuItem to the order.
    // Outputs the Menu to the client and allows a selection.
    public void addToOrderControl(Scanner input) {
        // Select lunch or dinner menu.
        System.out.println("Would you like to view the...\n\t1. Lunch Menu\n\t2. Dinner Menu\n\tPlease enter an option...");
        int choice = input.nextInt();
        input.nextLine();
        if (choice == 1) {
            printLunchMenu();
            System.out.println("Please enter the ID of the food/drink item you wish to add to the order.");
            String itemID = input.nextLine();
            addToOrder(itemID, choice);
        } else if (choice == 2) {
            printDinnerMenu();
            System.out.println("Please enter the ID of the food/drink item you wish to add to the order.");
            String itemID = input.nextLine();
            addToOrder(itemID, choice);
        }
    }
    // Basic input/output for removing a MenuItem from the order.
    // Outputs the current order with all MenuItems
    // Client chooses which item to remove.
    public void removeFromOrderControl(Scanner input) {
        if (!viewOrderControl()) {return;}
        System.out.println("Please enter the number of the item you wish to remove.");
        int choice = input.nextInt();
        removeFromOrder(choice);   
    }
    // Basic input/output to view order with all MenuItems
    // Used for prompt from client and within removeFromOrderControl()
    public boolean viewOrderControl() {
        if (order.isEmpty()) {
            System.out.println("Order is Currently Empty!");
            return false;
        }
        System.out.println("Your Order:");
        int i=1;
        for (MenuItem m : order.getOrderedItems()) {
            System.out.println("\t" + i + ". " + m.getName());
            i++;
        }
        return true;
    }
    
    // print diner menu for display
    public void printDinnerMenu() {
        for (MenuItem m : dinnerMenu.getMenu()) {
            System.out.println(m.getName());
            System.out.println("\tID: " + m.getId());
            System.out.println("\t" + m.getDescription());
            if (!m.getDietaryRequirement1().equals("NONE")) {System.out.println("\tWarning: " + m.getDietaryRequirement1());}
            if (!m.getDietaryRequirement2().equals("NONE")) {System.out.println("\tWarning: " + m.getDietaryRequirement2());}
            System.out.println("\t" + m.getPrice() + "\n");
        }
    }
    // print lunch menu for display
    public void printLunchMenu() {
        for (MenuItem m : lunchMenu.getMenu()) {
            System.out.println(m.getName());
            System.out.println("\tID: " + m.getId());
            System.out.println("\t" + m.getDescription());
            if (!m.getDietaryRequirement1().equals("NONE")) {System.out.println("\tWarning: " + m.getDietaryRequirement1());}
            if (!m.getDietaryRequirement2().equals("NONE")) {System.out.println("\tWarning: " + m.getDietaryRequirement2());}
            System.out.println("\t" + m.getPrice() + "\n");
        }
    }
    // Add an item to the order.
    public void addToOrder(String id, int menu) {
        // get item with id
        MenuItem item = null;
        if (menu == 1) {
            item = lunchMenu.getMenuItem(id);
        } else if (menu == 2) {
            item = dinnerMenu.getMenuItem(id);
        }
        order.addToOrder(item);
    }
    // Remove an item from the order.
    public void removeFromOrder(int choice) {
        if (choice <= order.getSize()) {
            order.removeFromOrder(choice-1);
        } else {
            System.out.println("Invalid number, please try again.");
        }
    }

    // Various methods for moving the order along different
    // states, calling the respective order methods.
    
    public void placeOrder() {
        order.orderPlaced();
    }
    public void payOrder() {
        order.orderFinished();
    }
    // Reset the order, call respective order method
    public void resetOrder() {
        order.resetOrder();
    }
    // Add observer to observers
    public void addObserver(OrderObserver o) {
        observers.add(o);
    }
}
