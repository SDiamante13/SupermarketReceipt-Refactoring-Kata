package dojo.supermarket.model;

import java.util.Objects;

public class Product {
    private final String name;
    private final ProductUnitType unit;
    private double price;

    public Product(String name, ProductUnitType unit, double price) {
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public String getName() {
        return name;
    }


    public ProductUnitType getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                unit == product.unit;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, unit);
    }
}
