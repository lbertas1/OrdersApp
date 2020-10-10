package org.example.service;

import org.example.persistence.model.Client;
import org.example.persistence.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrepareMap {

    private final List<Client> clients;
    private final List<Product> products;

    public PrepareMap(List<Client> clients, List<Product> products) {
        this.clients = clients;
        this.products = products;
    }

    public Map<Client, List<Product>> createMap() {
        return clients
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        client -> {
                            List<Product> clientProducts = new ArrayList<>();
                            Double balance = client.getCash();
                            client.getPreferences().forEach(integer -> clientProducts.addAll(buyProducts(clientProducts, balance, integer)));
                            return clientProducts;
                        }
                ));
    }

    private Product calculatePriceQuantityRatio(int preference, Double balance) {
        AtomicReference<Product> productAtomicReference = new AtomicReference<>();
        products
                .stream()
                .filter(product -> product.getCategory().equals(preference) && product.getPrice() <= balance)
                .reduce((product, product2) -> {
                    double ratio1 = product.getPrice() / product.getQuantity();
                    double ratio2 = product2.getPrice() / product2.getQuantity();
                    return ratio1 > ratio2 ? product : product2;
                })
                .ifPresent(productAtomicReference::set);
        return productAtomicReference.get();
    }

    private List<Product> buyProducts(List<Product> products, Double clientBalance, int preference) {
        double balance = clientBalance - products.stream().mapToDouble(Product::getPrice).sum();
        List<Product> tmpListProducts = new ArrayList<>();
        Product product;
        if (balance > 0) {

             do {
                product = calculatePriceQuantityRatio(preference, balance);
                if (product == null) break;
                if (balance >= product.getPrice()) {
                    tmpListProducts.add(product);
                    balance -= product.getPrice();
                }
            } while (balance >= product.getPrice());
        }
        return tmpListProducts;
    }
}
