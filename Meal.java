package foodquick;

/**
 * Meal class represents a meal in the Food Quick application.
 * It contains details about the meal such as name, price, and quantity.
 */
public class Meal {
  private String name;
  private double price;
  private int quantity;

  /**
   * Constructs a Meal object with the specified details.
   *
   * @param name     The name of the meal.
   * @param price    The price of the meal.
   * @param quantity The quantity of the meal.
   */
  public Meal(String name, double price, int quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Gets the name of the meal.
   *
   * @return The name of the meal.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the price of the meal.
   *
   * @return The price of the meal.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Gets the quantity of the meal.
   *
   * @return The quantity of the meal.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Returns a string representation of the Meal object.
   *
   * @return A string representation of the Meal object.
   */
  @Override
  public String toString() {
    return quantity + " x " + name + " (R" + String.format("%.2f", price) + ")";
  }
}


