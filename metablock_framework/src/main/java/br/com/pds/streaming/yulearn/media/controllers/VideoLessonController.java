package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonRequest;
import br.com.pds.streaming.yulearn.media.model.dto.VideoLessonResponse;
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
    public ResponseEntity<List<VideoLessonResponse>> getAllvideoLessons() {
        return new ResponseEntity<>(videoLessonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoLessonResponse> getvideoLessonById(@PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VideoLessonResponse> createvideoLesson(@RequestBody VideoLessonRequest videoLessonRequest, @RequestParam(name = "moduleId") String moduleId) {
        return new ResponseEntity<>(videoLessonService.insert(videoLessonRequest, moduleId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoLessonResponse> updatevideoLesson(@RequestBody VideoLessonRequest videoLessonRequest, @PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.update(videoLessonRequest, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VideoLessonResponse> patchvideoLesson(@RequestBody VideoLessonRequest videoLessonRequest, @PathVariable String id) {
        return new ResponseEntity<>(videoLessonService.patch(videoLessonRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletevideoLesson(@PathVariable String id) {
        videoLessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
