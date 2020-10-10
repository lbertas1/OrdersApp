package org.example.service;

import org.example.persistence.converters.JsonClientConverter;
import org.example.persistence.converters.JsonPreferencesConverter;
import org.example.persistence.converters.JsonProductConverter;
import org.example.persistence.model.Client;
import org.example.persistence.model.Preferences;
import org.example.persistence.model.Product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrdersService {

    private final Map<Client, List<Product>> clientProductsMap;
    private Map<Integer, String> categories;

    public OrdersService(String clientsFile, String productsFile, String preferencesFile) {
        clientProductsMap = init(clientsFile, productsFile, preferencesFile);
    }

    public Map<Client, List<Product>> init(String clientsFile, String productsFile, String preferencesFile) {
        JsonClientConverter jsonClientConverter = new JsonClientConverter(clientsFile);
        List<Client> clients = jsonClientConverter
                .fromJson()
                .orElseThrow();

        List<Product> products = new JsonProductConverter(productsFile)
                .fromJson()
                .orElseThrow();

        categories = new JsonPreferencesConverter(preferencesFile)
                .fromJson()
                .orElseThrow()
                .stream()
                .collect(Collectors.toMap(Preferences::getId, Preferences::getName));

        PrepareMap prepareMap = new PrepareMap(clients, products);

        return prepareMap.createMap();
    }

    public Map<Client, List<Product>> getClientProductsMap() {
        return clientProductsMap;
    }

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public void print() {
        clientProductsMap.forEach((client, products) -> {
            System.out.println("\nCLIENT: " + client);
            products.forEach(product -> System.out.println("\n\t" + product));
        });
    }

    public Client getClientWhoBoughtTheMostProducts() {
        return clientProductsMap
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(o -> o.getValue().size()))
                .orElseThrow()
                .getKey();
    }

    public Client getClientWhoBoughtProductsWithTheMostSumOfPrices() {
        return clientProductsMap.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(value -> value.getValue().stream().mapToDouble(Product::getPrice).sum())))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    public Map<Product, Long> getProductSalesStatistics() {
        return clientProductsMap
                .entrySet()
                .stream()
                .flatMap(clientListEntry -> clientListEntry.getValue().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    public Product productMostOftenBought() {
        return clientProductsMap
                .entrySet()
                .stream()
                .flatMap(clientListEntry -> clientListEntry.getValue().stream())
                .collect(Collectors.groupingBy(
                        product -> product,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    public Product productLeastBought() {
        return clientProductsMap
                .entrySet()
                .stream()
                .flatMap(clientListEntry -> clientListEntry.getValue().stream())
                .collect(Collectors.groupingBy(
                        product -> product,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .min(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    public List<String> getCategoriesNameSortedByFrequencySelection() {
        return clientProductsMap
                .entrySet()
                .stream()
                .flatMap(clientListEntry -> clientListEntry.getKey().getPreferences().stream())
                .map(integer -> categories
                        .entrySet()
                        .stream()
                        .filter(integerStringEntry -> integerStringEntry.getKey().equals(integer))
                        .findFirst()
                        .orElseThrow()
                        .getValue())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
