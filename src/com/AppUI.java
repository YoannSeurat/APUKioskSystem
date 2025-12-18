package com;

import com.user.*;
import com.product.*;
import com.order.*;
import com.system.*;
import com.patterns.observer.*;
import com.patterns.factory.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Interactive Terminal-based Kiosk System
 * Run main() to start the interactive menu-driven interface
 * Control everything from the terminal: users, products, orders, admin functions
 */
public class AppUI {
    
    private static CentralMenuSystem centralMenuSystem;
    private static AdminSystem adminSystem;
    private static APCardSystem cardSystem;
    private static Menu menu;
    private static Kiosk currentKiosk;
    private static User currentUser;
    private static Scanner scanner;
    private static List<Kiosk> kiosks;
    
    public static void main(String[] args) {
        startTerminalKiosk();
    }
    
    /**
     * Main entry point - Initialize systems and start terminal interface
     */
    public static void startTerminalKiosk() {
        // Initialize systems
        centralMenuSystem = CentralMenuSystem.getInstance();
        adminSystem = AdminSystem.getInstance();
        cardSystem = APCardSystem.getInstance();
        menu = centralMenuSystem.getMenu();
        scanner = new Scanner(System.in);
        kiosks = new ArrayList<>();
        
        // Create sample kiosks
        currentKiosk = new Kiosk(menu);
        kiosks.add(currentKiosk);
        centralMenuSystem.registerObserver(currentKiosk);
        
        // Initialize sample menu (optional - user can add products)
        initializeSampleMenu();
        
        // Start main terminal loop
        mainMenu();
    }
    
    /**
     * Initialize sample products (optional)
     */
    private static void initializeSampleMenu() {
        Drink cappuccino = new Drink("Cappuccino", 8.0, true);
        Drink latte = new Drink("Latte", 7.5, true);
        Drink tea = new Drink("Tea", 5.0, true);
        
        Snack chips = new Snack("Chips", 3.0, true);
        Snack cookie = new Snack("Cookie", 2.5, true);
        
        Sandwich tuna = new Sandwich("Tuna Sandwich", 12.0, true);
        Sandwich chicken = new Sandwich("Chicken Sandwich", 13.0, true);
        
        menu.addProduct(cappuccino);
        menu.addProduct(latte);
        menu.addProduct(tea);
        menu.addProduct(chips);
        menu.addProduct(cookie);
        menu.addProduct(tuna);
        menu.addProduct(chicken);
    }
    
    /**
     * Main menu - Choose between User, Admin, or Exit
     */
    private static void mainMenu() {
        while (true) {
            clearScreen();
            printHeader("APU CAMPUS KIOSK SYSTEM - MAIN MENU");
            System.out.println("\n1. User Interface (Order & Browse)");
            System.out.println("2. Admin Interface (Manage Products & Users)");
            System.out.println("3. Exit");
            System.out.print("\nSelect option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    userMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                case "3":
                    System.out.println("\n✓ Thank you for using APU Campus Kiosk System!");
                    scanner.close();
                    return;
                default:
                    printError("Invalid option. Please try again.");
            }
        }
    }
    
    /**
     * User Menu - Identify yourself and create orders
     */
    private static void userMenu() {
        while (true) {
            clearScreen();
            printHeader("USER INTERFACE");
            System.out.println("\n1. Identify Myself (Student/Lecturer)");
            System.out.println("2. View Menu");
            System.out.println("3. Create New Order");
            System.out.println("4. View My Balance");
            System.out.println("5. Back to Main Menu");
            System.out.print("\nSelect option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    identifyUser();
                    break;
                case "2":
                    viewMenu();
                    break;
                case "3":
                    if (currentUser == null) {
                        printError("Please identify yourself first!");
                    } else {
                        createOrder();
                    }
                    break;
                case "4":
                    if (currentUser == null) {
                        printError("Please identify yourself first!");
                    } else {
                        System.out.println("\n" + currentUser.prettyPrint());
                        pause();
                    }
                    break;
                case "5":
                    currentUser = null;
                    return;
                default:
                    printError("Invalid option.");
            }
        }
    }
    
    /**
     * Identify User - Register or Login as Student/Lecturer
     */
    private static void identifyUser() {
        clearScreen();
        printHeader("USER IDENTIFICATION");
        
        System.out.println("\n1. New Student");
        System.out.println("2. New Lecturer");
        System.out.println("3. Login Existing User");
        System.out.print("\nSelect option: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                registerStudent();
                break;
            case "2":
                registerLecturer();
                break;
            case "3":
                loginExistingUser();
                break;
            default:
                printError("Invalid option.");
        }
    }
    
    /**
     * Register new student
     */
    private static void registerStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter initial balance (MYR): ");
        
        try {
            double balance = Double.parseDouble(scanner.nextLine().trim());
            Student student = new Student(name, balance);
            cardSystem.addUser(student);
            currentUser = student;
            
            clearScreen();
            printSuccess("✓ Student registered successfully!");
            System.out.println("\n" + student.prettyPrint());
            System.out.println("User ID: " + student.getUserID());
            pause();
        } catch (NumberFormatException _) {
            printError("Invalid balance format.");
        }
    }
    
    /**
     * Register new lecturer
     */
    private static void registerLecturer() {
        System.out.print("\nEnter lecturer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter initial balance (MYR): ");
        
        try {
            double balance = Double.parseDouble(scanner.nextLine().trim());
            Lecturer lecturer = new Lecturer(name, balance);
            cardSystem.addUser(lecturer);
            currentUser = lecturer;
            
            clearScreen();
            printSuccess("✓ Lecturer registered successfully!");
            System.out.println("\n" + lecturer.prettyPrint());
            System.out.println("User ID: " + lecturer.getUserID());
            pause();
        } catch (NumberFormatException e) {
            printError("Invalid balance format.");
        }
    }
    
    /**
     * Login existing user
     */
    private static void loginExistingUser() {
        System.out.print("\nEnter your User ID: ");
        try {
            int userID = Integer.parseInt(scanner.nextLine().trim());
            User user = cardSystem.getUserById(userID);
            
            if (user != null) {
                currentUser = user;
                clearScreen();
                printSuccess("✓ Login successful!");
                System.out.println("\n" + user.prettyPrint());
                pause();
            } else {
                printError("User not found!");
            }
        } catch (NumberFormatException e) {
            printError("Invalid User ID format.");
        }
    }
    
    /**
     * View Menu - Display all available products
     */
    private static void viewMenu() {
        clearScreen();
        printHeader("MENU");
        
        List<Product> products = menu.getAvailableProducts();
        
        if (products.isEmpty()) {
            System.out.println("\nNo products available.");
        } else {
            int index = 1;
            for (Product product : products) {
                System.out.println(index + ". " + product.prettyPrint() + " [ID: " + product.getProductID() + "]");
                if (product instanceof Drink drink) {
                    System.out.println("   Size: " + drink.getSize() + " | Sugar: " + drink.getSugarLevel() +
                                     " | Milk: " + drink.getMilkLevel() + " | Ice: " + drink.getIceLevel());
                }
                System.out.println();
                index++;
            }
        }
        
        pause();
    }
    
    /**
     * Create Order - Browse menu, customize, and add products to order
     */
    private static void createOrder() {
        clearScreen();
        printHeader("CREATE NEW ORDER");
        
        currentKiosk.createOrder(currentUser);
        Order order = currentKiosk.getCurrentOrder();
        
        System.out.println("\n" + currentUser.prettyPrint());
        System.out.println("\nOrder ID: " + order.getOrderID());
        System.out.println("Status: " + order.getStatus());
        
        // Add products loop
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.println("\n--- Add Products to Order ---");
            displayAvailableProducts();
            
            System.out.println("\n0. Finish Order");
            System.out.print("Select product ID to add (or 0 to finish): ");
            
            try {
                int productID = Integer.parseInt(scanner.nextLine().trim());
                
                if (productID == 0) {
                    addingProducts = false;
                } else {
                    Product product = menu.getProduct(productID);
                    
                    if (product != null && product.isAvailable()) {
                        Product copy = ProductFactory.createProduct(product.getClass().getSimpleName(),
                                                                     product.getName(),
                                                                     product.getPrice(),
                                                                     product.isAvailable());
                        
                        // Customize product if applicable
                        switch (copy) {
                            case Drink drink -> customizeDrink(drink, order);
                            case Sandwich sandwich -> customizeSandwich(sandwich, order);
                            default -> {
                                order.addProduct(copy);
                                printSuccess("✓ " + copy.getName() + " added to order!");
                            }
                        }
                    } else {
                        printError("Product not found or unavailable.");
                    }
                }
            } catch (NumberFormatException _) {
                printError("Invalid product ID.");
            }
        }
        
        // Show order summary
        displayOrderSummary(order);
        
        // Confirm or cancel
        System.out.print("\nConfirm order? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            order.confirmOrder();
            if (order.getStatus().toString().equals("PENDING")) {
                printError("Order confirmation failed - check balance or preparation area!");
            } else {
                printSuccess("✓ Order confirmed and sent to preparation area!");
            }
        } else {
            order.cancelOrder();
            printSuccess("✓ Order cancelled.");
        }
        
        pause();
    }
    
    /**
     * Customize Drink - Add size, sugar level, milk level, ice level, toppings
     */
    private static void customizeDrink(Drink drink, Order order) {
        clearScreen();
        printHeader("CUSTOMIZE DRINK - " + drink.getName().toUpperCase());
        
        System.out.println("\nBase Price: RM " + String.format("%.2f", drink.getPrice()));
        
        // Size
        System.out.println("\n--- Select Size ---");
        System.out.println("1. Small (RM 0.00)");
        System.out.println("2. Medium (RM 1.00) - Default");
        System.out.println("3. Large (RM 2.00)");
        System.out.print("Select size: ");
        
        String sizeChoice = scanner.nextLine().trim();
        switch (sizeChoice) {
            case "1":
                drink.setSize(Size.SMALL);
                break;
            case "3":
                drink.setSize(Size.LARGE);
                break;
            default: // 2 or other
                drink.setSize(Size.MEDIUM);
        }
        
        // Sugar Level
        System.out.println("\n--- Select Sugar Level ---");
        System.out.println("1. Less (RM 0.00)");
        System.out.println("2. Normal (RM 1.00) - Default");
        System.out.println("3. More (RM 2.00)");
        System.out.print("Select sugar level: ");
        
        String sugarChoice = scanner.nextLine().trim();
        switch (sugarChoice) {
            case "1":
                drink.setSugarLevel(Level.LESS);
                break;
            case "3":
                drink.setSugarLevel(Level.MORE);
                break;
            default: // 2 or other
                drink.setSugarLevel(Level.NORMAL);
        }
        
        // Milk Level
        System.out.println("\n--- Select Milk Level ---");
        System.out.println("1. Less (RM 0.00)");
        System.out.println("2. Normal (RM 1.00) - Default");
        System.out.println("3. More (RM 2.00)");
        System.out.print("Select milk level: ");
        
        String milkChoice = scanner.nextLine().trim();
        switch (milkChoice) {
            case "1":
                drink.setMilkLevel(Level.LESS);
                break;
            case "3":
                drink.setMilkLevel(Level.MORE);
                break;
            default: // 2 or other
                drink.setMilkLevel(Level.NORMAL);
        }
        
        // Ice Level
        System.out.println("\n--- Select Ice Level ---");
        System.out.println("1. Less (RM 0.00)");
        System.out.println("2. Normal (RM 1.00) - Default");
        System.out.println("3. More (RM 2.00)");
        System.out.print("Select ice level: ");
        
        String iceChoice = scanner.nextLine().trim();
        switch (iceChoice) {
            case "1":
                drink.setIceLevel(Level.LESS);
                break;
            case "3":
                drink.setIceLevel(Level.MORE);
                break;
            default: // 2 or other
                drink.setIceLevel(Level.NORMAL);
        }
        
        // Toppings
        System.out.println("\n--- Add Toppings ---");
        boolean addingToppings = true;
        while (addingToppings) {
            System.out.println("\n1. Chocolate (RM 0.50)");
            System.out.println("2. Caramel (RM 0.50)");
            System.out.println("3. Whipped Cream (RM 0.50)");
            System.out.println("4. Done");
            System.out.print("Select topping (or 4 to finish): ");
            
            String toppingChoice = scanner.nextLine().trim();
            switch (toppingChoice) {
                case "1":
                    drink.addTopping("Chocolate");
                    System.out.println("✓ Chocolate added");
                    break;
                case "2":
                    drink.addTopping("Caramel");
                    System.out.println("✓ Caramel added");
                    break;
                case "3":
                    drink.addTopping("Whipped Cream");
                    System.out.println("✓ Whipped Cream added");
                    break;
                case "4":
                    addingToppings = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        
        order.addProduct(drink);
        clearScreen();
        printSuccess("✓ Drink added to order!");
        System.out.println("\n" + drink.prettyPrint());
        System.out.println("Final Price: RM " + String.format("%.2f", drink.calculatePrice()));
    }
    
    /**
     * Customize Sandwich - Add ingredients
     */
    private static void customizeSandwich(Sandwich sandwich, Order order) {
        clearScreen();
        printHeader("CUSTOMIZE SANDWICH - " + sandwich.getName().toUpperCase());
        
        System.out.println("\nBase Price: RM " + String.format("%.2f", sandwich.getPrice()));
        System.out.println("Ingredients: RM 0.25 each");
        
        boolean addingIngredients = true;
        while (addingIngredients) {
            System.out.println("\n--- Add Ingredients ---");
            System.out.println("1. Lettuce");
            System.out.println("2. Tomato");
            System.out.println("3. Onion");
            System.out.println("4. Cheese");
            System.out.println("5. Mayo");
            System.out.println("6. Done");
            System.out.print("Select ingredient (or 6 to finish): ");
            
            String ingredientChoice = scanner.nextLine().trim();
            switch (ingredientChoice) {
                case "1":
                    sandwich.addIngredient("Lettuce");
                    System.out.println("✓ Lettuce added");
                    break;
                case "2":
                    sandwich.addIngredient("Tomato");
                    System.out.println("✓ Tomato added");
                    break;
                case "3":
                    sandwich.addIngredient("Onion");
                    System.out.println("✓ Onion added");
                    break;
                case "4":
                    sandwich.addIngredient("Cheese");
                    System.out.println("✓ Cheese added");
                    break;
                case "5":
                    sandwich.addIngredient("Mayo");
                    System.out.println("✓ Mayo added");
                    break;
                case "6":
                    addingIngredients = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        
        order.addProduct(sandwich);
        clearScreen();
        printSuccess("✓ Sandwich added to order!");
        System.out.println("\n" + sandwich.prettyPrint());
        System.out.println("Ingredients: " + sandwich.getIngredients());
        System.out.println("Final Price: RM " + String.format("%.2f", sandwich.calculatePrice()));
    }
    
    /**
     * Display order summary
     */
    private static void displayOrderSummary(Order order) {
        clearScreen();
        printHeader("ORDER SUMMARY");
        
        System.out.println("\nOrder ID: " + order.getOrderID());
        System.out.println("User: " + order.getUser().prettyPrint());
        System.out.println("\nItems:");
        
        List<Product> products = order.getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println("  " + (i + 1) + ". " + p.prettyPrint() + " - RM " + 
                             String.format("%.2f", p.calculatePrice()));
        }
        
        System.out.println("\n--- Pricing Breakdown ---");
        double subtotal = 0;
        for (Product p : products) {
            subtotal += p.calculatePrice();
        }
        System.out.println("Subtotal: RM " + String.format("%.2f", subtotal));
        
        double totalAfterDiscount = order.calculateTotalPrice();
        if (order.getUser() instanceof Lecturer) {
            double discount = subtotal - totalAfterDiscount;
            System.out.println("Staff Discount (10%): -RM " + String.format("%.2f", discount));
        }
        
        System.out.println("Total: RM " + String.format("%.2f", totalAfterDiscount));
        System.out.println("Your Balance: RM " + String.format("%.2f", order.getUser().getBalance()));
    }
    
    /**
     * Admin Menu - Manage products, users, and view system info
     */
    private static void adminMenu() {
        while (true) {
            clearScreen();
            printHeader("ADMIN INTERFACE");
            System.out.println("\n1. Manage Products");
            System.out.println("2. View Users");
            System.out.println("3. View Orders in Preparation");
            System.out.println("4. View Transaction Logs");
            System.out.println("5. System Statistics");
            System.out.println("6. Back to Main Menu");
            System.out.print("\nSelect option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    manageProducts();
                    break;
                case "2":
                    viewUsers();
                    break;
                case "3":
                    viewPreparationOrders();
                    break;
                case "4":
                    viewTransactionLogs();
                    break;
                case "5":
                    systemStatistics();
                    break;
                case "6":
                    return;
                default:
                    printError("Invalid option.");
            }
        }
    }
    
    /**
     * Manage Products - Add, Update, Remove, View
     */
    private static void manageProducts() {
        while (true) {
            clearScreen();
            printHeader("MANAGE PRODUCTS");
            System.out.println("\n1. View All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Update Product Price");
            System.out.println("4. Update Product Availability");
            System.out.println("5. Remove Product");
            System.out.println("6. Back");
            System.out.print("\nSelect option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    viewAllProducts();
                    break;
                case "2":
                    addNewProduct();
                    break;
                case "3":
                    updateProductPrice();
                    break;
                case "4":
                    updateProductAvailability();
                    break;
                case "5":
                    removeProduct();
                    break;
                case "6":
                    return;
                default:
                    printError("Invalid option.");
            }
        }
    }
    
    /**
     * View all products
     */
    private static void viewAllProducts() {
        clearScreen();
        printHeader("ALL PRODUCTS");
        
        List<Product> products = menu.getProducts();
        
        if (products.isEmpty()) {
            System.out.println("\nNo products in menu.");
        } else {
            for (Product product : products) {
                String available = product.isAvailable() ? "✓ Available" : "✗ Unavailable";
                System.out.println("[ID: " + product.getProductID() + "] " + 
                                 product.getName() + " - RM " + 
                                 String.format("%.2f", product.getPrice()) + " | " + available);
            }
        }
        
        pause();
    }
    
    /**
     * Add new product
     */
    private static void addNewProduct() {
        clearScreen();
        printHeader("ADD NEW PRODUCT");
        
        System.out.println("\n1. Drink");
        System.out.println("2. Snack");
        System.out.println("3. Sandwich");
        System.out.print("\nSelect product type: ");
        
        String type = scanner.nextLine().trim();
        
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter price (MYR): ");
        
        try {
            double price = Double.parseDouble(scanner.nextLine().trim());
            
            switch (type) {
                case "1":
                    Drink drink = new Drink(name, price, true);
                    menu.addProduct(drink);
                    printSuccess("✓ Drink added: " + name + " (ID: " + drink.getProductID() + ")");
                    break;
                case "2":
                    Snack snack = new Snack(name, price, true);
                    menu.addProduct(snack);
                    printSuccess("✓ Snack added: " + name + " (ID: " + snack.getProductID() + ")");
                    break;
                case "3":
                    Sandwich sandwich = new Sandwich(name, price, true);
                    menu.addProduct(sandwich);
                    printSuccess("✓ Sandwich added: " + name + " (ID: " + sandwich.getProductID() + ")");
                    break;
                default:
                    printError("Invalid product type.");
            }
        } catch (NumberFormatException e) {
            printError("Invalid price format.");
        }
        
        pause();
    }
    
    /**
     * Update product price
     */
    private static void updateProductPrice() {
        clearScreen();
        printHeader("UPDATE PRODUCT PRICE");
        
        viewAllProducts();
        clearScreen();
        printHeader("UPDATE PRODUCT PRICE");
        
        System.out.print("\nEnter product ID to update: ");
        
        try {
            int productID = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter new price (MYR): ");
            double newPrice = Double.parseDouble(scanner.nextLine().trim());
            
            adminSystem.updateProductPrice(productID, newPrice);
            printSuccess("✓ Price updated!");
        } catch (NumberFormatException e) {
            printError("Invalid format.");
        }
        
        pause();
    }
    
    /**
     * Update product availability
     */
    private static void updateProductAvailability() {
        clearScreen();
        printHeader("UPDATE PRODUCT AVAILABILITY");
        
        viewAllProducts();
        clearScreen();
        printHeader("UPDATE PRODUCT AVAILABILITY");
        
        System.out.print("\nEnter product ID: ");
        
        try {
            int productID = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Available? (yes/no): ");
            String available = scanner.nextLine().trim().toLowerCase();
            
            boolean isAvailable = available.equals("yes") || available.equals("y");
            adminSystem.updateProductAvailability(productID, isAvailable);
            printSuccess("✓ Availability updated!");
        } catch (NumberFormatException e) {
            printError("Invalid format.");
        }
        
        pause();
    }
    
    /**
     * Remove product
     */
    private static void removeProduct() {
        clearScreen();
        printHeader("REMOVE PRODUCT");
        
        viewAllProducts();
        clearScreen();
        printHeader("REMOVE PRODUCT");
        
        System.out.print("\nEnter product ID to remove: ");
        
        try {
            int productID = Integer.parseInt(scanner.nextLine().trim());
            menu.removeProduct(productID);
            printSuccess("✓ Product removed!");
        } catch (NumberFormatException e){
            printError("Invalid format.");
        }
        
        pause();
    }
    
    /**
     * View all users
     */
    private static void viewUsers() {
        clearScreen();
        printHeader("ALL USERS");
        
        java.util.Map<Integer, User> users = cardSystem.getUsers();
        
        if (users.isEmpty()) {
            System.out.println("\nNo users registered.");
        } else {
            for (User user : users.values()) {
                System.out.println(user.prettyPrint());
            }
        }
        
        pause();
    }
    
    /**
     * View orders in preparation area
     */
    private static void viewPreparationOrders() {
        clearScreen();
        printHeader("ORDERS IN PREPARATION");
        
        List<Order> orders = (List<Order>) PreparationAreaSystem.getPendingOrders();
        
        if (orders.isEmpty()) {
            System.out.println("\nNo orders pending.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderID() + " | User: " + 
                                 order.getUser().getName() + " | Status: " + order.getStatus() + 
                                 " | Total: RM " + String.format("%.2f", order.getTotalPrice()));
            }
        }
        
        pause();
    }
    
    /**
     * View transaction logs
     */
    private static void viewTransactionLogs() {
        clearScreen();
        printHeader("TRANSACTION LOGS");
        
        if (kiosks.isEmpty()) {
            System.out.println("\nNo kiosks available.");
        } else {
            Kiosk kiosk = kiosks.getFirst();
            List<Order> logs = adminSystem.viewTransactionLogs(kiosk);
            
            if (logs.isEmpty()) {
                System.out.println("\nNo transactions.");
            } else {
                for (Order order : logs) {
                    System.out.println("Order ID: " + order.getOrderID() + " | User: " + 
                                     order.getUser().getName() + " | Amount: RM " + 
                                     String.format("%.2f", order.getTotalPrice()) + 
                                     " | Time: " + order.getTimestamp());
                }
            }
        }
        
        pause();
    }
    
    /**
     * System statistics
     */
    private static void systemStatistics() {
        clearScreen();
        printHeader("SYSTEM STATISTICS");
        
        java.util.Map<Integer, User> users = cardSystem.getUsers();
        List<Product> products = menu.getProducts();
        List<Product> availableProducts = menu.getAvailableProducts();
        List<Order> pendingOrders = (List<Order>) PreparationAreaSystem.getPendingOrders();
        
        System.out.println("\n--- Users ---");
        System.out.println("Total Users: " + users.size());
    long students = users.values().stream().filter(Student.class::isInstance).count();
    long lecturers = users.values().stream().filter(Lecturer.class::isInstance).count();
        System.out.println("Students: " + students);
        System.out.println("Lecturers: " + lecturers);
        
        System.out.println("\n--- Products ---");
        System.out.println("Total Products: " + products.size());
        System.out.println("Available Products: " + availableProducts.size());
        System.out.println("Unavailable Products: " + (products.size() - availableProducts.size()));
        
        System.out.println("\n--- Orders ---");
        System.out.println("Pending Orders: " + pendingOrders.size());
        System.out.println("Kiosks: " + kiosks.size());
        System.out.println("Menu Observers: " + centralMenuSystem.getObservers().size());
        
        pause();
    }
    
    /**
     * Display available products (helper)
     */
    private static void displayAvailableProducts() {
        List<Product> products = menu.getAvailableProducts();
        int index = 1;
        for (Product product : products) {
            System.out.println(index + ". [ID: " + product.getProductID() + "] " + 
                             product.getName() + " - RM " + String.format("%.2f", product.getPrice()));
            index++;
        }
    }
    
    /**
     * UI Helper Methods
     */
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void printHeader(String title) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║    " + String.format("%-50s", title) + "  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    private static void printSuccess(String message) {
        System.out.println("\n✓ " + message);
    }
    
    private static void printError(String message) {
        System.out.println("\n✗ " + message);
    }
    
    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
