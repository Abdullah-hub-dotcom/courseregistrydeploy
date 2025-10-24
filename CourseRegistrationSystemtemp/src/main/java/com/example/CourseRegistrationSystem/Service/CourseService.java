package com.example.CourseRegistrationSystem.Service;

import com.example.CourseRegistrationSystem.Model.Courseregistry;
import com.example.CourseRegistrationSystem.Model.Model;
import com.example.CourseRegistrationSystem.Repository.courseRepository;
import com.example.CourseRegistrationSystem.Repository.courseregistryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private courseRepository courseRepository;


    @Autowired
    private courseregistryrepo courseregistryrepo;

    // ===================== User Methods =====================

    // Get all available courses
    public List<Model> availableCourse() {
        return courseRepository.findAll();
    }

    // Enroll a student in a course
    public void registercourses(String name, String emailid, String courseName) {
        Courseregistry registration = new Courseregistry(name, emailid, courseName);
        courseregistryrepo.save(registration);
    }

    // Get all enrolled courses
    public List<Courseregistry> enrolledcourses() {
        return courseregistryrepo.findAll();
    }

    // ===================== Admin Methods =====================

    // Add a new course
    public void addCourse(Model course) {
        courseRepository.save(course);
    }

    // Delete a course by ID
    public boolean deleteCourse(String courseId) {
        Optional<Model> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
            return true;
        }
        return false;
    }

    // Optional: Find a course by name (could be used for validation)
    public Optional<Model> findCourseByName(String courseName) {
        return courseRepository.findAll()
                .stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(courseName))
                .findFirst();
    }

    // Optional: Find enrolled students for a specific course
    public List<Courseregistry> getStudentsByCourse(String courseName) {
        return courseregistryrepo.findAll()
                .stream()
                .filter(e -> e.getCourseName().equalsIgnoreCase(courseName))
                .toList();
    }
}
