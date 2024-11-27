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

    @GetMapping(value = "/title")
    public ResponseEntity<?> searchByTitle(@RequestParam(name = "title") String title) {

        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body("Title can't be empty");
        }

        return ResponseEntity.ok().body(searchService.searchByTitle(title));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> searchByCategory(@RequestParam(name = "category") String category) {
        if (category == null || category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category can't be empty");
        }

        return ResponseEntity.ok().body(searchService.searchByCategory(category));
    }

}
