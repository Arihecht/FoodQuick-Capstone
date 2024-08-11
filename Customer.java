package foodquick;

/**
 * Customer class represents a customer in the Food Quick application.
 * It contains customer details such as order number, name, contact number,
 * address, city, and email.
 */
public class Customer {
  private int orderNumber;
  private String name;
  private String contactNumber;
  private String address;
  private String city;
  private String email;

  /**
   * Constructs a Customer object with the specified details.
   *
   * @param orderNumber    The order number of the customer.
   * @param name           The name of the customer.
   * @param contactNumber  The contact number of the customer.
   * @param address        The address of the customer.
   * @param city           The city of the customer.
   * @param email          The email address of the customer.
   */
  public Customer(int orderNumber, String name, String contactNumber,
                  String address, String city, String email) {
    this.orderNumber = orderNumber;
    this.name = name;
    this.contactNumber = contactNumber;
    this.address = address;
    this.city = city;
    this.email = email;
  }

  /**
   * Gets the order number of the customer.
   *
   * @return The order number.
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Gets the name of the customer.
   *
   * @return The name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the contact number of the customer.
   *
   * @return The contact number.
   */
  public String getContactNumber() {
    return contactNumber;
  }

  /**
   * Gets the address of the customer.
   *
   * @return The address.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Gets the city of the customer.
   *
   * @return The city.
   */
  public String getCity() {
    return city;
  }

  /**
   * Gets the email address of the customer.
   *
   * @return The email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns a string representation of the Customer object.
   *
   * @return A string representation of the Customer object.
   */
  @Override
  public String toString() {
    return "Customer{" +
           "orderNumber=" + orderNumber +
           ", name='" + name + '\'' +
           ", contactNumber='" + contactNumber + '\'' +
           ", address='" + address + '\'' +
           ", city='" + city + '\'' +
           ", email='" + email + '\'' +
           '}';
  }
}




