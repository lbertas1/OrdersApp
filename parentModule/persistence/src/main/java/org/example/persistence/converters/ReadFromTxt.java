package org.example.persistence.converters;

import org.example.persistence.model.Client;
import org.example.persistence.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ReadFromTxt {

    private Map<Client, List<Product>> clientProductsMap;
    private List<Product> products;
    private List<Client> clients;

    public ReadFromTxt(String clientsFile, String preferencesFile, String productsFile) {
        products = createProductList(productsFile);
        clients = createClientList(clientsFile);
        this.clientProductsMap = new LinkedHashMap<>();
        iterateByClients();
    }

    public Map<Client, List<Product>> getClientProductsMap() {
        return clientProductsMap;
    }

    private List<Product> createProductList(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .map(s -> {
                        String[] array = s.split(";");
                        System.out.println(Arrays.toString(array));
                        return new Product(
                                        array[0],
                                        Integer.parseInt(array[1]),
                                        Double.parseDouble(array[2]),
                                        Integer.parseInt(array[3]));
                            }
                    )
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Client> createClientList(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .map(s -> {
                        List<Integer> preferences = new ArrayList<>();
                        preferences.add((int) s.split(";")[4].charAt(0));
                        preferences.add((int) s.split(";")[4].charAt(1));
                        preferences.add((int) s.split(";")[4].charAt(2));

                        return new Client(
                                s.split(";")[0],
                                s.split(";")[1],
                                Integer.parseInt(s.split(";")[2]),
                                Double.parseDouble(s.split(";")[3]),
                                preferences);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void iterateByClients() {
        Map<Integer, List<Product>> allproductsFromClientPreferences = new LinkedHashMap<>();
        clients.forEach(client -> {
            allproductsFromClientPreferences.clear();
            List<Integer> preferences = client.getPreferences();
            Map<Integer, List<Product>> allCategoryMap = createCategoryMap();
            preferences.forEach(integer -> {
                allCategoryMap.forEach((integer1, products1) -> {
                    if (integer.equals(integer1)) {
                        allproductsFromClientPreferences.put(integer, products1);
                    }
                });
            });

            createMapWithClientAndHisProducts(client, allproductsFromClientPreferences);
        });
    }

    private Map<Integer, List<Product>> createCategoryMap() {
        return products.stream()
                .collect(Collectors.toMap(Product::getCategory, product -> products.stream()
                        .filter(product1 -> product1.getCategory().equals(product.getCategory()))
                        .collect(Collectors.toList()), (objects, objects2) -> objects, LinkedHashMap::new));
    }

    private void createMapWithClientAndHisProducts(Client client, Map<Integer, List<Product>> allproductsFromClientPreferences) {
        List<Product> clientProducts = new ArrayList<>();

        allproductsFromClientPreferences.forEach((integer, products1) -> {
            products1.forEach(product -> {
                if (client.getCash() > product.getPrice() * product.getQuantity()) {
                    clientProducts.add(product);
                    client.setCash(client.getCash() - (product.getPrice() * product.getQuantity()));
                }
            });
        });

        clientProductsMap.put(client, clientProducts);
    }

    public Map<Integer, String> createCategoriesMap(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .collect(Collectors.toMap(
                            s -> Integer.valueOf(s.split("_")[0]),
                            s -> s.split("_")[1]
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
