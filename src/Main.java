import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Item> items = readItemsFromInput();
        ReceiptGenerator receiptGenerator = new ReceiptGenerator(items);
        receiptGenerator.generateReceipt();
    }

    private static List<Item> readItemsFromInput() {
        List<Item> items = new ArrayList<>();
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

    private static Item parseItem(String input) throws IllegalArgumentException {
    int atIndex = input.lastIndexOf(" at ");
    if (atIndex < 0) {
        throw new IllegalArgumentException("Invalid format.");
    }

    String quantityAndName = input.substring(0, atIndex).trim();
    double price = Double.parseDouble(input.substring(atIndex + 4).trim());

    int quantity = 1;
    int firstSpaceIndex = quantityAndName.indexOf(' ');
    if (firstSpaceIndex > 0) {
        try {
            quantity = Integer.parseInt(quantityAndName.substring(0, firstSpaceIndex));
        } catch (NumberFormatException e) {
            // If the quantity is not a valid integer, set it to 1
            quantity = 1;
        }
    }

    String name = quantityAndName.substring(firstSpaceIndex + 1);
    if (name.startsWith("imported ")) {
        name = name.substring(9); // Remove "imported " from the name
        return new TaxableItem(name, price, quantity, true);
    } else {
        return new ExemptItem(name, price, quantity, false);
    }
}
