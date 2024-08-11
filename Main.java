/** A program for ordering food which takes a customer's and restaurant's
 * details and assigns to a driver from a txt file.
 */ 
package foodquick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Food Quick application.
 * This program captures customer and restaurant details, processes food orders,
 * and assigns a driver for delivery based on their location.
 */
public class Main {
  /**
   * Main method to run the Food Quick application.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // File paths
    String driversFilePath = "txt_files" + File.separator + "drivers-info.txt";
    String invoiceFilePath = "txt_files" + File.separator + "invoice.txt";

    try {
      Customer customer = captureCustomerDetails(scanner);
      Restaurant restaurant = captureRestaurantDetails(scanner);
      List<Meal> meals = captureOrderDetails(scanner);
      String specialInstructions = captureSpecialInstructions(scanner);
      double totalAmount = calculateTotalAmount(meals);

      // Read drivers from the drivers-info.txt file
      List<String[]> drivers = readDrivers(driversFilePath);

      // Check if there is a driver in both the customer's city and the restaurant's city
      boolean driverInCustomerCity = drivers.stream()
        .anyMatch(driver -> driver[1].equalsIgnoreCase(customer.getCity()));
      boolean driverInRestaurantCity = drivers.stream()
        .anyMatch(driver -> driver[1].equalsIgnoreCase(restaurant.getLocation()));

      if (!driverInCustomerCity || !driverInRestaurantCity
          || !customer.getCity().equalsIgnoreCase(restaurant.getLocation())) {
        System.out.println(
          "Sorry! Our drivers are too far away from you to be able to deliver " +
          "to your location."
        );
        writeInvoiceTooFar(invoiceFilePath);
        return;
      }

      // Find the driver in the correct area with the smallest load
      String[] assignedDriver = findDriverWithSmallestLoad(
        drivers, restaurant.getLocation()
      );

      // Write the invoice to invoice.txt
      writeInvoice(
        invoiceFilePath, customer, restaurant, meals, 
        specialInstructions, totalAmount, assignedDriver
      );
      System.out.println("\nInvoice written to " + invoiceFilePath);

    } catch (IOException e) {
      System.out.println("Error writing invoice: " + e.getMessage());
    } catch (InputMismatchException e) {
      System.out.println("Invalid input provided. Please try again.");
    } finally {
      scanner.close();
    }
  }

  /**
   * Captures customer details from the input.
   *
   * @param scanner Scanner object for input.
   * @return Customer object with captured details.
   */
  private static Customer captureCustomerDetails(Scanner scanner) {
    try {
      System.out.println("Enter Customer Details:");
      System.out.print("Order Number: ");
      int orderNumber = scanner.nextInt();
      scanner.nextLine(); // Consume the newline

      System.out.print("Name: ");
      String customerName = scanner.nextLine();

      System.out.print("Contact Number: ");
      String customerContactNumber = scanner.nextLine();

      System.out.print("Address: ");
      String customerAddress = scanner.nextLine();

      System.out.print("City: ");
      String customerCity = scanner.nextLine();

      System.out.print("Email: ");
      String customerEmail = scanner.nextLine();

      Customer customer = new Customer(
        orderNumber, customerName, customerContactNumber, 
        customerAddress, customerCity, customerEmail
      );
      System.out.println("\nCustomer Created:\n" + customer);

      return customer;
    } catch (InputMismatchException e) {
      System.out.println("Invalid input. Please enter correct details.");
      scanner.next(); // Consume invalid input
      return captureCustomerDetails(scanner); // Retry
    }
  }

  /**
   * Captures restaurant details from the input.
   *
   * @param scanner Scanner object for input.
   * @return Restaurant object with captured details.
   */
  private static Restaurant captureRestaurantDetails(Scanner scanner) {
    System.out.println("\nEnter Restaurant Details:");
    System.out.print("Name: ");
    String restaurantName = scanner.nextLine();

    System.out.print("Location: ");
    String restaurantLocation = scanner.nextLine();

    System.out.print("Contact Number: ");
    String restaurantContactNumber = scanner.nextLine();

    Restaurant restaurant = new Restaurant(
      restaurantName, restaurantLocation, restaurantContactNumber
    );
    System.out.println("\nRestaurant Created:\n" + restaurant);

    return restaurant;
  }

  /**
   * Captures order details from the input.
   *
   * @param scanner Scanner object for input.
   * @return List of Meal objects with captured details.
   */
  private static List<Meal> captureOrderDetails(Scanner scanner) {
    System.out.println("\nEnter Order Details:");
    List<Meal> meals = new ArrayList<>();

    boolean addingMeals = true;
    while (addingMeals) {
      System.out.print("Meal Name: ");
      String mealName = scanner.nextLine();

      double mealPrice = 0.0;
      boolean validMealPrice = false;
      while (!validMealPrice) {
        try {
          System.out.print("Meal Price: ");
          mealPrice = scanner.nextDouble();
          validMealPrice = true;
        } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a valid price.");
          scanner.next(); // Consume invalid input
        }
      }

      int mealQuantity = 0;
      boolean validMealQuantity = false;
      while (!validMealQuantity) {
        try {
          System.out.print("Meal Quantity: ");
          mealQuantity = scanner.nextInt();
          validMealQuantity = true;
        } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a valid quantity.");
          scanner.next(); // Consume invalid input
        }
      }
      scanner.nextLine(); // Consume the newline

      meals.add(new Meal(mealName, mealPrice, mealQuantity));

      System.out.print("Do you want to add another meal? (yes/no): ");
      String addMore = scanner.nextLine();
      if (addMore.equalsIgnoreCase("no")) {
        addingMeals = false;
      }
    }

    return meals;
  }

  /**
   * Captures special instructions from the input.
   *
   * @param scanner Scanner object for input.
   * @return Special instructions as a string.
   */
  private static String captureSpecialInstructions(Scanner scanner) {
    System.out.print("Special Instructions: ");
    return scanner.nextLine();
  }

  /**
   * Calculates the total amount for the order.
   *
   * @param meals List of Meal objects.
   * @return Total amount for the order.
   */
  private static double calculateTotalAmount(List<Meal> meals) {
    double totalAmount = 0.0;
    for (Meal meal : meals) {
      totalAmount += meal.getPrice() * meal.getQuantity();
    }
    return totalAmount;
  }

  /**
   * Reads drivers' details from a file.
   *
   * @param filename Path to the file containing drivers' details.
   * @return List of drivers' details as arrays of strings.
   */
  public static List<String[]> readDrivers(String filename) {
    List<String[]> drivers = new ArrayList<>();
    try (Scanner fileScanner = new Scanner(new File(filename))) {
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");
        for (int i = 0; i < parts.length; i++) {
          parts[i] = parts[i].trim(); // Trim whitespace from each part
        }
        drivers.add(parts);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
    }
    return drivers;
  }

  /**
   * Finds the driver with the smallest load in the specified location.
   *
   * @param drivers List of drivers' details.
   * @param location Location to match.
   * @return Details of the driver with the smallest load.
   */
  public static String[] findDriverWithSmallestLoad(
      List<String[]> drivers, String location) {
    String[] smallestLoadDriver = null;
    for (String[] driver : drivers) {
      if (driver[1].equalsIgnoreCase(location)) {
        int currentLoad = Integer.parseInt(driver[2]);
        if (smallestLoadDriver == null 
            || currentLoad < Integer.parseInt(smallestLoadDriver[2])) {
          smallestLoadDriver = driver;
        }
      }
    }
    return smallestLoadDriver;
  }

  /**
   * Writes the invoice to a file.
   *
   * @param filename Path to the invoice file.
   * @param customer Customer details.
   * @param restaurant Restaurant details.
   * @param meals List of meals ordered.
   * @param specialInstructions Special instructions for the order.
   * @param totalAmount Total amount for the order.
   * @param driver Assigned driver's details.
   * @throws IOException If an I/O error occurs.
   */
  public static void writeInvoice(
      String filename, Customer customer, Restaurant restaurant, 
      List<Meal> meals, String specialInstructions, double totalAmount, 
      String[] driver) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }

    try (FileWriter writer = new FileWriter(file)) {
      writer.write("Order number: " + customer.getOrderNumber() + "\n");
      writer.write("Customer: " + customer.getName() + "\n");
      writer.write("Email: " + customer.getEmail() + "\n");
      writer.write("Phone number: " + customer.getContactNumber() + "\n");
      writer.write("Location: " + customer.getCity() + "\n");
      writer.write("\n");
      writer.write(
        "You have ordered the following from " + restaurant.getName() + 
        " in " + restaurant.getLocation() + ":\n"
      );
      writer.write("\n");

      for (Meal meal : meals) {
        writer.write(meal.toString() + "\n");
        writer.write("\n");
      }

      writer.write("Special instructions: " + specialInstructions + "\n");
      writer.write("\n");
      writer.write("Total: R" + String.format("%.2f", totalAmount) + "\n");
      writer.write("\n");

      if (driver != null) {
        writer.write(
          driver[0] + " is nearest to the restaurant and so he will be " +
          "delivering your order to you at:\n"
        );
        writer.write(customer.getAddress() + "\n");
        writer.write("\n");
      } else {
        writer.write(
          "Sorry! Our drivers are too far away from you to be able to deliver " +
          "to your location.\n"
        );
      }

      writer.write(
        "If you need to contact the restaurant, their number is " + 
        restaurant.getContactNumber() + ".\n"
      );
    }
  }

  /**
   * Writes a message to the invoice file indicating that drivers are too far away.
   *
   * @param filename Path to the invoice file.
   * @throws IOException If an I/O error occurs.
   */
  public static void writeInvoiceTooFar(String filename) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }

    try (FileWriter writer = new FileWriter(file)) {
      writer.write(
        "Sorry! Our drivers are too far away from you to be able to deliver " +
        "to your location.\n"
      );
    }
  }
}




















