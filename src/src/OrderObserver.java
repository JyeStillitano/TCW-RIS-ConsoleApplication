package src;

// Extremely basic interface to ensure 
//      - Cashier Terminal
//      - Kitchen Terminal
//      - Stats Observer
// implement the update method to be notified
// when a change in OrderState occurs.
public interface OrderObserver {
    public void update(Order order);
}
