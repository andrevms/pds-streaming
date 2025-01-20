package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.HistoryNode;
import br.com.pds.streaming.framework.media.services.HistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class HistoryNodeController {

    private HistoryNodeService historyNodeService;

    public HistoryNodeController(HistoryNodeService historyNodeService) {
        this.historyNodeService = historyNodeService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryNode>> getAllHistoryNodes() {
        return new ResponseEntity<>(historyNodeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<HistoryNodeDTO>> getHistoryNodesByHistoryId(@RequestParam(name = "historyId") String historyId) {
        return new ResponseEntity<>(historyNodeService.findByHistoryId(historyId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryNode> getHistoryNodeById(@PathVariable String id) {
        return new ResponseEntity<>(historyNodeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryNodeDTO> insert(@RequestBody HistoryNodeDTO historyNodeDTO, @RequestParam(name = "historyId") String historyId, @RequestParam(name = "mediaId", required = false) String mediaId) {
        return new ResponseEntity<>(historyNodeService.insert(mediaId, historyNodeDTO, historyId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoryNodeDTO> updateHistoryNode(@RequestBody HistoryNodeDTO historyNodeDTO, @PathVariable String id) {
        return new ResponseEntity<>(historyNodeService.update(historyNodeDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HistoryNodeDTO> patchHistoryNode(@RequestBody HistoryNodeDTO historyNodeDTO, @PathVariable String id) {
        return new ResponseEntity<>(historyNodeService.patch(historyNodeDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoryNode(@PathVariable String id) {
        historyNodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
