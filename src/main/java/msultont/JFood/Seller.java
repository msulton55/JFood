package msultont.JFood;

/**
 * This class will generate data about the seller or food supplier to the JFood
 * restaurant.
 *
 * @author Muhammad Sulton Tauhid
 * @version May 12th, 2020
 */
public class Seller {

    // Instances Variables
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    /**
     * Constructor for objects of class Seller
     *
     * @param id  seller id
     * @param name  seller name
     * @param email  seller email
     * @param phoneNumber  seller phone number
     * @param location  seller's location
     */
    public Seller(int id, String name, String email, String phoneNumber, Location location) {        
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * This method will return the Id of the seller or supplier
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * This method will return name of the seller or supplier
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This method will return email of the seller or supplier
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method will return phone number of the seller or supplier
     * 
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method will return the spesific location of the seller or supplier. This
     * method's return data type is Location which means will call information
     * included in Location class
     * 
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * This method will reassign the id of the seller or supplier
     * 
     * @param id  seller id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method will reassign the name of the seller or supplier
     * 
     * @param name  seller name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method will reassign the email of the seller or supplier
     * 
     * @param email  seller email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method will reassign the phone number of the seller or supplier
     * 
     * @param phoneNumber  seller phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method will reassign the location of the seller or supplier. This
     * parameter's data type is using * Location class so it must be set to the new
     * object
     * 
     * @param location  seller location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * This method will print all the variables data to the terminal
     */
    @Override
    public String toString() {
        return "==========SELLER==========\n" + "\nID = " + id + "\nName = " + name + "\nPhone number = " + phoneNumber + "\nLocation = "
                + getLocation().getProvince() + ", " + getLocation().getCity();
    }

}
