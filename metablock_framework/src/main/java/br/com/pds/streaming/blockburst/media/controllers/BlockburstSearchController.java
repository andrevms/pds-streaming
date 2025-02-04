package br.com.pds.streaming.blockburst.media.controllers;

import br.com.pds.streaming.blockburst.media.services.BlockburstSearchService;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blockburst/search")
public class BlockburstSearchController {

    @Autowired
    private BlockburstSearchService blockburstSearchService;

    @GetMapping("/{keyWord}")
    public ResponseEntity<List<? extends MediaDTO>> search(@PathVariable String keyWord) {
        return new ResponseEntity<>(blockburstSearchService.search(keyWord), HttpStatus.OK);
    }
}
