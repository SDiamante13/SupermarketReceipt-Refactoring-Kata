package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double amount;

    public Offer(SpecialOfferType offerType, Product product, double amount) {
        this.offerType = offerType;
        this.amount = amount;
        this.product = product;
    }

    public Discount buildDiscount(Double quantity, double unitPrice) {
        int quantityAsInt = Math.toIntExact(Math.round(quantity));
        return offerType.isEligible(quantityAsInt)
                ? new Discount(
                product,
                offerType.description(amount),
                offerType.discountAmount(quantity, unitPrice, quantityAsInt, amount)
        )
                : null;
    }
}
