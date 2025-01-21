package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonDTO;
import br.com.pds.streaming.yulearn.media.services.VideoLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/videolessons", "/api/video-lessons", "/api/video_lessons"})
public class VideoLessonController {
    
    @Autowired
    private VideoLessonService videoLessonService;

    @GetMapping
    public ResponseEntity<List<VideoLessonDTO>> getAllvideoLessons() {
        return new ResponseEntity<>(videoLessonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoLessonDTO> getvideoLessonById(@PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VideoLessonDTO> createvideoLesson(@RequestBody VideoLessonDTO videoLessonDTO, @RequestParam(name = "moduleId") String moduleId) {
        return new ResponseEntity<>(videoLessonService.insert(videoLessonDTO, moduleId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoLessonDTO> updatevideoLesson(@RequestBody VideoLessonDTO videoLessonDTO, @PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.update(videoLessonDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VideoLessonDTO> patchvideoLesson(@RequestBody VideoLessonDTO videoLessonDTO, @PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.patch(videoLessonDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletevideoLesson(@PathVariable String id) {
        videoLessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
