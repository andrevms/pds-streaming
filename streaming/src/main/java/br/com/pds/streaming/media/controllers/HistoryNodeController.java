package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.services.HistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/historynode", "/api/historynodes", "/api/history-node", "/api/history-nodes", "/api/history_node", "/api/history_nodes"})
public class HistoryNodeController {

    @Autowired
    private HistoryNodeService historyNodeService;

    @GetMapping
    public ResponseEntity<List<HistoryNodeDTO>> getAllHistoryNodes() {
        return new ResponseEntity<>(historyNodeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<HistoryNodeDTO>> getHistoryNodesByHistoryId(@RequestParam(name = "historyId") String historyId) {
        return new ResponseEntity<>(historyNodeService.findByHistoryId(historyId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryNodeDTO> getHistoryNodeById(@PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(historyNodeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HistoryNodeDTO> insert(@RequestBody HistoryNodeDTO historyNodeDTO, @RequestParam(name = "historyId") String historyId, @RequestParam(name = "episodeId", required = false) String episodeId, @RequestParam(name = "movieId", required = false) String movieId) throws EntityNotFoundException {

        if (episodeId != null) {
            return new ResponseEntity<>(historyNodeService.insert(episodeId, historyNodeDTO, historyId), HttpStatus.OK);
        }

        if (movieId != null) {
            return new ResponseEntity<>(historyNodeService.insert(historyNodeDTO, movieId, historyId), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
