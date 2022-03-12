package dojo.supermarket.model;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupermarketTest {
    private final SupermarketCatalog fakeCatalog = new FakeCatalog();
    private final Teller teller = new Teller(fakeCatalog);
    private final ShoppingCart theCart = new ShoppingCart();
    private final Product toothbrush = new Product("toothbrush", ProductUnitType.EACH);
    private final Product rice = new Product("rice", ProductUnitType.EACH);
    private final Product apples = new Product("apples", ProductUnitType.KILO);
    private final Product cherryTomatoes = new Product("cherry tomato box", ProductUnitType.EACH);

    @BeforeEach
    void setUp() {
        fakeCatalog.addProduct(toothbrush, 0.99);
        fakeCatalog.addProduct(rice, 2.99);
        fakeCatalog.addProduct(apples, 1.99);
        fakeCatalog.addProduct(cherryTomatoes, 0.69);
    }

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
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, fakeCatalog.getUnitPrice(toothbrush));
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void buy_two_get_one_free_but_insufficient_in_basket() {
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, fakeCatalog.getUnitPrice(toothbrush));
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
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, fakeCatalog.getUnitPrice(toothbrush));
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
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, rice, 10.0);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void xForY_discount() {
        theCart.addItem(cherryTomatoes);
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void xForY_discount_with_insufficient_in_basket() {
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount() {
        theCart.addItemQuantity(apples, 5);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples,6.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withSix() {
        theCart.addItemQuantity(apples, 6);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples,5.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withSixteen() {
        theCart.addItemQuantity(apples, 16);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples,7.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }

    @Test
    void FiveForY_discount_withFour() {
        theCart.addItemQuantity(apples, 4);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples,8.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(receipt.print());
    }
}
