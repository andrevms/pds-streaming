package br.com.pds.streaming.media.controllers;

import br.com.pds.streaming.media.model.dto.SearchResultDTO;
import br.com.pds.streaming.media.services.SearchService;
import com.amazonaws.Response;
import io.micrometer.core.instrument.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam(name = "title") String title) {

        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body("Title can't be empty");
        }

        return ResponseEntity.ok().body(searchService.search(title));
    }
}
