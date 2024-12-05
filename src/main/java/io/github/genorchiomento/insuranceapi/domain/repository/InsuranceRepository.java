package io.github.genorchiomento.insuranceapi.domain.repository;

import io.github.genorchiomento.insuranceapi.domain.model.Insurance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsuranceRepository extends MongoRepository<Insurance, String> {
}
