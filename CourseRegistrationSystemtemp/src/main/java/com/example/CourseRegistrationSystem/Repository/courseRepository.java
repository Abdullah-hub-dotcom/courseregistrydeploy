package com.example.CourseRegistrationSystem.Repository;

import com.example.CourseRegistrationSystem.Model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseRepository extends JpaRepository<Model,String> {

}
