import java.text.DecimalFormat;

public class TaxableItem extends Item {
    private static final double BASIC_SALES_TAX_RATE = 0.1;

    public TaxableItem(String name, double price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }

    @Override
    public double calculateSalesTax() {
        double taxRate = BASIC_SALES_TAX_RATE;
        if (isImported()) {
            taxRate += 0.05;
        }
        return roundToNearest0_05(getPrice() * taxRate);
    }

    @Override
    public String getDetails() {
        return getQuantity() + " " + (isImported() ? "imported " : "") + getName() + ": " + formatCurrency(calculateItemPriceWithTax());
    }

    private double calculateItemPriceWithTax() {
        return getPrice() + calculateSalesTax();
    }

    private double roundToNearest0_05(double value) {
        double roundedValue = Math.ceil(value * 20) / 20.0;
        return roundTo2DecimalPlaces(roundedValue);
    }

    private double roundTo2DecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(amount);
    }
}
