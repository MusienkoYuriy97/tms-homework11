package my_shop.data;

import my_shop.exceptions.IdAlreadyExistsException;
import my_shop.exceptions.IdNotExistException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Product> products;

    public Shop() {
        products = readProducts();
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) throws IdAlreadyExistsException{
        if (products.contains(product)){
            throw new IdAlreadyExistsException("Причина - Product с таким id уже существует");
        }
        products.add(product);
    }

    public void removeProduct(int id) throws IdNotExistException{
        if (products.contains(new Product(id))){
            products.remove(new Product(id));
        }else {
            throw new IdNotExistException("Причина - Product с таким id не существует");
        }
    }


    public void setProductValues(Product product) throws IdNotExistException {
        int index = products.indexOf(product);

        if (index != -1){
            products.set(index,product);
        }else {
            throw new IdNotExistException("Причина - Product с таким id не существует");
        }
    }

    public void writeProducts(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("product.txt"))){
            outputStream.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Product> readProducts(){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("product.txt"))){
            List<Product> list = (List<Product>) objectInputStream.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<Product>();
        }
    }
}
