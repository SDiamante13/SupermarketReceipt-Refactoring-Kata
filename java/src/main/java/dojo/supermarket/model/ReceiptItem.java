package dojo.supermarket.model;

import dojo.supermarket.utils.Formatter;

import java.util.Locale;
import java.util.Objects;

public class ReceiptItem {
    private final Product product;
    private final double price;
    private final double totalPrice;
    private final double quantity;

    // TODO: total price could be derived
    public ReceiptItem(Product product, double quantity, double price, double totalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
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

    public String print() {
        String totalPricePresentation = String.format(Locale.UK, "%.2f", totalPrice);
        String line = Formatter.formatLineWithWhitespace(product.getName(), totalPricePresentation);

        if (getQuantity() != 1) {
            line += "  " + String.format(Locale.UK, "%.2f", price) + " * " + presentQuantity() + "\n";
        }
        return line;
    }

    private String presentQuantity() {
        return ProductUnit.Each.equals(this.product.getUnit())
                ? String.format("%x", (int)this.quantity)
                : String.format(Locale.UK, "%.3f", this.quantity);
    }
}
