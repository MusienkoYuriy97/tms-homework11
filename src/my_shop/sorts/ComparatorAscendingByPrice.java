package my_shop.sorts;

import my_shop.data.Product;

import java.util.Comparator;

public class ComparatorAscendingByPrice implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getPrice()-o2.getPrice();
    }
}
