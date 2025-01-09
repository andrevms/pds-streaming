package br.com.pds.streaming.framework.authentication.controllers;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO businessUserDTO, @PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.update(businessUserDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
