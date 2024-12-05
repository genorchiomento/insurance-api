package io.github.genorchiomento.insuranceapi.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "insurances")
public record Insurance(
        @Id String id,
        String cpf,
        String insuranceType,
        String contractDate
) {
}