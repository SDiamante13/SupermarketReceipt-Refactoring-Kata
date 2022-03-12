package dojo.supermarket.model;

public enum SpecialOfferType {
    THREE_FOR_TWO {
        @Override
        double discountAmount(Double quantity, double unitPrice, int quantityAsInt, double offerAmount) {
            return -(quantity * unitPrice - (((quantityAsInt / 3) * 2 * unitPrice) + quantityAsInt % 3 * unitPrice));
        }

        @Override
        String description(double offerAmount) {
            return "3 for 2";
        }

        @Override
        boolean isEligible(int quantity) {
            return quantity >= 3;
        }
    },
    TEN_PERCENT_DISCOUNT {
        @Override
        double discountAmount(Double quantity, double unitPrice, int quantityAsInt, double offerAmount) {
            return -(quantity * unitPrice * offerAmount / 100.0);
        }

        @Override
        String description(double offerAmount) {
            return offerAmount + "% off";
        }

        @Override
        boolean isEligible(int quantity) {
            return true;
        }
    },
    TWO_FOR_AMOUNT {
        @Override
        double discountAmount(Double quantity, double unitPrice, int quantityAsInt, double offerAmount) {
            double pricePerUnit = offerAmount * (quantity / 2);
            double total = pricePerUnit + (quantity % 2) * unitPrice;
            return -(unitPrice * quantity - total);
        }

        @Override
        String description(double offerAmount) {
            return "2 for " + offerAmount;
        }

        @Override
        boolean isEligible(int quantity) {
            return quantity >= 2;
        }
    },
    FIVE_FOR_AMOUNT {
        @Override
        double discountAmount(Double quantity, double unitPrice, int quantityAsInt, double offerAmount) {
            double discountN = unitPrice * quantity - (offerAmount * (quantityAsInt / 5) + quantityAsInt % 5 * unitPrice);
            return -discountN;
        }

        @Override
        String description(double offerAmount) {
            return 5 + " for " + offerAmount;
        }

        @Override
        boolean isEligible(int quantity) {
            return quantity >= 5;
        }
    };

    abstract double discountAmount(Double quantity, double unitPrice, int quantityAsInt, double offerAmount);
    abstract String description(double offerAmount);
    abstract boolean isEligible(int quantity);
}
