package dojo.supermarket;

import dojo.supermarket.model.*;

import java.util.Locale;

public class ReceiptPrinter {

    private final int columns;

    public ReceiptPrinter() {
        this(40);
    }

    public ReceiptPrinter(int columns) {
        this.columns = columns;
    }

    public String printReceipt(Receipt receipt) {
        StringBuilder result = new StringBuilder();
        for (ReceiptItem item : receipt.getItems()) {
            String line = item.toString();

            //result.append(item.toString());
            result.append(line);
        }

        for (Discount discount : receipt.getDiscounts()) {
            String discountPresentation = presentDiscount(discount);
            result.append(discountPresentation);
        }

        result.append("\n");
        result.append(presentTotal(receipt));
        return result.toString();
    }

    private String presentDiscount(Discount discount) {
        String name = discount.getDescription() + "(" + discount.getProduct().getName() + ")";
        String value = String.format(Locale.UK, "%.2f", discount.getDiscountAmount());

        return formatLineWithWhitespace(name, value, this.columns);
    }

    private String presentTotal(Receipt receipt) {
        String name = "Total: ";
        String value = String.format(Locale.UK, "%.2f", receipt.getTotalPrice());
        return formatLineWithWhitespace(name, value, this.columns);
    }

    public String formatLineWithWhitespace(String name, String value, int columns) {
        StringBuilder line = new StringBuilder();
        line.append(name);
        int whitespaceSize = columns - name.length() - value.length();
        for (int i = 0; i < whitespaceSize; i++) {
            line.append(" ");
        }
        line.append(value);
        line.append('\n');
        return line.toString();
    }

}
