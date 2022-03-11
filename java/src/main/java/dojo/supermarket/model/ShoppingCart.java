package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    // TODO use Set of products, can move quantity into product model

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
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
        Offer offer = offers.get(product);
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = Math.toIntExact(Math.round(quantity));
        Discount discount = null;
        int x = 1;

        if (offer.offerType == SpecialOfferType.ThreeForTwo) {
            x = 3;
        } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
            x = 2;
            if (quantityAsInt >= 2) {
                int intDivision = quantityAsInt / x;
                double pricePerUnit = offer.argument * intDivision;
                double total = pricePerUnit + (quantityAsInt % 2) * unitPrice;
                double discountN = unitPrice * quantity - total;
                String description = "2 for " + offer.argument;

                discount = new Discount(product, description, -discountN);
            }
        }


        if (offer.offerType == SpecialOfferType.FiveForAmount) {
            x = 5;
        }
        int numberOfXs = quantityAsInt / x;

        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(product, "3 for 2", -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(product, x + " for " + offer.argument, -discountTotal);
        }
        if (discount != null) {
            receipt.addDiscount(discount);
        }
    }
}
