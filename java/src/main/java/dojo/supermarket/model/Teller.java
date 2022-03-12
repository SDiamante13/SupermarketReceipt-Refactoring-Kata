package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class Teller {
    private final Map<Product, Offer> offers = new HashMap<>();

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart shoppingCart) {
        Receipt receipt = new Receipt();

        for (Item item : shoppingCart.getItems()) {
            Product product = item.getProduct();
            double quantity = item.getQuantity();

            // TODO: move this into a query. product.calculatePriceFor(quantity)
            double price = quantity * product.getPrice();

            // TODO: remove price parameter
            receipt.addProduct(product, quantity, product.getPrice(), price);
        }

        shoppingCart.handleOffers(receipt, offers);

        return receipt;
    }

}
