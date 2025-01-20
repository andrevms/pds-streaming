package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.framework.media.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/description")
    public ResponseEntity<?> searchByDescription(@RequestParam(name = "description") String description) {

        if (description == null || description.isEmpty()) {
            return ResponseEntity.badRequest().body("Description can't be empty");
        }

        return ResponseEntity.ok().body(searchService.searchByDescription(description));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> searchByCategory(@RequestParam(name = "category") String category) {

        if (category == null || category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category can't be empty");
        }

        return ResponseEntity.ok().body(searchService.searchByCategory(category));
    }

//    @GetMapping(value = "/random-content/movies")
//    public ResponseEntity<?> getRandomMovies(@RequestParam(name = "count", defaultValue = "1") int count) {
//        return ResponseEntity.ok().body(searchService.getRandomMovies(count));
//    }
//
//    @GetMapping(value = "/random-content/tvShows")
//    public ResponseEntity<?> getRandomTvShows(@RequestParam(name = "count", defaultValue = "1") int count) {
//        return ResponseEntity.ok().body(searchService.getRandomTvShows(count));
//    }
}
