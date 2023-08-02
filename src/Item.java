public abstract class Item {
    private String name;
    private double price;
    private int quantity;
    private boolean imported;

    public Item(String name, double price, int quantity, boolean imported) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imported = imported;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isImported() {
        return imported;
    }

    public abstract double calculateSalesTax();

    public abstract String getDetails();
}
