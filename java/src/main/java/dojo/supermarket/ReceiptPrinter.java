package dojo.supermarket;

import dojo.supermarket.model.*;
import dojo.supermarket.utils.Formatter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class ReceiptPrinter {

    public static final String NEW_LINE = "\n";

    public String printReceipt(Receipt receipt) {
        List<String> itemsOnReceipt = formatItemsOnReceipt(receipt.getItems());
        List<String> discountsOnReceipt = formatDiscountsOnReceipt(receipt.getDiscounts());

        return join("", itemsOnReceipt) +
                join("", discountsOnReceipt) +
                NEW_LINE +
                receipt.formatTotalPrice();
    }

    private List<String> formatDiscountsOnReceipt(List<Discount> discounts) {
        return discounts.stream()
                .map(Discount::print)
                .collect(Collectors.toList());
    }

    private List<String> formatItemsOnReceipt(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::print)
                .collect(Collectors.toList());
    }
}
