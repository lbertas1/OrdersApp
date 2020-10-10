package org.example.persistence.converters;

import org.example.persistence.model.Preferences;

import java.util.List;

public class JsonPreferencesConverter extends JsonConverter<List<Preferences>> {
    public JsonPreferencesConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
