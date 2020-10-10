package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.Client;
import org.example.persistence.model.Product;
import org.example.service.extensions.ExtensionForOrders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ExtensionForOrders.class)
@RequiredArgsConstructor
class OrdersServiceTest {

    private final OrdersService ordersService;

    @Test
    public void getOrders() {
        assertNotNull(ordersService.getClientProductsMap());
    }

    @Test
    public void getClientWhoBoughtTheMostProducts() {
        Client clientWhoBoughtTheMostProducts = ordersService.getClientWhoBoughtTheMostProducts();

        Client expectedClient = ordersService
                .getClientProductsMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        clientListEntry -> clientListEntry.getValue().size()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        assertEquals(expectedClient, clientWhoBoughtTheMostProducts);
    }

    @Test
    public void getClientWhoBoughtProductsWithTheMostSumOfPrices() {
        Client clientWhoBoughtProductsWithTheMostSumOfPrices = ordersService.getClientWhoBoughtProductsWithTheMostSumOfPrices();

        Client expectedClient = ordersService
                .getClientProductsMap()
                .entrySet()
                .stream()
                .map(clientListEntry -> {
                    double sum = clientListEntry.getValue()
                            .stream()
                            .mapToDouble(Product::getPrice)
                            .sum();

                    return Map.of(clientListEntry.getKey(), sum);
                })
                .flatMap(clientDoubleMap -> clientDoubleMap.entrySet().stream())
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        assertEquals(expectedClient, clientWhoBoughtProductsWithTheMostSumOfPrices);
    }

    @Test
    public void getProductSalesStatistics() {
        Map<Product, Long> productSalesStatistics = ordersService.getProductSalesStatistics();

        productSalesStatistics
                .keySet()
                .forEach(product -> {
                    long count = ordersService
                            .getClientProductsMap()
                            .entrySet()
                            .stream()
                            .flatMap(clientListEntry -> clientListEntry.getValue().stream())
                            .filter(product1 -> product1.equals(product))
                            .count();

                    assertEquals(count, productSalesStatistics.get(product));
                });
    }

    @Test
    public void productMostOftenAndLeastBought() {
        Product productMostOftenBought = ordersService.productMostOftenBought();
        Product productLeastBought = ordersService.productLeastBought();

        List<Product> productsList = ordersService
                .getClientProductsMap()
                .entrySet()
                .stream()
                .flatMap(clientListEntry -> clientListEntry.getValue().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertAll(() -> {
            assertEquals(productsList.get(0), productLeastBought);
            assertEquals(productsList.get(productsList.size() - 1), productMostOftenBought);
        });
    }

    @Test
    public void getCategoriesNameSortedByFrequencySelection() {
        List<String> categoriesNameSortedByFrequencySelection = ordersService.getCategoriesNameSortedByFrequencySelection();

        assertEquals(ordersService.getCategories().size(), categoriesNameSortedByFrequencySelection.size());
    }
}