package com.example.CourseRegistrationSystem.Controller;

import com.example.CourseRegistrationSystem.Model.Courseregistry;
import com.example.CourseRegistrationSystem.Model.Model;
import com.example.CourseRegistrationSystem.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(origins = "https://courseregistry.netlify.app")
public class AdminController {

    @Autowired
    private CourseService courseService;
    @GetMapping("/courses")
    public List<Model> getCourses() {
        return courseService.availableCourse();
    }

    @PostMapping("/addcourse")
    public String addCourse(@RequestBody Model course) {
        courseService.addCourse(course);
        return "Course added successfully!";
    }

    @DeleteMapping("/deletecourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return "Course deleted successfully!";
    }

    @GetMapping("/enrolled")
    public List<Courseregistry> viewEnrolledStudents() {
        return courseService.enrolledcourses();
    }
}
