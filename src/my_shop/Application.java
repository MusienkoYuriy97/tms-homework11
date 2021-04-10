package my_shop;

import my_shop.data.Product;
import my_shop.data.Shop;
import my_shop.exceptions.IdAlreadyExistsException;
import my_shop.exceptions.IdNotExistException;
import my_shop.exceptions.ListIsEmptyException;
import my_shop.sorts.ComparatorAscendingByPrice;
import my_shop.sorts.ComparatorByDate;
import my_shop.sorts.ComparatorByDefault;
import my_shop.sorts.ComparatorDescendingByPrice;
import java.util.*;

public class Application {
    private final Shop SHOP = new Shop();
    private boolean isStarted = true;

    //метод запуска из main
    public void start(){
        System.out.println("Вас приветствует приложение Магазин");
        run();
        System.out.println("Магазин закончил свою работу");
    }

    //метод работы магазина
    private void run(){
        while (isStarted){
            showMenu();
            selectOperation();
        }
        SHOP.writeProducts();
    }

    //Выбор операции
    private void selectOperation(){
        try {
            Scanner scanInt = new Scanner(System.in);
            System.out.print("Выбери номер операции: ");
            int chOperation = scanInt.nextInt();
            System.out.println();
            switch (chOperation) {
                case 1:
                    getAllProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    removeProduct();
                    break;
                case 4:
                    setProductValues();
                    break;
                case 0:
                    isStarted = false;
                    break;
                default:
                    System.out.println("Нет операции с таким номером");
                    selectOperation();
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            selectOperation();
        }catch (ListIsEmptyException e){
            System.out.println(e.getMessage());
        }
    }

    //Вывод товаров
    private void getAllProducts() throws ListIsEmptyException{
        if (SHOP.getAllProducts().isEmpty()){
            throw  new ListIsEmptyException("Список пуст, добавь товар");
        }
        Comparator<Product> comparator = selectSort();
        Collections.sort(SHOP.getAllProducts(),comparator);
        System.out.println(SHOP.getAllProducts());
    }

    //выбор сортировки
    private Comparator<Product> selectSort(){
        try {
            Scanner scanInt = new Scanner(System.in);
            showMenuSorts();
            int x = scanInt.nextInt();
            switch (x){
                case 1:
                    return new ComparatorAscendingByPrice();
                case 2:
                    return new ComparatorDescendingByPrice();
                case 3:
                    return new ComparatorByDate();
                default:
                    System.out.println("Сортировки с таким номером не существует");
                    return selectSort();
            }
        }catch (InputMismatchException e){
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            if (restartOperation()){
                selectSort();
            }
            System.out.println("Последний раз список выглядел так: ");
            return new ComparatorByDefault();
        }
    }

    //добавление товара
    private void addProduct(){
        try {
            Scanner scanInt = new Scanner(System.in);
            Scanner scanStr = new Scanner(System.in);
            System.out.println("Ввод характеристик товара");
            System.out.print("id товара(только числа): ");
            int id = scanInt.nextInt();
            System.out.print("название товара: ");
            String name = scanStr.nextLine();
            System.out.print("цена товара в $: ");
            int price = scanInt.nextInt();
            SHOP.addProduct(new Product(id, name, price));

            System.out.println("Товар успешно добавлен!");
        } catch (IdAlreadyExistsException | InputMismatchException e) {
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            if (restartOperation()){
                addProduct();
            }
        }
    }

    //удаление товара по id
    private void removeProduct(){
        try {
            if (SHOP.getAllProducts().isEmpty()){
                throw new ListIsEmptyException("Список пуст, добавь товар");
            }
            Scanner scanInt = new Scanner(System.in);
            System.out.print("Введи id товара(только числа) который хочешь удалить: ");
            int id = scanInt.nextInt();
            SHOP.removeProduct(id);

            System.out.println("Товар успешно удалён!");
        } catch (IdNotExistException | InputMismatchException e) {
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            if (restartOperation()){
                removeProduct();
            }
        }catch (ListIsEmptyException e){
            System.out.println(e.getMessage());
        }
    }

    //изменение характеристик товара
    private void setProductValues(){
        try {
            if (SHOP.getAllProducts().isEmpty()){
                throw new ListIsEmptyException("Список пуст, добавь товар");
            }
            Scanner scanInt = new Scanner(System.in);
            Scanner scanStr = new Scanner(System.in);
            System.out.println("Ввод характеристик товара");
            System.out.print("id товара(только числа): ");
            int id = scanInt.nextInt();
            System.out.print("название товара: ");
            String name = scanStr.nextLine();
            System.out.print("цена товара в $: ");
            int price = scanInt.nextInt();
            SHOP.setProductValues(new Product(id,name,price));

            System.out.println("Товар успешно изменён!");
        } catch (IdNotExistException | InputMismatchException e) {
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            if (restartOperation()){
                setProductValues();
            }
        }catch (ListIsEmptyException e){
            System.out.println(e.getMessage());
        }
    }

    //основное меню
    private void showMenu(){
        System.out.println();
        System.out.println("Меню:");
        System.out.println("1 - Вывод всех товаров");
        System.out.println("2 - Добавление товара");
        System.out.println("3 - Удаление товара");
        System.out.println("4 - Редактирование товара");
        System.out.println("0 - Выход из приложения");

    }

    //меню сортировок
    private void showMenuSorts(){
        System.out.println("Выбери в каком виде ты хочешь видеть список товаров:");
        System.out.println("1 - Отсортированы по возрастанию цены");
        System.out.println("2 - Отсортированы по убыванию цены");
        System.out.println("3 - Отсортированы по дате добавления(от новых к старым)");
    }

    //метод повторного ввода, при неверных данных
    private boolean restartOperation(){
        try {
            Scanner scanInt = new Scanner(System.in);
            System.out.println("Желаете повторить ввод?\n1 - ДА : Дугое число - НЕТ");
            int chose = scanInt.nextInt();
            if (chose == 1){
                return true;
            }
        }catch (InputMismatchException e){
            System.out.println("Ошибка ввода - message {" + e.getMessage() + "} "+ e.getClass());
            restartOperation();
        }
        return false;
    }
}
