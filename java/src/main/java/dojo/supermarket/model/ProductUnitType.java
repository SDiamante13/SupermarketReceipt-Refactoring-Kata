package dojo.supermarket.model;

public enum ProductUnitType {
    KILO, EACH;

    public boolean isEach() {
        return EACH.equals(this);
    }
}
