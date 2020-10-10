package org.example.ui;

import org.example.service.OrdersService;
import org.example.ui.controller.Controller;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class App {
    public static void main(String[] args) {

//        final String FILENAME_PATH_CLIENTS = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/klienci.txt";
//        final String FILENAME_PATH2_PREFERENCES = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/preferencje.txt";
//        final String FILENAME_PATH3_PRODUCTS = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/produkty.txt";
        final String FILENAME_PATH4_CLIENTS_JSON = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/clients.json";
        final String FILENAME_PATH4_PRODUCTS_JSON = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/products.json";
        final String FILENAME_PATH4_PREFERENCES_JSON = "C:/IntelijProjects/Z10_T03_MultimoduleApp/parentModule/org.example.ui/src/main/resources/preferences.json";
        OrdersService ordersService = new OrdersService(FILENAME_PATH4_CLIENTS_JSON, FILENAME_PATH4_PRODUCTS_JSON, FILENAME_PATH4_PREFERENCES_JSON);
        MenuService menuService = new MenuService(ordersService);
        menuService.menu();

        port(8090);
        initExceptionHandler(e -> System.out.println(e.getMessage()));

        Controller controller = new Controller(ordersService);
        controller.initRoutes();
    }
}
