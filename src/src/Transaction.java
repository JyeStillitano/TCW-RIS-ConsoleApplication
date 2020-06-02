package src;
import java.util.List;

public class Transaction {
    enum transactionState {
        UNPAID,
        PAID
    }
    private float billTotal;
    public transactionState state;
    
    // Initialises the transaction, totalling the bill 
    // and sets state to "UNPAID"
    public Transaction(List<MenuItem> aList) {
        for (MenuItem m : aList) {
            billTotal += m.getPrice();
        }
        state = transactionState.UNPAID;
    }

    // When the bill is invoiced and paid, the transaction is marked as "PAID"
    public boolean payBill() {
        // Payment doesn't need to be implemented in this assignment.
        state = transactionState.PAID;
        return true;
    }

    // billTotal Getter
    public float getBillTotal() {
        return billTotal;
    }
}
