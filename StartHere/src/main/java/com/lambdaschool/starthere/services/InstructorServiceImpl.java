package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Instructor;
import com.lambdaschool.starthere.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public List<Instructor> findAllPageable(Pageable pageable) {
        List<Instructor> list = new ArrayList<>();
        instructrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public ArrayList<Instructor> findAll()
    {
        List<Instructor> list = new ArrayList<>();
        instructrepos.findAll().iterator().forEachRemaining(list::add);
        return (ArrayList<Instructor>) list;
    }
}
