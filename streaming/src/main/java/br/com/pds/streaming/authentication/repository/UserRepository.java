package br.com.pds.streaming.authentication.repository;

import br.com.pds.streaming.authentication.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String username);

}
