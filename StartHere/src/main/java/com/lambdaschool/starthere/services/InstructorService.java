package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Instructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface InstructorService
{
    ArrayList<Instructor> findAll();

    List<Instructor> findAllPageable(Pageable pageable);
}
