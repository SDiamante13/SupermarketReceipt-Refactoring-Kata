package dojo.supermarket.model;

import dojo.supermarket.utils.Formatter;

import java.util.Locale;

public class Discount {
    private final String description;
    private final double discountAmount;
    private final Product product;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

    public String print() {
        String name = getDescription() + "(" + getProduct().getName() + ")";
        String value = String.format(Locale.UK, "%.2f", getDiscountAmount());

        return Formatter.formatLineWithWhitespace(name, value);
    }
}
