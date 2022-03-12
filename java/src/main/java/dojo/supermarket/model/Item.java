package dojo.supermarket.model;

public class Item {
    private final Product product;
    private final double quantity;

    public Item(Product product, double weight) {
        this.product = product;
        this.quantity = weight;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }
}
