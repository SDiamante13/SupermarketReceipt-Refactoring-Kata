package dojo.supermarket.model;

import dojo.supermarket.utils.Formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Receipt {
    public static final String NEW_LINE = "\n";

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public Double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : this.items) {
            total += item.getTotalPrice();
        }
        for (Discount discount : this.discounts) {
            total += discount.getDiscountAmount();
        }
        return total;
    }

    public void addProduct(Product product, double quantity, double price, double totalPrice) {
        this.items.add(new ReceiptItem(product, quantity, price, totalPrice));
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    public String print() {
        return addItemsTo() +
                addDiscountsTo() +
                NEW_LINE +
                formatTotalPrice();
    }

    private String addItemsTo() {
        return items.stream()
                .map(ReceiptItem::print)
                .collect(Collectors.joining(""));
    }

    private String addDiscountsTo() {
        return discounts.stream()
                .map(Discount::print)
                .collect(Collectors.joining(""));
    }

    private String formatTotalPrice() {
        return Formatter.formatLineWithWhitespace("Total: ", String.format(Locale.UK, "%.2f", getTotalPrice()));
    }
}
