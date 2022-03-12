package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    // TODO use Set of products, can move quantity into product model

    private final List<Item> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<Item> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new Item(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        productQuantities.forEach((product, quantity) -> {
            if (offers.containsKey(product)) {
                applyOfferToProduct(receipt, offers, catalog, product, quantity);
            }
        });
    }

    private void applyOfferToProduct(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog, Product product, Double quantity) {
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = Math.toIntExact(Math.round(quantity));

        Discount discount = buildDiscount(offers, product, quantity, unitPrice, quantityAsInt);

        if (discount != null) {
            receipt.addDiscount(discount);
        }
    }

    private Discount buildDiscount(Map<Product, Offer> offers, Product product, Double quantity, double unitPrice, int quantityAsInt) {
        Offer offer = offers.get(product);

        if (offer.offerType.equals(SpecialOfferType.TwoForAmount) && quantityAsInt >= 2) {

            int discountRate = 2;

            double pricePerUnit = offer.argument * (quantityAsInt / discountRate);
            double total = pricePerUnit + (quantityAsInt % 2) * unitPrice;
            double discountN = unitPrice * quantity - total;
            String description = "2 for " + offer.argument;

            return new Discount(product, description, -discountN);
        } else if (offer.offerType.equals(SpecialOfferType.FiveForAmount) && quantityAsInt >= 5) {
            int discountRate = 5;

            double discountTotal = unitPrice * quantity - (offer.argument * (quantityAsInt / discountRate) + quantityAsInt % 5 * unitPrice);
            return new Discount(product, discountRate + " for " + offer.argument, -discountTotal);
        } else if (offer.offerType.equals(SpecialOfferType.ThreeForTwo) && quantityAsInt >= 3) {
            int discountRate = 3;
            double discountAmount =
                    quantity *
                            unitPrice -
                            (((quantityAsInt / discountRate) * 2 * unitPrice)
                                    + quantityAsInt % 3 * unitPrice);
            return new Discount(product, "3 for 2", -discountAmount);
        } else if (offer.offerType.equals(SpecialOfferType.TenPercentDiscount)) {
            return new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        } else {
            return null;
        }
    }

}
