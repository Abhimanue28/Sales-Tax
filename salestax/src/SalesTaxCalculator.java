import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalesTaxCalculator {
    private static final double BASIC_SALES_TAX_RATE = 0.1;
    private static final double IMPORT_DUTY_RATE = 0.05;
    private static final List<String> EXEMPT_CATEGORIES = List.of("book", "food", "medical product");

    private List<Item> items;

    private List<Item> readItemsFromInput() {
        items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter items in the format: <quantity> <item name> at <price>");
        System.out.println("Press Enter after each item. Type 'done' to finish input.");

        String input;
        do {
            input = scanner.nextLine().trim();
            if (!input.equalsIgnoreCase("done")) {
                try {
                    Item item = parseItem(input);
                    items.add(item);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input. " + e.getMessage() + " Please try again.");
                }
            }
        } while (!input.equalsIgnoreCase("done"));

        scanner.close();
        return items;
    }

    private Item parseItem(String input) throws IllegalArgumentException {
        int atIndex = input.lastIndexOf(" at ");
        if (atIndex < 0) {
            throw new IllegalArgumentException("Invalid format.");
        }
    
        String quantityAndName = input.substring(0, atIndex).trim();
        double price = Double.parseDouble(input.substring(atIndex + 4).trim());
    
        int quantity = 1;
        int lastSpaceIndex = quantityAndName.lastIndexOf(' ');
        if (lastSpaceIndex > 0) {
            try {
                quantity = Integer.parseInt(quantityAndName.substring(0, lastSpaceIndex));
            } catch (NumberFormatException e) {
                // If the quantity is not a valid integer, set it to 1
                quantity = 1;
            }
        }
    
        String name = quantityAndName.substring(lastSpaceIndex + 1);
        if (name.startsWith("imported ")) {
            name = name.substring(9); // Remove "imported " from the name
        }
    
        return new Item(name, price, quantity);
    }
    
    
    

    private void generateReceipt() {
        double totalSalesTax = 0;
        double totalCost = 0;
    
        System.out.println("Output:");
        for (Item item : items) {
            double salesTax = calculateSalesTax(item);
            double itemPrice = item.getPrice() + salesTax;
            totalSalesTax += salesTax;
            totalCost += itemPrice;
    
            System.out.println(item.getQuantity() + " " + (item.isImported() ? "imported " : "") + item.getName() + ": " + formatCurrency(itemPrice));
        }
    
        System.out.println("Sales Taxes: " + formatCurrency(totalSalesTax));
        System.out.println("Total: " + formatCurrency(totalCost));
    }
    
    
    

    private double calculateSalesTax(Item item) {
        double taxRate = 0;
        if (!isExempt(item.getName())) {
            taxRate += BASIC_SALES_TAX_RATE;
        }
        if (item.isImported()) {
            taxRate += IMPORT_DUTY_RATE;
        }

        double rawSalesTax = item.getPrice() * taxRate;
        return roundToNearest0_05(rawSalesTax);
    }

    private boolean isExempt(String itemName) {
        for (String category : EXEMPT_CATEGORIES) {
            if (itemName.contains(category)) {
                return true;
            }
        }
        return false;
    }

    private double roundToNearest0_05(double value) {
        double roundedValue = Math.ceil(value * 20) / 20.0;
        return roundTo2DecimalPlaces(roundedValue);
    }

    private double roundTo2DecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(amount);
    }

    public void run() {
        List<Item> items = readItemsFromInput();
        generateReceipt();
    }
}
