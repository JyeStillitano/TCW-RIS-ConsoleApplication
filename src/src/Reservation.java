package src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;

public class Reservation {
    // Customer Info
    private String custName;
    private String custNum;
    
    // Time and Date
    private Date date;
    private Time time;
    
    // # of Customers (Seats Needed)
    private int seats;
    // # of Table
    private int tableNum;

    public Reservation(String aCustName, String aCustNum, Date aDate, Time aTime, int aSeatCount, int aTableNum) {
        custName = aCustName;
        custNum = aCustNum;
        date = aDate;
        time = aTime;
        seats = aSeatCount;
        tableNum = aTableNum;
    }

    // Basic Getters
    
    public String getCustName() {
        return custName;
    }

    public String getCustNum() {
        return custNum;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getSeats() {
        return seats;
    }

    public int getTableNum() {
        return tableNum;
    }

    
    // Creates a connection to the Java DB and returns the connection.
    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");

        // get the connection
        return DriverManager.getConnection("jdbc:derby://localhost/sun-appserv-samples;create=true", "APP", "APP");
    }
    
    // Store reservation in database using a Connection and executing the query.
    public boolean placeReservation() {
        boolean result = false;
        Connection conn = null;
        Statement stmt = null;
        String query = "INSERT INTO RESERVATIONS VALUES ("
                    + "'" + custName + "',"
                    + "'" + custNum + "',"
                    + "'" + date + "',"
                    + "'" + time + "',"
                    + seats + "," 
                    + tableNum + ")";
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            stmt.execute(query);
            result = true;
            if (stmt != null) {stmt.close();}
            if (conn != null) {conn.close();}
            
        } catch (Exception e) {
            System.out.println(e);
            result = false;
        }
        return result;
    }
    

    // Check reservation doesn't clash
    // If a reservation is found for the same day and time
    // Loops through reservations to find any table not booked and returns it.
    public static int getAvailableTable(Date aDate, Time aTime) {
        int freeTableNum = 0;
        Connection conn = null;
        Statement stmt = null;
        String query = "SELECT * FROM RESERVATIONS WHERE DATE='" + aDate + "' AND TIME='" + aTime + "'";
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            ResultSet results = stmt.executeQuery(query);
            
            // 40 Tables, check all reservations on that date and time.
            for(int i=1; i <=40; i++) {
                boolean tableFree = true;
                while(results.next()) {
                    if(results.getInt("TABLENUM") == i) {
                        tableFree = false;
                        break;
                    }
                }
                if (tableFree) {
                    freeTableNum = i;
                    break;
                }
            }
            
            
            if (stmt != null) {stmt.close();}
            if (conn != null) {conn.close();}
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return freeTableNum;
    }
}
