package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
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

    public void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        productQuantities.forEach((product, quantity) -> {
            if (offers.containsKey(product)) {
                applyOfferToProduct(receipt, quantity, catalog.getUnitPrice(product), offers.get(product));
            }
        });
    }

    private void applyOfferToProduct(Receipt receipt, Double quantity, double unitPrice, Offer offer) {
        Discount discount = offer.buildDiscount(quantity, unitPrice);
        receipt.addDiscount(discount);
    }

}
