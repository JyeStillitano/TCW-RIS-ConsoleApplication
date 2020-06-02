package src;
public class MenuItem {
    private String name;
    private String id;
    private String description;
    private double price;
    private String dietaryRequirement1;
    private String dietaryRequirement2;

    // Create menu item, passing in all relevant fields from database (performed in Menu class).
    public MenuItem(String aName,String aID, String aDescription, double aPrice, String aDiet1, String aDiet2) {
        name = aName;
        id = aID;
        description = aDescription;
        price = aPrice;
        dietaryRequirement1 = aDiet1;
        dietaryRequirement2 = aDiet2;
    }

    // GETTERS
    
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public String getDietaryRequirement1() {
        return dietaryRequirement1;
    }
    public String getDietaryRequirement2() {
        return dietaryRequirement2;
    }
}
