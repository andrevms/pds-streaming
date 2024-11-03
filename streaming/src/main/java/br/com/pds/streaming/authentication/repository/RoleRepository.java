package br.com.pds.streaming.authentication.repository;

import br.com.pds.streaming.authentication.model.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    boolean existsRoleByName(String roleName);
    Optional<Role> findByName(String string);
}
