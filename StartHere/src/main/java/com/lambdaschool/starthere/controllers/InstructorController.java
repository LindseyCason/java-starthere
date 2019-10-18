package com.lambdaschool.starthere.controllers;


import com.lambdaschool.starthere.services.InstructorService;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.models.Instructor;
import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.services.InstructorService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/instructor")
public class InstructorController
{
    @Autowired
    private InstructorService instructorService;

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})



    @ApiOperation( value = "return all instructors", response = Instructor.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Instructors Found", response = Instructor.class),
            @ApiResponse(code = 404, message = "Instructors Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/instructors", produces = {"application/json"})
    public ResponseEntity<?> listAllInstructors()
    {

        logger.info("endpoint /instructors has been accessed");
        ArrayList<Instructor> myInstructors = instructorService.findAll();
        return new ResponseEntity<>(myInstructors, HttpStatus.OK);
    }
    @ApiOperation(value = "Retrieves all of the instructors by page", response = Instructor.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Instructor Found", response = Student.class),
            @ApiResponse(code = 404, message = "Instructor Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/instructors/paging", produces = {"application/json"})
    public ResponseEntity<?> listAllInstructorsByPage(@PageableDefault(page=0, size=3) Pageable pageable){
        List<Instructor> myInstructors = instructorService.findAllPageable(pageable);
        return new ResponseEntity<>( myInstructors, HttpStatus.OK );
    }

}