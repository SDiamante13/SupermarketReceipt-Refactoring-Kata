package dojo.supermarket;

import dojo.supermarket.model.*;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    Product toothbrush = new Product("toothbrush", ProductUnitType.EACH);
    Product apples = new Product("apples", ProductUnitType.KILO);
    Receipt receipt = new Receipt();

    @Test
    void oneLineItem() {
        receipt.addProduct(toothbrush, 1, 0.99, 0.99);
        Approvals.verify(receipt.print());
    }

    @Test
    void quantityTwo() {
        receipt.addProduct(toothbrush, 2, 0.99,0.99 * 2);
        Approvals.verify(receipt.print());
    }

    @Test
    void looseWeight() {
        receipt.addProduct(apples, 2.3, 1.99,1.99 * 2.3);
        Approvals.verify(receipt.print());
    }

    @Test
    void total() {

        receipt.addProduct(toothbrush, 1, 0.99, 2*0.99);
        receipt.addProduct(apples, 0.75, 1.99, 1.99*0.75);
        Approvals.verify(receipt.print());
    }

    @Test
    void discounts() {
        receipt.addDiscount(new Discount(apples, "3 for 2", -0.99));
        Approvals.verify(receipt.print());
    }

    @Test
    void printWholeReceipt() {
        receipt.addProduct(toothbrush, 1, 0.99, 0.99);
        receipt.addProduct(toothbrush, 2, 0.99, 2*0.99);
        receipt.addProduct(apples, 0.75, 1.99, 1.99*0.75);
        receipt.addDiscount(new Discount(toothbrush, "3 for 2", -0.99));
        Approvals.verify(receipt.print());
    }

}
