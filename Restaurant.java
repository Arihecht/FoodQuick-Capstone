package foodquick;

/**
 * Restaurant class represents a restaurant in the Food Quick application.
 * It contains details about the restaurant such as name, location,
 * and contact number.
 */
public class Restaurant {
  private String name;
  private String location;
  private String contactNumber;

  /**
   * Constructs a Restaurant object with the specified details.
   *
   * @param name          The name of the restaurant.
   * @param location      The location of the restaurant.
   * @param contactNumber The contact number of the restaurant.
   */
  public Restaurant(String name, String location, String contactNumber) {
    this.name = name;
    this.location = location;
    this.contactNumber = contactNumber;
  }

  /**
   * Gets the name of the restaurant.
   *
   * @return The name of the restaurant.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of the restaurant.
   *
   * @return The location of the restaurant.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Gets the contact number of the restaurant.
   *
   * @return The contact number of the restaurant.
   */
  public String getContactNumber() {
    return contactNumber;
  }

  /**
   * Returns a string representation of the Restaurant object.
   *
   * @return A string representation of the Restaurant object.
   */
  @Override
  public String toString() {
    return "Restaurant{" +
           "name='" + name + '\'' +
           ", location='" + location + '\'' +
           ", contactNumber='" + contactNumber + '\'' +
           '}';
  }
}



