package com.example.CourseRegistrationSystem.Repository;

import com.example.CourseRegistrationSystem.Model.Courseregistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseregistryrepo extends JpaRepository<Courseregistry,Integer> {
    
}
