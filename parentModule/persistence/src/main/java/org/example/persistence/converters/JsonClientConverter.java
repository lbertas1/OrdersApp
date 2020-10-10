package org.example.persistence.converters;

import org.example.persistence.model.Client;

import java.util.List;

public class JsonClientConverter extends JsonConverter<List<Client>>{
    public JsonClientConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
