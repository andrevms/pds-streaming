package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.yulearn.media.model.dto.CourseDTO;
import br.com.pds.streaming.yulearn.media.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable String id) {
        return new ResponseEntity<>(courseService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.insert(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO,@PathVariable String id) {
        return new ResponseEntity<>(courseService.update(courseDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseDTO> patchCourse(@RequestBody CourseDTO courseDTO, @PathVariable String id) {
        return new ResponseEntity<>(courseService.patch(courseDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
