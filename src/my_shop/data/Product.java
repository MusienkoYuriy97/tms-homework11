package my_shop.data;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private static int count = 0;
    private final int indexByDateAdd = count;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        count++;
    }

    public Product(int id) {
        this.id = id;
    }

    public Product(Product product) {
        count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIndexByDateAdd() {
        return indexByDateAdd;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", товар='" + name + '\'' +
                ", цена=" + price +
                "$}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}


