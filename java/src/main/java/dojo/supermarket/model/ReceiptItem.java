package dojo.supermarket.model;

import java.util.Locale;
import java.util.Objects;

public class ReceiptItem {
    private final Product product;
    private final double price;
    private double totalPrice;
    private final double quantity;

    public ReceiptItem(Product p, double quantity, double price, double totalPrice) {
        this.product = p;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return this.price;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return Double.compare(that.price, price) == 0 &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {

        return Objects.hash(product, price, totalPrice, quantity);
    }

    @Override
    public String toString() {
        String totalPricePresentation = String.format(Locale.UK, "%.2f", getTotalPrice());
        String line = formatLineWithWhitespace(getProduct().getName(), totalPricePresentation);

        if (getQuantity() != 1) {
            line += "  " + String.format(Locale.UK, "%.2f", getPrice()) + " * " + presentQuantity() + "\n";
        }
        return line;
    }

    public String formatLineWithWhitespace(String name, String value) {
        StringBuilder line = new StringBuilder();
        line.append(name);
        int whitespaceSize = 40 - name.length() - value.length();
        for (int i = 0; i < whitespaceSize; i++) {
            line.append(" ");
        }
        line.append(value);
        line.append('\n');
        return line.toString();
    }

    private String presentQuantity() {
        return ProductUnit.Each.equals(this.product.getUnit())
                ? String.format("%x", (int)this.quantity)
                : String.format(Locale.UK, "%.3f", this.quantity);
    }
}
