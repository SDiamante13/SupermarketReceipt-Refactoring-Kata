package dojo.supermarket.model;

public interface SupermarketCatalog {
    void addProduct(Product product, double price);
    double getUnitPrice(Product product);
    double getTotalPrice(Product product, double quantity);
}
