package org.example.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    private String name;
    private String surname;
    private Integer age;
    private Double cash;
    private List<Integer> preferences;
}
