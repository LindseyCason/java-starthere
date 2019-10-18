package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.CourseService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})



    @ApiOperation(value = "Returns all courses", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Courses Found", response = Course.class),
            @ApiResponse(code = 404, message = "Courses Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        logger.info("endpoint /courses has been accessed");
        ArrayList<Course> myCourses = courseService.findAll( Pageable.unpaged() );
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns courses by page", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Courses Found", response = Course.class),
            @ApiResponse(code = 404, message = "Courses Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/course/paging", produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesByPage(@PageableDefault(page=0, size=3) Pageable pageable){
        List<Course> myCourses = courseService.findAllPageable(pageable);
        return new ResponseEntity<>( myCourses, HttpStatus.OK );
    }


    @ApiOperation(value = "Returns how many students per course", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Number of Students Found", response = Course.class),
            @ApiResponse(code = 404, message = "Student Count Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        logger.info("endpoint /courses/studcount has been accessed");
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(),
                HttpStatus.OK);
    }
    @ApiOperation(value = "Deletes course by id", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Courses Found", response = Course.class),
            @ApiResponse(code = 404, message = "Course Not Deleted", response = ErrorDetail.class)})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {
        logger.info("Delete endpoint /courses/:id has been accessed" + courseid);
        courseService.delete(courseid);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a course by ID", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Course ID Found", response = Course.class),
            @ApiResponse(code = 404, message = "Course ID Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/courseid/{courseid}",
            produces = {"application/json"})
    public ResponseEntity<?> getCourseById(
            @ApiParam(value = "Course ID number", required = true, example = "3")
            @PathVariable long courseid) {
        logger.info( "GET endpoint /course/" + courseid + " has been accessed" );
        Course r = courseService.findCourseById( courseid );
        return new ResponseEntity<>( r, HttpStatus.OK );
    }


//    This add does not work

    @PostMapping(value = "/course/add",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid
                                           @RequestBody
                                                   Course newCourse) throws URISyntaxException {
        logger.info( "POST endpoint /course/add has been accessed" );
        newCourse = courseService.save( newCourse );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

}