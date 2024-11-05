package br.com.pds.streaming.domain.registration.repositories;

import br.com.pds.streaming.domain.registration.model.entities.BusinessUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessUserRepository extends MongoRepository<BusinessUser, String> {
    BusinessUser findByUsername(String email);
}
