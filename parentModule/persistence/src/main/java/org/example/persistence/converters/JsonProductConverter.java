package org.example.persistence.converters;

import org.example.persistence.model.Product;

import java.util.List;

public class JsonProductConverter extends JsonConverter<List<Product>> {

    public JsonProductConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
