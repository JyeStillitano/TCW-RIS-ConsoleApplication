package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KitchenTerminal implements OrderObserver {
    private List<Order> preparingOrders;
    
    public KitchenTerminal() {
        preparingOrders = new ArrayList<Order>();
    }

    // Implements update from OrderObserver
    // If an order is in a "PLACED" state
    // it is added to the list of orders to prepare
    // the kitchen notifies the customer the meal is being prepared.
    @Override
    public void update(Order order) {
        if (order.orderState == Order.OrderState.PLACED) {
            preparingOrders.add(order);
            System.out.println("Kitchen Terminal: The chefs are cooking up a marvelous meal.");
        }
    }
    
    // Once the meals been made the kitchen notifies the FoH staff
    // The order is placed into a "PREPARED" state to notify the FoH Staff
    public void orderCompleted(Order order) {
        System.out.println("Kitchen Terminal: The order has been prepared.");
        order.orderPrepared();
    }
    
    // Basic input/output for the KitchenTerminal
    // Has the responsibility of making orders and 
    // marking them when completed to be picked up.
    public void terminalControl(Scanner input) {
        boolean kitchenTerminal = true;
        while (kitchenTerminal) {
            System.out.println("Orders to be prepared:");
            int i = 1;
            for (Order o : preparingOrders) { 
                System.out.println("Order #" + i);
                for (MenuItem m : o.getOrderedItems()) {
                    System.out.println("\t " + m.getName());
                }
                i++;
            }
            
            System.out.println("Once an order has been prepared, enter the order # to clear it from the list.\n[Enter 98 to mark an order as late.]\n[Enter 99 to exit system.]");
            int orderNumber = input.nextInt();
            if (orderNumber == 99) {kitchenTerminal = false;}
            else {
                if (orderNumber == 98) {
                    System.out.println("Which order is late?");
                    int lateOrderChoice = input.nextInt();
                    Order lateOrder = preparingOrders.get(lateOrderChoice-1);
                    lateOrder.orderLate();
                }

                if (orderNumber <= preparingOrders.size()) {
                    Order order = preparingOrders.get(orderNumber-1);
                    orderCompleted(order);
                    preparingOrders.remove(order);
                } else {
                    System.out.println("Order # entered was invalid. Try again.");
                }
            }
        }
    }
}
