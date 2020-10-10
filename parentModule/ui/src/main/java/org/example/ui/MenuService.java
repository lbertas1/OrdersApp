package org.example.ui;

import lombok.RequiredArgsConstructor;
import org.example.service.OrdersService;
import org.example.ui.data.UserData;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class MenuService {
    private final OrdersService ordersService;

    private int chooseOption() {
        System.out.println("Menu:");
        System.out.println("1. Show client and his products");
        System.out.println("2. Client who bought the most");
        System.out.println("3. Get Client who paid the most");
        System.out.println("4. Get product sales statistics");
        System.out.println("5. Get categories sorted by frequency selection");
        System.out.println("0. End of app");
        return UserData.getInt("Choose option:");
    }

    public void menu() {

        int option;
        do {
            option = chooseOption();
            switch (option) {
                case 1 -> option1();
                case 2 -> option2();
                case 3 -> option3();
                case 4 -> option4();
                case 5 -> option5();
                case 0 -> {
                    System.out.println("Have a nice day!");
                    return;
                }
                default -> System.out.println("No such option");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private void option1() {
        ordersService.print();
    }

    private void option2() {
        System.out.println(ordersService.getClientWhoBoughtTheMostProducts());
    }

    private void option3() {
        System.out.println(ordersService.getClientWhoBoughtProductsWithTheMostSumOfPrices());
    }

    private void option4() {
        ordersService.getProductSalesStatistics();
    }

    private void option5() {
        System.out.println(ordersService.getCategoriesNameSortedByFrequencySelection());
    }
}
