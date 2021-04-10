package my_shop.sorts;

import my_shop.data.Product;

import java.util.Comparator;

public class ComparatorDescendingByPrice implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getPrice()-o1.getPrice();
    }
}
