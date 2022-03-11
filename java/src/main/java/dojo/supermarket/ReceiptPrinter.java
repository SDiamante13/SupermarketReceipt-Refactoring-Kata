package dojo.supermarket;

import dojo.supermarket.model.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class ReceiptPrinter {

    public static final String NEW_LINE = "\n";

    public String printReceipt(Receipt receipt) {
        return join("", formatItemsOnReceipt(receipt)) +
                join("", formatDiscountsOnReceipt(receipt)) +
                NEW_LINE +
                receipt.formatTotalPrice();
    }

    private List<String> formatDiscountsOnReceipt(Receipt receipt) {
        return receipt.getDiscounts().stream()
                .map(Discount::print)
                .collect(Collectors.toList());
    }

    private List<String> formatItemsOnReceipt(Receipt receipt) {
        return receipt.getItems().stream()
                .map(ReceiptItem::print)
                .collect(Collectors.toList());
    }
}
