package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.ModuleDTO;
import br.com.pds.streaming.yulearn.media.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    private ResponseEntity<List<ModuleDTO>> getModules() {
        return new ResponseEntity<>(moduleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable String id) {
        return new ResponseEntity<>(moduleService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO moduleDTO, @RequestParam(name = "courseId") String courseId) {
        return new ResponseEntity<>(moduleService.insert(moduleDTO, courseId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> updateModule(@RequestBody ModuleDTO moduleDTO, @PathVariable String id) {
        return new ResponseEntity<>(moduleService.update(moduleDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModuleDTO> patchModule(@RequestBody ModuleDTO moduleDTO, @PathVariable String id) {
        return new ResponseEntity<>(moduleService.patch(moduleDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable String id) {
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
