public class ExemptItem extends Item {
    public ExemptItem(String name, double price, int quantity, boolean imported) {
        super(name, price, quantity, imported);
    }

    @Override
    public double calculateSalesTax() {
        return 0;
    }

    @Override
    public String getDetails() {
        return (isImported() ? "imported " : "") + getQuantity() + " " + getName();
    }
}
