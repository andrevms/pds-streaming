package br.com.pds.streaming.domain.registration.controllers;

import br.com.pds.streaming.domain.registration.model.dto.BusinessUserDTO;
import br.com.pds.streaming.domain.registration.services.BusinessUserServices;
import br.com.pds.streaming.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/business-users")
public class BusinessUserController {

    @Autowired
    private BusinessUserServices businessUserServices;

    @GetMapping
    public ResponseEntity<List<BusinessUserDTO>> getAllBusinessUsers() {
        List<BusinessUserDTO> users = businessUserServices.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessUserDTO> getBusinessUserById(@PathVariable String id) throws UserNotFoundException {
        BusinessUserDTO user = businessUserServices.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<BusinessUserDTO> create(@RequestBody BusinessUserDTO businessUserDTO) {
        BusinessUserDTO createdUser = businessUserServices.create(businessUserDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessUserDTO> updateBusinessUser(@PathVariable String id, @RequestBody BusinessUserDTO businessUserDTO) throws UserNotFoundException {
        BusinessUserDTO updatedUser = businessUserServices.update(businessUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws UserNotFoundException {
        businessUserServices.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
