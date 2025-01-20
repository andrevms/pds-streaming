package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.services.WatchLaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/watchlater", "/api/watch-later", "/api/watch_later"})
public class WatchLaterController {

    @Autowired
    private WatchLaterService watchLaterService;

    @GetMapping
    public ResponseEntity<List<? extends MediaDTO>> getWatchLaterListByUserId(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(watchLaterService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addMediaToWatchLater(@RequestParam(name = "mediaId") String mediaId, @RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(watchLaterService.insert(mediaId, userId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeMediaFromWatchLater(@RequestParam(name = "mediaId") String mediaId, @RequestParam(name = "userId") String userId) {
        watchLaterService.delete(mediaId, userId);
        return ResponseEntity.noContent().build();
    }
}
