package com.example.CourseRegistrationSystem.Controller;

import com.example.CourseRegistrationSystem.Model.Courseregistry;
import com.example.CourseRegistrationSystem.Model.Model;
import com.example.CourseRegistrationSystem.Service.CourseService;
import com.example.CourseRegistrationSystem.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "https://courseregistry.netlify.app")
public class Controller {

    private final CourseService courseService;
    private final EmailSenderService emailService;

    @Autowired
    public Controller(CourseService courseService, EmailSenderService emailService) {
        this.courseService = courseService;
        this.emailService = emailService;
    }

    @GetMapping("/courses")
    public List<Model> availableCourse() {
        return courseService.availableCourse();
    }

    @GetMapping("/enrolledcourses")
    public List<Courseregistry> enrolledCourses() {
        return courseService.enrolledcourses();
    }

    @PostMapping("/registercourse")
    public String registerCourse(
            @RequestParam("name") String name,
            @RequestParam("emailid") String emailid,
            @RequestParam("CourseName") String courseName) {

        courseService.registercourses(name, emailid, courseName);

        emailService.sendCourseRegistrationEmail(emailid, name, courseName);

        return "🎉 Congratulations " + name +
                "! You have successfully enrolled for " + courseName +
                ". A confirmation email has been sent to " + emailid;
    }
}

//    @PostMapping("/registercourse")
//    public String registerCourse(
//            @RequestParam("name") String name,
//            @RequestParam("emailid") String emailid,
//            @RequestParam("CourseName") String courseName) {
//
//        courseService.registercourses(name, emailid, courseName);
//        return "🎉 Congratulations " + name +
//                "! You have successfully enrolled for " + courseName;
//    }
//@PostMapping("/registercourse")
//public String registerCourse(@RequestBody Courseregistry course) {
//    courseService.registercourses(
//            course.getName(),
//            course.getEmailid(),
//            course.getCourseName()
//    );
//    return "🎉 Congratulations " + course.getName() +
//            "! You have successfully enrolled for " + course.getCourseName();
//}

