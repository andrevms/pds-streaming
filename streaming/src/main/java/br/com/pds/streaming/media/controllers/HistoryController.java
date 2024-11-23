package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.media.model.dto.HistoryDTO;
import br.com.pds.streaming.media.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/history", "/api/histories"})
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getAllHistories() {
        return new ResponseEntity<>(historyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<HistoryDTO>> getHistoriesByUserId(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(historyService.findByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable String id) throws ObjectNotFoundException {
        return new ResponseEntity<>(historyService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO, @RequestParam(name = "userId") String userId) throws ObjectNotFoundException {
        return new ResponseEntity<>(historyService.insert(historyDTO, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable String id) {
        historyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
