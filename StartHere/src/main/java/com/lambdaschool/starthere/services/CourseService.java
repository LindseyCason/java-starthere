package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.view.JustTheCount;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface CourseService
{
    ArrayList<Course> findAll(Pageable unpaged);

    List<Course> findAllPageable(Pageable pageable);

    Course findCourseById(long id);

    Course save(Course course);

    ArrayList<JustTheCount> getCountStudentsInCourse();

    void delete(long id);
}
