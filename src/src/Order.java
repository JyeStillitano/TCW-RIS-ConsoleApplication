package src;
import java.util.ArrayList;
import java.util.List;

public class Order {
    // The OrderState enum is the basis for the Observer Pattern implemented.
    enum OrderState {
        PLACING,    // Order object has been created.
        PLACED,     // Order has been placed.
        LATE,       // Order taking too long.
        PREPARED,   // Kitchen has prepared the meal.
        RECEIVED,   // Order placed on table.
        FINISHED,   // Ready to make payment.
        PAID        // Transaction paid for.
    }

    private Transaction transaction;
    private List<MenuItem> orderedItems;
    public OrderState orderState;
    // Time of order being placed. Need to calculate LATE arrival.
    private List<OrderObserver> observers;
    boolean takeAway;

    // Initialise order with observers and whether it is take-away or eat-in.
    public Order(List<OrderObserver> aObservers, boolean takeaway) {
        //transaction = new Transaction(orderedItems);
        observers = aObservers;
        orderedItems = new ArrayList<MenuItem>();
        orderState = OrderState.PLACING;
        takeAway = takeaway;
    }

    public int getSize() {
        return orderedItems.size();
    }
    public boolean isEmpty() {
        return orderedItems.isEmpty();
    }
    public OrderState getState() {
        return orderState;
    }

    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }
    
    // Once an order has been placed reset the ArrayList 
    // to be used again and reset the OrderState
    public void resetOrder() {
        orderState = OrderState.PLACING;
        getOrderedItems().clear();
    }
    
    // Add MenuItem to order.
    public void addToOrder(MenuItem item) {
        try {
            orderedItems.add(item);
            System.out.println(item.getName() + " added to order!");
        } catch (Exception e) {
            System.out.println("Failed to find item entered, please review your input and try again.");
        }
    }
    // Remove MenuItem from order.
    public void removeFromOrder(int aIndex) {
        MenuItem item = orderedItems.get(aIndex);
        orderedItems.remove(item);
        System.out.println(item.getName() + " removed from order!");
        
    }

    // Various methods for altering the OrderState of 
    // the order and notifying all observers.
    
    public float payBill() {
        orderState = OrderState.PAID;
        notifyObservers();
        return transaction.getBillTotal();
    }

    public void orderPlaced() {
        orderState = OrderState.PLACED;
        transaction = new Transaction(orderedItems);
        // Notify observers.
        notifyObservers();
    }

    public void orderReceived() {
        orderState = OrderState.RECEIVED;
        // Notify observers.
        notifyObservers();
    }

    public void orderFinished() {
        orderState = OrderState.FINISHED;
        // Notify observers.
        notifyObservers();
    }

    public void orderLate() {
        orderState = OrderState.LATE;
        // Notify observers.
        notifyObservers();
    }
    public void orderPrepared() {
        orderState = OrderState.PREPARED;
        // Notify observers.
        notifyObservers();
    }

    // Attach new observer. Add to list
    public void attachObserver(OrderObserver observer){
        observers.add(observer);
    }

    // Detach an observer. Remove from list
    public void detachObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers. Loop through list
    public void notifyObservers() {
        for (OrderObserver o : observers) {
            o.update(this);
        }
    }
}
