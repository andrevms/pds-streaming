package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.yulearn.media.services.YulearnSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/yulearn/search")
public class YulearnSearchController {

    @Autowired
    private YulearnSearchService yulearnSearchService;

    @GetMapping("/{keyWord}")
    public ResponseEntity<List<? extends MediaDTO>> search(@PathVariable String keyWord) {
        return new ResponseEntity<>(yulearnSearchService.search(keyWord), HttpStatus.OK);
    }
}
