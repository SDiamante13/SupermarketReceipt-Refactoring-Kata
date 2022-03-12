package dojo.supermarket.model;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupermarketTest {
    private final Teller teller = new Teller();
    private final ShoppingCart theCart = new ShoppingCart();
    private final Product toothbrush = new Product("toothbrush", ProductUnitType.EACH, 0.99);
    private final Product rice = new Product("rice", ProductUnitType.EACH, 2.99);
    private final Product apples = new Product("apples", ProductUnitType.KILO, 1.99);
    private final Product cherryTomatoes = new Product("cherry tomato box", ProductUnitType.EACH, 0.69);

    @Test
    void an_empty_shopping_cart_should_cost_nothing() {
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void one_normal_item() {
        theCart.addItem(toothbrush);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void two_normal_items() {
        theCart.addItem(toothbrush);
        theCart.addItem(rice);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void buy_two_get_one_free() {
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, toothbrush, toothbrush.getPrice());
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void buy_two_get_one_free_but_insufficient_in_basket() {
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, toothbrush, toothbrush.getPrice());
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }
    @Test
    void buy_five_get_one_free() {
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, toothbrush, toothbrush.getPrice());
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void loose_weight_product() {
        theCart.addItemQuantity(apples, .5);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void percent_discount() {
        theCart.addItem(rice);
        teller.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, rice, 10.0);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void xForY_discount() {
        theCart.addItem(cherryTomatoes);
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(SpecialOfferType.TWO_FOR_AMOUNT, cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void xForY_discount_with_insufficient_in_basket() {
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(SpecialOfferType.TWO_FOR_AMOUNT, cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount() {
        theCart.addItemQuantity(apples, 5);
        teller.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, apples,6.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withSix() {
        theCart.addItemQuantity(apples, 6);
        teller.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, apples,5.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withSixteen() {
        theCart.addItemQuantity(apples, 16);
        teller.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, apples,7.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withFour() {
        theCart.addItemQuantity(apples, 4);
        teller.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, apples,8.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }
}
