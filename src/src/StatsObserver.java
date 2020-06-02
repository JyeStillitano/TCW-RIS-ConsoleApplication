package src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsObserver implements OrderObserver {
    
    public StatsObserver() {
        
    }
    
    // Method implemented from OrderObserver to get notified
    // once an order is "FINISHED" to update the database stats
    @Override
        public void update(Order order) {
        if (order.orderState == Order.OrderState.FINISHED) {
            logStats(order);
        }
    }
        
    // Foreach MenuItem in the Order, update statistics for that MenuItem
    public void logStats(Order order) {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            for (MenuItem m : order.getOrderedItems()) {
                String query = "UPDATE MENUITEMS SET ORDERCOUNT=ORDERCOUNT+1 WHERE ID='" + m.getId() + "'";
                stmt.execute(query);
            }
            
            if (stmt != null) {stmt.close();}
            if (conn != null) {conn.close();}
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Creates a connection to the Java DB and returns the connection.
    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");

        // get the connection
        return DriverManager.getConnection("jdbc:derby://localhost/sun-appserv-samples;create=true", "APP", "APP");
    }
}
