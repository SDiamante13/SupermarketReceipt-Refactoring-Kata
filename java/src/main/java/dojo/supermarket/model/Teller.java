package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class Teller {
    private final SupermarketCatalog catalog;
    private final Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart shoppingCart) {
        Receipt receipt = new Receipt();

        for (Item item : shoppingCart.getItems()) {
            Product product = item.getProduct();
            double quantity = item.getQuantity();

            double price = quantity * catalog.getUnitPrice(product);

            receipt.addProduct(product, quantity, catalog.getUnitPrice(product), price);
        }

        shoppingCart.handleOffers(receipt, offers, catalog);

        return receipt;
    }

}
