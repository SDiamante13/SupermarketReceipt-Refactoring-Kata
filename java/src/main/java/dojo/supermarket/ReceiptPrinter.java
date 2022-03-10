package dojo.supermarket;

import dojo.supermarket.model.*;
import dojo.supermarket.utils.Formatter;

import java.util.Locale;

public class ReceiptPrinter {

    public String printReceipt(Receipt receipt) {
        StringBuilder result = new StringBuilder();

        addItemsToReceipt(receipt, result);

        for (Discount discount : receipt.getDiscounts()) {
            String name = discount.getDescription() + "(" + discount.getProduct().getName() + ")";
            String value = String.format(Locale.UK, "%.2f", discount.getDiscountAmount());

            String discountPresentation = Formatter.formatLineWithWhitespace(name, value);
            result.append(discountPresentation);
        }

        result.append("\n");
        result.append(presentTotal(receipt));
        return result.toString();
    }

    private void addItemsToReceipt(Receipt receipt, StringBuilder result) {
        receipt.getItems()
                .forEach(item -> result.append(item.print()));
    }

    private String presentTotal(Receipt receipt) {
        String name = "Total: ";
        String value = String.format(Locale.UK, "%.2f", receipt.getTotalPrice());
        return Formatter.formatLineWithWhitespace(name, value);
    }

}
