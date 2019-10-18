package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceFoundException;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.repository.CourseRepository;
import com.lambdaschool.starthere.view.JustTheCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Override
    public List<Course> findAllPageable(Pageable pageable) {
        List<Course> list = new ArrayList<>();
        courserepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;    }

    @Autowired
    private CourseRepository courserepos;

    @Override
    public ArrayList<Course> findAll(Pageable unpaged)
    {
        ArrayList<Course> list = new ArrayList<>();
        courserepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public ArrayList<JustTheCount> getCountStudentsInCourse()
    {
        return courserepos.getCountStudentsInCourse();
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (courserepos.findById(id).isPresent())
        {
            courserepos.deleteCourseFromStudcourses(id);
            courserepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Override
    public Course findCourseById(long id) throws ResourceFoundException
    {
        return courserepos.findById(id)
                .orElseThrow(() -> new ResourceFoundException(Long.toString(id)));
    }

    @Override
    public Course save(Course course) {
        Course newCourse = new Course();
        newCourse.setCoursename(course.getCoursename());

        return courserepos.save(newCourse);
    }
}
