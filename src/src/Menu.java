package src;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menu;
    private String menuType;

    // Initialise menu setting type (lunch or dinner)
    // Fill menu with MenuItems based on the menu type
    public Menu(String aMenuType) {
        menu = new ArrayList<MenuItem>();
        menuType = aMenuType;
        setMenu();
    }
    
    // Menu Getter
    public List<MenuItem> getMenu() {
        return menu;
    }

    // Create connection with Java Database and return to caller.
    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");

        // get the connection
        return DriverManager.getConnection("jdbc:derby://localhost/sun-appserv-samples;create=true", "APP", "APP");
    }
    
    // Connect to DB and fill menu with menu items.
    public void setMenu() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            ResultSet results = stmt.executeQuery("SELECT * FROM MENUITEMS WHERE MENU='" + menuType + "'");
            
            
            while(results.next()) {
                String name = results.getString("NAME");
                String id = results.getString("ID");
                String description = results.getString("DESCRIPTION");
                Double price = results.getDouble("PRICE");
                String diet1 = results.getString("DIET1");
                String diet2 = results.getString("DIET2");
                menu.add(new MenuItem(name, id, description, price, diet1, diet2));
            }
            if (stmt != null) {stmt.close();}
            if (conn != null) {conn.close();}
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // MenuItem getter from Menu, finds matching ID.
    public MenuItem getMenuItem(String id) {
        for (MenuItem m : menu) {
            if (id.equals(m.getId())) {return m;}
        }
        return null;
    }
}
