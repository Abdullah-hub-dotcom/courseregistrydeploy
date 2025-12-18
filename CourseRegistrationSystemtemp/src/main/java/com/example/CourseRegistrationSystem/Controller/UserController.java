package com.example.CourseRegistrationSystem.Controller;

import com.example.CourseRegistrationSystem.Model.Courseregistry;
import com.example.CourseRegistrationSystem.Model.Model;
import com.example.CourseRegistrationSystem.Service.CourseService;
import com.example.CourseRegistrationSystem.Service.BrevoEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "https://courseregistry.netlify.app")
public class UserController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private BrevoEmailService emailService;
    @GetMapping("/courses")
    public List<Model> availableCourse() {
        return courseService.availableCourse();
    }

    @PostMapping("/registercourse")
    public String registerCourse(@RequestParam String name,
                                 @RequestParam String emailid,
                                 @RequestParam String courseName) {
        courseService.registercourses(name, emailid, courseName);
        emailService.sendCourseRegistrationEmail(emailid, name, courseName);
        return "Successfully registered for course " + courseName;
    }
}


