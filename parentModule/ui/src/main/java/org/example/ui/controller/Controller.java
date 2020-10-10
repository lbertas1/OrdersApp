package org.example.ui.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.JsonTransformer;
import org.example.service.OrdersService;

import static spark.Spark.*;

@RequiredArgsConstructor
public class Controller {

    private final OrdersService orders;

    public void initRoutes() {

        path("/get-orders", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                orders.print();
                return null;
            }), new JsonTransformer());
        });

        path("/client-who-bought-most", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                orders.getClientWhoBoughtTheMostProducts();
                return null;
            }), new JsonTransformer());
        });

        path("/client-who-paid-most", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                return orders.getClientWhoBoughtProductsWithTheMostSumOfPrices();
            }), new JsonTransformer());
        });

        path("/statistics", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                orders.getProductSalesStatistics();
                return null;
            }), new JsonTransformer());
        });

        path("/categories-sorted-by-frequency-selection", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                response.status(200);
                return orders.getCategoriesNameSortedByFrequencySelection();
            }), new JsonTransformer());
        });
    }
}
