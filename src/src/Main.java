package src;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;
        boolean on = true;
        // Create all terminal instances and attach observers where necessary.
        TableTerminal customer = new TableTerminal();
        WebApp onlineCustomer = new WebApp();
        CashierTerminal fohStaff = new CashierTerminal();
        KitchenTerminal bohStaff = new KitchenTerminal();
        StatsObserver statistics = new StatsObserver();
        customer.addObserver(statistics);
        customer.addObserver(fohStaff);
        customer.addObserver(bohStaff);
        fohStaff.addObserver(statistics);
        fohStaff.addObserver(fohStaff);
        fohStaff.addObserver(bohStaff);
        onlineCustomer.addObserver(statistics);
        onlineCustomer.addObserver(fohStaff);
        onlineCustomer.addObserver(bohStaff);
        
        // Selection allows for testing of all command-line interfaces within one instance.
        while (on) {
            System.out.println("System Setup: Is this device a... \n\t1. Table Terminal"
                    + "\n\t2. Test WebApp Control\n\t3. Cashier Terminal"
                    + "\n\t4. Kitchen Terminal\n\t5. Power Off System");
            choice = input.nextInt();
            switch(choice) {
                // Pass control to Table Terminal
                case 1:
                    customer.terminalControl(input);
                    break;
                // Pass control to Web Application (CLI)
                case 2:
                    onlineCustomer.WebAppControl(input);
                    break;
                // Pass control to Cashier Terminal
                case 3:
                    fohStaff.terminalControl(input);
                    break;
                // Pass control to Kitchen Terminal
                case 4:
                    bohStaff.terminalControl(input);
                    break;
                // Turn off system.
                case 5:
                    on = false;
                    break;
                default:
                    System.out.println("Please make a valid selection between 1 and 4...");
            }
        }
        System.out.println("Shutting down...");
    }
}
