package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Instructor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface InstructorRepository extends PagingAndSortingRepository<Instructor, Long>
{
    ArrayList<Instructor> findInstructorsByInstructnameEquals(String name);
}