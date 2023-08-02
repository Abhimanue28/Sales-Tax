import java.text.DecimalFormat;
import java.util.List;

public class ReceiptGenerator {
    private List<Item> items;

    public ReceiptGenerator(List<Item> items) {
        this.items = items;
    }

    public void generateReceipt() {
        double totalSalesTax = 0;
        double totalCost = 0;

        System.out.println("Output:");
        for (Item item : items) {
            double salesTax = item.calculateSalesTax();
            double itemPrice = item.getPrice() + salesTax;
            totalSalesTax += salesTax;
            totalCost += itemPrice;

            System.out.println(item.getDetails());
        }

        System.out.println("Sales Taxes: " + formatCurrency(totalSalesTax));
        System.out.println("Total: " + formatCurrency(totalCost));
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(amount);
    }
}

